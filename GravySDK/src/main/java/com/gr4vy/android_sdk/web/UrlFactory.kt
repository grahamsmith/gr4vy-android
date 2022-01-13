package com.gr4vy.android_sdk.web

import com.gr4vy.android_sdk.models.Parameters

class UrlFactory {

    companion object {

        fun fromParameters(parameters: Parameters) : String {
            return "https://embed.${parameters.config.instance}.gr4vy.app/mobile.html?channel=123"
        }
    }
}