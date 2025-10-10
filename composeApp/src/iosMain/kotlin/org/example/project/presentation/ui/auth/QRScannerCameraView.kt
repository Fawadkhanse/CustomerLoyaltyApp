

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.*
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSNotificationCenter
import platform.UIKit.UIView
import platform.darwin.NSObject
import kotlinx.cinterop.ObjCAction
import kotlin.system.getTimeMillis

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun QRScannerCameraView(
    onQRCodeScanned: (String) -> Unit,
    modifier: Modifier
) {
    var lastScannedCode by remember { mutableStateOf<String?>(null) }
    var scanTimestamp by remember { mutableStateOf(0L) }

    UIKitView(
        factory = {
            val cameraView = UIView()

            // Check camera authorization
            val authStatus = AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)

            when (authStatus) {
                AVAuthorizationStatusAuthorized -> {
                    setupCamera(
                        view = cameraView,
                        onQRCodeDetected = { qrCode ->
                            val currentTime = getTimeMillis()
                            if (currentTime - scanTimestamp > 2000 && qrCode != lastScannedCode) {
                                lastScannedCode = qrCode
                                scanTimestamp = currentTime
                                onQRCodeScanned(qrCode)
                            }
                        }
                    )
                }
                AVAuthorizationStatusNotDetermined -> {
                    AVCaptureDevice.requestAccessForMediaType(AVMediaTypeVideo) { granted ->
                        if (granted) {
                            setupCamera(
                                view = cameraView,
                                onQRCodeDetected = { qrCode ->
                                    val currentTime = getTimeMillis()
                                    if (currentTime - scanTimestamp > 2000 && qrCode != lastScannedCode) {
                                        lastScannedCode = qrCode
                                        scanTimestamp = currentTime
                                        onQRCodeScanned(qrCode)
                                    }
                                }
                            )
                        }
                    }
                }
                else -> {
                    // Permission denied - show message
                }
            }

            cameraView
        },
        modifier = modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalForeignApi::class)
private fun setupCamera(
    view: UIView,
    onQRCodeDetected: (String) -> Unit
) {
    val captureSession = AVCaptureSession()
    captureSession.sessionPreset = AVCaptureSessionPresetHigh

    // Get camera device
    val videoDevice = AVCaptureDevice.defaultDeviceWithMediaType(AVMediaTypeVideo)
        ?: return

    // Create input
    val videoInput = try {
        AVCaptureDeviceInput.deviceInputWithDevice(videoDevice, null)
    } catch (e: Exception) {
        return
    }

    if (captureSession.canAddInput(videoInput)) {
        captureSession.addInput(videoInput)
    }

    // Create metadata output
    val metadataOutput = AVCaptureMetadataOutput()

    if (captureSession.canAddOutput(metadataOutput)) {
        captureSession.addOutput(metadataOutput)

        // Set delegate for QR code detection
        val delegate = QRCodeScannerDelegate { qrCode ->
            onQRCodeDetected(qrCode)
        }

        metadataOutput.setMetadataObjectsDelegate(
            delegate,
            dispatch_get_main_queue()
        )

        metadataOutput.metadataObjectTypes = listOf(AVMetadataObjectTypeQRCode)
    }

    // Create preview layer
    val previewLayer = AVCaptureVideoPreviewLayer.layerWithSession(captureSession)
    previewLayer.videoGravity = AVLayerVideoGravityResizeAspectFill
    previewLayer.frame = view.bounds
    view.layer.addSublayer(previewLayer)

    // Start session
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0u)) {
        captureSession.startRunning()
    }
}

@OptIn(ExperimentalForeignApi::class)
private class QRCodeScannerDelegate(
    private val onQRCodeDetected: (String) -> Unit
) : NSObject(), AVCaptureMetadataOutputObjectsDelegateProtocol {

    override fun captureOutput(
        output: AVCaptureOutput,
        didOutputMetadataObjects: List<*>,
        fromConnection: AVCaptureConnection
    ) {
        didOutputMetadataObjects.firstOrNull()?.let { metadata ->
            if (metadata is AVMetadataMachineReadableCodeObject) {
                metadata.stringValue?.let { qrCode ->
                    onQRCodeDetected(qrCode)
                }
            }
        }
    }
}

// ViewModel provider for iOS
@Composable
actual fun rememberQRScannerViewModel(): QRScannerViewModel {
    return remember {
        // Get from Koin
        org.koin.core.component.KoinComponent().getKoin().get()
    }
}