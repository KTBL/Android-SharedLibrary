package de.ktbl.android.sharedlibrary

import com.google.android.material.snackbar.Snackbar

data class SnackbarNotificationEvent(
        val text: String? = null,
        val length: Int = Snackbar.LENGTH_SHORT) {
    var hasBeenHandled: Boolean = false
}