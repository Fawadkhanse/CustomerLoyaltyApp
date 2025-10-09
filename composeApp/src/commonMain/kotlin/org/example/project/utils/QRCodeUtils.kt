package org.example.project.utils


import io.github.alexzhirkevich.qrose.options.*
import androidx.compose.ui.graphics.Color as ComposeColor

object QRCodeUtils {

    /**
     * Create QR code data string from user ID
     */
    fun createQRCodeData(qrId: String): String {
        return "littleAppStation00$qrId"
    }

    /**
     * Parse QR code data to extract user ID
     */
    fun parseQRCodeData(qrData: String): String? {
        return if (qrData.startsWith("littleAppStation00")) {
            qrData.removePrefix("littleAppStation00")
        } else {
            null
        }
    }

    /**
     * Validate QR code format
     */
    fun isValidQRCode(qrData: String): Boolean {
        return qrData.startsWith("littleAppStation00") &&
                qrData.length > "littleAppStation00".length
    }
}


