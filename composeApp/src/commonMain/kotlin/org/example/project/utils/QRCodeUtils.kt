package org.example.project.utils


import io.github.alexzhirkevich.qrose.options.*
import androidx.compose.ui.graphics.Color as ComposeColor


object QRCodeUtils {

    // Constants for QR code prefixes
    private const val USER_QR_PREFIX = "littleAppStation00user:"
    private const val COUPON_QR_PREFIX = "littleAppStation00coupon:"

    /**
     * Create user QR code data string from user ID
     */
    fun createQRCodeData(qrId: String): String {
        return "$USER_QR_PREFIX$qrId"
    }

    /**
     * Create coupon QR code data string from coupon ID
     */
    fun createCouponQRCodeData(couponId: String): String {
        return "$COUPON_QR_PREFIX$couponId"
    }

    /**
     * Parse QR code data to extract user ID
     */
    fun parseQRCodeData(qrData: String): String? {
        return when {
            qrData.startsWith(USER_QR_PREFIX) -> {
                qrData.removePrefix(USER_QR_PREFIX)
            }
            // Support legacy format without "user:" prefix
            qrData.startsWith("littleAppStation00") && !qrData.contains("coupon:") -> {
                qrData.removePrefix("littleAppStation00")
            }
            else -> null
        }
    }

    /**
     * Parse coupon QR code data to extract coupon ID
     */
    fun parseCouponQRCode(qrData: String): String? {
        return if (qrData.startsWith(COUPON_QR_PREFIX)) {
            qrData.removePrefix(COUPON_QR_PREFIX)
        } else {
            null
        }
    }

    /**
     * Determine QR code type from the scanned data
     */
    fun getQRCodeType(qrData: String): QRCodeType? {
        return when {
            qrData.startsWith(USER_QR_PREFIX) -> QRCodeType.USER
            qrData.startsWith(COUPON_QR_PREFIX) -> QRCodeType.COUPON
            // Legacy format support (old user QR codes without "user:" prefix)
           // qrData.startsWith("littleAppStation00") && !qrData.contains("coupon:") -> QRCodeType.USER
            else -> null
        }
    }

    /**
     * Validate QR code format
     */
    fun isValidQRCode(qrData: String): Boolean {
        return getQRCodeType(qrData) != null
    }

    /**
     * Validate user QR code format
     */
    fun isValidUserQRCode(qrData: String): Boolean {
        return getQRCodeType(qrData) == QRCodeType.USER
    }

    /**
     * Validate coupon QR code format
     */
    fun isValidCouponQRCode(qrData: String): Boolean {
        return getQRCodeType(qrData) == QRCodeType.COUPON
    }
}

enum class QRCodeType {
    USER,
    COUPON
}

