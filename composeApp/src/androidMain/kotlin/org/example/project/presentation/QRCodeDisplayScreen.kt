package org.example.project.presentation

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.content.FileProvider
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

/**
 * Generate QR code bitmap with customer name overlay
 */
actual suspend fun generateQRCodeBitmap(
    qrData: String,
    customerName: String
): ImageBitmap? = withContext(Dispatchers.Default) {
    try {
        val size = 512
        val qrCodeWriter = QRCodeWriter()
        val bitMatrix = qrCodeWriter.encode(qrData, Barcode.FORMAT_QR_CODE, size, size)

        val width = bitMatrix.width
        val height = bitMatrix.height

        // Create bitmap with extra space for text
        val totalHeight = height + 100 // Extra space for text
        val bitmap = Bitmap.createBitmap(width, totalHeight, Bitmap.Config.ARGB_8888)

        // Draw QR code
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(
                    x,
                    y,
                    if (bitMatrix[x, y])
                        Color.BLACK
                    else
                        Color.WHITE
                )
            }
        }

        // Draw white background for text area
        val canvas = Canvas(bitmap)
        val paint = Paint().apply {
            color = Color.WHITE
            style = Paint.Style.FILL
        }
        canvas.drawRect(0f, height.toFloat(), width.toFloat(), totalHeight.toFloat(), paint)

        // Draw customer name
        val textPaint = Paint().apply {
            color = Color.BLACK
            textSize = 32f
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }

        canvas.drawText(
            customerName,
            width / 2f,
            height + 50f,
            textPaint
        )

        bitmap.asImageBitmap()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

/**
 * Share QR code using Android's share sheet
 */
actual suspend fun shareQRCode(
    bitmap: ImageBitmap,
    customerName: String
) = withContext(Dispatchers.IO) {
    try {
        val context = getApplicationContext()
        val androidBitmap = bitmap.asAndroidBitmap()

        // Save to cache
        val cachePath = File(context.cacheDir, "qr_codes")
        cachePath.mkdirs()
        val file = File(cachePath, "qr_${System.currentTimeMillis()}.png")

        val stream = FileOutputStream(file)
        androidBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.close()

        // Get URI using FileProvider
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )

        // Create share intent
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            Intent.createChooser(this, "Share QR Code")
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_SUBJECT, "QR Code for $customerName")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        context.startActivity(Intent.createChooser(shareIntent, "Share QR Code").apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * Save QR code to device gallery
 */
actual suspend fun saveQRCode(
    bitmap: ImageBitmap,
    customerName: String
): Boolean = withContext(Dispatchers.IO) {
    try {
        val context = getApplicationContext()
        val androidBitmap = bitmap.asAndroidBitmap()

        val fileName = "QR_${customerName.replace(" ", "_")}_${System.currentTimeMillis()}.png"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Use MediaStore for Android 10+
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/LoyaltyApp")
            }

            val uri = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )

            uri?.let {
                val outputStream: OutputStream? = context.contentResolver.openOutputStream(it)
                outputStream?.use { stream ->
                    androidBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                }
                true
            } ?: false
        } else {
            // Legacy method for older Android versions
            val picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val appDir = File(picturesDir, "LoyaltyApp")
            if (!appDir.exists()) {
                appDir.mkdirs()
            }

            val file = File(appDir, fileName)
            val outputStream = FileOutputStream(file)
            androidBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.close()

            // Notify media scanner
            val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            intent.data = Uri.fromFile(file)
            context.sendBroadcast(intent)

            true
        }
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

// Helper to get application context
private fun getApplicationContext(): Context {
    return try {
        Class.forName("android.app.ActivityThread")
            .getMethod("currentApplication")
            .invoke(null) as Context
    } catch (e: Exception) {
        throw IllegalStateException("Unable to get application context", e)
    }
}