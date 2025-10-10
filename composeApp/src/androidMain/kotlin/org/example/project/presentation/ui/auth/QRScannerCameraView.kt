package org.example.project.presentation.ui.auth


import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors

@Composable
actual fun QRScannerCameraView(
    onQRCodeScanned: (String) -> Unit,
    modifier: Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    if (hasCameraPermission) {
        var lastScannedCode by remember { mutableStateOf<String?>(null) }
        var scanTimestamp by remember { mutableStateOf(0L) }

        AndroidView(
            factory = { ctx ->
                val previewView = PreviewView(ctx)
                val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                val executor = Executors.newSingleThreadExecutor()

                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()

                    // Preview
                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                    // Image analysis for QR scanning
                    val imageAnalysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()

                    val barcodeScanner = BarcodeScanning.getClient()

                    imageAnalysis.setAnalyzer(executor) { imageProxy ->
                        val currentTime = System.currentTimeMillis()

                        // Throttle scanning to prevent multiple scans
                        if (currentTime - scanTimestamp > 2000) {
                            processImageProxy(
                                imageProxy = imageProxy,
                                barcodeScanner = barcodeScanner,
                                onBarcodeDetected = { barcode ->
                                    barcode.rawValue?.let { qrCode ->
                                        if (qrCode != lastScannedCode) {
                                            lastScannedCode = qrCode
                                            scanTimestamp = currentTime
                                            onQRCodeScanned(qrCode)
                                        }
                                    }
                                }
                            )
                        } else {
                            imageProxy.close()
                        }
                    }

                    // Camera selector
                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            preview,
                            imageAnalysis
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }, ContextCompat.getMainExecutor(ctx))

                previewView
            },
            modifier = modifier.fillMaxSize()
        )
    } else {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Camera permission is required to scan QR codes")
        }
    }
}

@androidx.camera.core.ExperimentalGetImage
private fun processImageProxy(
    imageProxy: ImageProxy,
    barcodeScanner: com.google.mlkit.vision.barcode.BarcodeScanner,
    onBarcodeDetected: (Barcode) -> Unit
) {
    imageProxy.image?.let { mediaImage ->
        val inputImage = InputImage.fromMediaImage(
            mediaImage,
            imageProxy.imageInfo.rotationDegrees
        )

        barcodeScanner.process(inputImage)
            .addOnSuccessListener { barcodes ->
                barcodes.firstOrNull { it.format == Barcode.FORMAT_QR_CODE }
                    ?.let(onBarcodeDetected)
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    } ?: imageProxy.close()
}

// ViewModel provider for Android
@Composable
actual fun rememberQRScannerViewModel(): QRScannerViewModel {
    return org.koin.androidx.compose.koinViewModel()
}