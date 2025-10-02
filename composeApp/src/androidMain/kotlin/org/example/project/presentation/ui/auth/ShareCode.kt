package org.example.project.presentation.ui.auth

// Android actual
actual fun ByteArray.encodeBase64(): String =
    android.util.Base64.encodeToString(this, android.util.Base64.NO_WRAP)