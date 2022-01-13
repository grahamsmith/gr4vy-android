package com.gr4vy.android_sdk.web

import android.webkit.WebChromeClient
import android.webkit.WebView

class MyWebChromeClient : WebChromeClient() {

    var titleUpdateListener: ((title: String) -> Unit)? = null

    override fun onReceivedTitle(view: WebView?, title: String?) {
        super.onReceivedTitle(view, title)
        titleUpdateListener?.invoke(title.orEmpty())
    }
}