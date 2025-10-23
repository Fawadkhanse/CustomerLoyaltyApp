// File: composeApp/src/androidMain/kotlin/org/example/project/presentation/ui/auth/QRCodePlatform.android.kt
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
import io.github.alexzhirkevich.qrose.options.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream


// Generate QR Code Bitmap
actual suspend fun generateQRCodeBitmap(qrData: String, customerName: String): ImageBitmap {
    return withContext(Dispatchers.Default) {
        val size = 800
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // Draw white background
        canvas.drawColor(Color.WHITE)

        // Generate QR code using qrose library
        val qrBitmap = createQRCodeBitmap(qrData, size - 100)

        // Draw QR code centered
        val left = (size - qrBitmap.width) / 2f
        val top = 50f
        canvas.drawBitmap(qrBitmap, left, top, null)

        // Draw customer name below QR code
        val textPaint = Paint().apply {
            color = Color.BLACK
            textSize = 40f
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }

        canvas.drawText(
            customerName,
            size / 2f,
            size - 30f,
            textPaint
        )

        bitmap.asImageBitmap()
    }
}

// Helper function to create QR code bitmap using Google's ZXing or similar
private fun createQRCodeBitmap(content: String, size: Int): Bitmap {
    // Using a simple approach - in production, use ZXing or similar library
    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    try {
        // For production, implement proper QR code generation with ZXing:
        // val writer = QRCodeWriter()
        // val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, size, size)

        // For now, create a placeholder
        val paint = Paint().apply {
            color = Color.BLACK
        }

        // Draw a simple pattern (replace with actual QR code generation)
        val cellSize = size / 25
        for (i in 0 until 25) {
            for (j in 0 until 25) {
                if ((i + j) % 2 == 0) {
                    canvas.drawRect(
                        (i * cellSize).toFloat(),
                        (j * cellSize).toFloat(),
                        ((i + 1) * cellSize).toFloat(),
                        ((j + 1) * cellSize).toFloat(),
                        paint
                    )
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return bitmap
}

// Share QR Code
actual suspend fun shareQRCode(bitmap: ImageBitmap, customerName: String) {
    withContext(Dispatchers.Main) {
        try {
            val context = AppContextProvider.context ?: return@withContext

            // Save bitmap to cache directory
            val cachePath = File(context.cacheDir, "qr_codes")
            cachePath.mkdirs()

            val file = File(cachePath, "qr_code_${System.currentTimeMillis()}.png")
            val stream = FileOutputStream(file)
            bitmap.asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.close()

            // Get URI for the file
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )

            // Create share intent
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                Intent.setType = "image/png"
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_SUBJECT, "My Loyalty QR Code")
                putExtra(Intent.EXTRA_TEXT, "My loyalty QR code for $customerName")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            val chooser = Intent.createChooser(shareIntent, "Share QR Code")
            chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(chooser)

        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Failed to share QR code: ${e.message}")
        }
    }
}

// Save QR Code to Gallery
actual suspend fun saveQRCode(bitmap: ImageBitmap, customerName: String): Boolean {
    return withContext(Dispatchers.IO) {
        try {
            val context = AppContextProvider.context ?: return@withContext false

            val displayName = "loyalty_qr_${customerName}_${System.currentTimeMillis()}.png"
            val mimeType = "image/png"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Use MediaStore for Android 10+
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
                    put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
                    put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/LoyaltyQR")
                }

                val resolver = context.contentResolver
                val imageUri = resolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues
                )

                imageUri?.let { uri ->
                    resolver.openOutputStream(uri)?.use { outputStream ->
                        bitmap.asAndroidBitmap().compress(
                            Bitmap.CompressFormat.PNG,
                            100,
                            outputStream
                        )
                    }
                    true
                } ?: false
            } else {
                // Legacy approach for older Android versions
                val imagesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                val qrDir = File(imagesDir, "LoyaltyQR")
                qrDir.mkdirs()

                val imageFile = File(qrDir, displayName)
                FileOutputStream(imageFile).use { outputStream ->
                    bitmap.asAndroidBitmap().compress(
                        Bitmap.CompressFormat.PNG,
                        100,
                        outputStream
                    )
                }

                // Notify media scanner
                val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                intent.data = Uri.fromFile(imageFile)
                context.sendBroadcast(intent)

                true
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}

// Context provider for Android
object AppContextProvider {
    var context: Context? = null
        private set

    fun initialize(context: Context) {
        this.context = context.applicationContext
    }
}

// Initialize in MainActivity
// Add this to your MainActivity.kt:
/*
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize context provider
        AppContextProvider.initialize(this)
        
        setContent {
            App()
        }
    }
}
*/