package com.gr4vy.android_sdk.web

import com.gr4vy.android_sdk.models.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MessageHandler(private val parameters: Parameters) {

    private val json = Json {
        classDiscriminator = "theType"
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    fun handleMessage(realMessage: String): MessageHandlerResult {
        val decodedMessage: Message =
            json.decodeFromString(MessagePolymorphicSerializer(), realMessage)

        return when (decodedMessage) {
            is UnknownMessage -> Unknown()
            is ApprovalMessage -> Open3ds(decodedMessage.data)
            is FrameReadyMessage -> {

                val jsonToPost = json.encodeToString(UpdateMessage.fromParameters(parameters))

                val js = "window.postMessage($jsonToPost)"

                return FrameReady(js)
            }
            is TransactionMessage -> {
                when (decodedMessage.data.status) {
                    "capture_succeeded", "capture_pending", "authorization_succeeded", "authorization_pending" -> {
                        return Gr4vyMessageResult(
                            Gr4vyResult.TransactionCreated(
                                status = decodedMessage.data.status
                            )
                        )
                    }
                    "capture_declined", "authorization_failed" -> {
                        return Gr4vyMessageResult(
                            Gr4vyResult.TransactionFailed(status = decodedMessage.data.status)
                        )
                    }
                    else -> {
                        return Gr4vyMessageResult(Gr4vyResult.GeneralError())
                    }
                }
            }
        }
    }
}

sealed class MessageHandlerResult
class Open3ds(val url: String) : MessageHandlerResult()
class Gr4vyMessageResult(val result: Gr4vyResult) : MessageHandlerResult()
class FrameReady(val js: String) : MessageHandlerResult()
class Unknown : MessageHandlerResult()