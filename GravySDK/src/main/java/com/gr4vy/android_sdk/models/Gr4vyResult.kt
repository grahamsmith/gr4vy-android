package com.gr4vy.android_sdk.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class Gr4vyResult : Parcelable {
    @Parcelize
    class TransactionCreated(
        val status: String,
    ) : Gr4vyResult()

    @Parcelize
    class TransactionFailed(
        val status: String,
    ) : Gr4vyResult()

    @Parcelize
    class GeneralError(val reason: String? = "") : Gr4vyResult()
}

