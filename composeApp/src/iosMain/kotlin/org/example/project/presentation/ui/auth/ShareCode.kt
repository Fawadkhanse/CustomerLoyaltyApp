package org.example.project.presentation.ui.auth

// iOS actual

// iosMain/kotlin/.../Base64Utils.kt
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.base64EncodedStringWithOptions
import platform.Foundation.create

@OptIn(ExperimentalForeignApi::class)
actual fun ByteArray.encodeBase64(): String {
    // Pin the array in memory so we can safely pass a pointer
    return this.usePinned { pinned ->
        val nsData = NSData.create(
            bytes = pinned.addressOf(0),
            length = this.size.toULong()
        )
        nsData.base64EncodedStringWithOptions(0u)
    }
}

