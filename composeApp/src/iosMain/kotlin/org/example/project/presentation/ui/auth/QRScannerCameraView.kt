package org.example.project.presentation.ui.auth

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import platform.AVFoundation.*
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UIView
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun QRScannerCameraView(
    onQRCodeScanned: (String) -> Unit,
    modifier: Modifier
) {
    var hasScanned by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        // Request camera permission
        AVCaptureDevice.requestAccessForMediaType(
            AVMediaTypeVideo
        ) { granted ->
            if (!granted) {
                // Handle permission denied
            }
        }

        onDispose { }
    }

    UIKitView(
        factory = {
            val view = UIView(frame = CGRectMake(0.0, 0.0, 0.0, 0.0))

            val captureSession = AVCaptureSession()
            captureSession.sessionPreset = AVCaptureSessionPresetHigh

            val videoDevice = AVCaptureDevice.defaultDeviceWithMediaType(AVMediaTypeVideo)

            videoDevice?.let { device ->
                try {
                    val input = AVCaptureDeviceInput.deviceInputWithDevice(device, null)

                    input?.let {
                        if (captureSession.canAddInput(it)) {
                            captureSession.addInput(input)
                        }
                    }

                    val output = AVCaptureMetadataOutput()
                    if (captureSession.canAddOutput(output)) {
                        captureSession.addOutput(output)

                        val delegate = QRCodeScannerDelegate { qrCode ->
                            if (!hasScanned) {
                                hasScanned = true
                                onQRCodeScanned(qrCode)

                                // Reset after delay
                                GlobalScope.launch {
                                    delay(3000)
                                    hasScanned = false
                                }
                            }
                        }

                        output.setMetadataObjectsDelegate(delegate, null)
                        output.metadataObjectTypes = listOf(AVMetadataObjectTypeQRCode)
                    }

                    val previewLayer = AVCaptureVideoPreviewLayer(session = captureSession)
                    previewLayer.videoGravity = AVLayerVideoGravityResizeAspectFill
                    previewLayer.frame = view.bounds
                    view.layer.addSublayer(previewLayer)

                    captureSession.startRunning()
                } catch (e: Exception) {
                    println("Camera setup error: ${e.message}")
                }
            }

            view
        },
        modifier = modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalForeignApi::class)
private class QRCodeScannerDelegate(
    private val onQRCodeScanned: (String) -> Unit
) : NSObject(), AVCaptureMetadataOutputObjectsDelegateProtocol {

    override fun captureOutput(
        output: AVCaptureOutput,
        didOutputMetadataObjects: List<*>,
        fromConnection: AVCaptureConnection
    ) {
        didOutputMetadataObjects.firstOrNull()?.let { metadata ->
            if (metadata is AVMetadataMachineReadableCodeObject) {
                metadata.stringValue?.let { qrCode ->
                    onQRCodeScanned(qrCode)
                }
            }
        }
    }
}