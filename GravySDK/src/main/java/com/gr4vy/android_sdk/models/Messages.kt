package com.gr4vy.android_sdk.models

import kotlinx.serialization.Serializable

@Serializable
data class Transaction(
    val status: String,
)

@Serializable
sealed class Message

@Serializable
object UnknownMessage : Message()

@Serializable
data class FrameReadyMessage(
    val type: String,
    val channel: String,
) : Message()

@Serializable
data class ApprovalMessage(
    val type: String,
    val channel: String,
    val data: String,
) : Message()

@Serializable
data class TransactionMessage(
    val type: String,
    val channel: String,
    val data: Transaction,
) : Message()

@Serializable
data class UpdateMessage(
    val type: String,
    val data: Update,

    ) {
    companion object {

        fun fromParameters(parameters: Parameters): UpdateMessage {

            return UpdateMessage(
                type = "updateOptions",
                data = Update(
                    apiHost = "api.${parameters.config.instance}.gr4vy.app",
                    apiUrl = "https://api.${parameters.config.instance}.gr4vy.app",
                    buyerId = parameters.config.buyerId,
                    token = parameters.token,
                    amount = parameters.amount,
                    country = parameters.country,
                    currency = parameters.currency,
                    externalIdentifier = parameters.externalIdentifier,
                    store = parameters.store,
                    display = parameters.display,
                    gr4vyIntent = parameters.gr4vyIntent,
                )
            )
        }
    }
}

@Serializable
data class Update(
    val apiHost: String,
    val apiUrl: String,
    val buyerId: String,
    val token: String,
    val amount: Int,
    val country: String,
    val currency: String,
    val externalIdentifier: String? = null,
    val store: String? = null,
    val display: String? = null,
    val gr4vyIntent: String? = null
)