package org.example.project.utils

import io.github.alexzhirkevich.qrose.options.*
import androidx.compose.ui.graphics.Color as ComposeColor

object QRCodeUtils {

    // Constants for QR code prefixes
    private const val USER_QR_PREFIX = "littleAppStation00user:"
    private const val COUPON_QR_PREFIX = "littleAppStation00coupon:"
    private const val LEGACY_PREFIX = "littleAppStation00"

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
     * Returns null if it's not a user QR code
     */
    fun parseQRCodeData(qrData: String): String? {
        return when {
            qrData.startsWith(USER_QR_PREFIX) -> {
                qrData.removePrefix(USER_QR_PREFIX)
            }
            // Support legacy format without "user:" prefix (only if it's not a coupon)
            qrData.startsWith(LEGACY_PREFIX) && !qrData.contains("user:") && !qrData.contains("coupon:") -> {
                qrData.removePrefix(LEGACY_PREFIX)
            }
            else -> null
        }
    }

    /**
     * Parse coupon QR code data to extract coupon ID
     * Returns null if it's not a coupon QR code
     */
    fun parseCouponQRCode(qrData: String): String? {
        return if (qrData.startsWith(COUPON_QR_PREFIX)) {
            qrData.removePrefix(COUPON_QR_PREFIX)
        } else {
            null
        }
    }

    /**
     * Parse ANY QR code and return the ID with type information
     * Returns Pair(type, id) or null if invalid
     */
    fun parseQRCode(qrData: String): Pair<QRCodeType, String>? {
        return when {
            qrData.startsWith(USER_QR_PREFIX) -> {
                val id = qrData.removePrefix(USER_QR_PREFIX)
                if (id.isNotEmpty()) QRCodeType.USER to id else null
            }
            qrData.startsWith(COUPON_QR_PREFIX) -> {
                val id = qrData.removePrefix(COUPON_QR_PREFIX)
                if (id.isNotEmpty()) QRCodeType.COUPON to id else null
            }
            // Legacy user QR codes (without explicit type prefix)
            qrData.startsWith(LEGACY_PREFIX) && !qrData.contains("user:") && !qrData.contains("coupon:") -> {
                val id = qrData.removePrefix(LEGACY_PREFIX)
                if (id.isNotEmpty()) QRCodeType.USER to id else null
            }
            else -> null
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
            qrData.startsWith(LEGACY_PREFIX) && !qrData.contains("user:") && !qrData.contains("coupon:") -> QRCodeType.USER
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