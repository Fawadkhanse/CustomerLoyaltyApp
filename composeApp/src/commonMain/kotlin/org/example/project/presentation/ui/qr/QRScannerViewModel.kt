package org.example.project.presentation.ui.qr

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.domain.models.Resource

class QRScannerViewModel : ViewModel() {

    private val _scanState = MutableStateFlow<Resource<QRScanResponse>>(Resource.None)
    val scanState: StateFlow<Resource<QRScanResponse>> = _scanState.asStateFlow()

    private val _customerInfo = MutableStateFlow<CustomerInfo?>(null)
    val customerInfo: StateFlow<CustomerInfo?> = _customerInfo.asStateFlow()

    private var hasScanned = false

    fun scanQRCode(qrId: String) {
        if (hasScanned) return // Prevent multiple scans

        hasScanned = true
        _scanState.value = Resource.Loading

        viewModelScope.launch {
            try {
                // TODO: Replace with actual API call
                // Simulate API call for now
                kotlinx.coroutines.delay(1000)

                // Mock customer data - replace with actual API response
                val mockCustomer = CustomerInfo(
                    id = qrId,
                    name = "John Doe",
                    points = 1250,
                    qrId = qrId
                )

                val response = QRScanResponse(
                    success = true,
                    customer = mockCustomer
                )

                _scanState.value = Resource.Success(response)
                _customerInfo.value = mockCustomer
            } catch (e: Exception) {
                _scanState.value = Resource.Error(e)
                hasScanned = false
            }
        }
    }

    fun awardPoints(customerId: String, points: Int) {
        viewModelScope.launch {
            try {
                _scanState.value = Resource.Loading

                // TODO: Replace with actual API call to award points
                kotlinx.coroutines.delay(1000)

                _scanState.value = Resource.Success(
                    QRScanResponse(success = true, customer = null)
                )

                // Clear customer info after successful award
                clearCustomerInfo()
            } catch (e: Exception) {
                _scanState.value = Resource.Error(e)
            }
        }
    }

    fun showInvalidQRError() {
        _scanState.value = Resource.Error(Exception("Invalid QR code format"))
        hasScanned = false
    }

    fun clearCustomerInfo() {
        _customerInfo.value = null
        hasScanned = false
        _scanState.value = Resource.None
    }

    fun resetScanState() {
        hasScanned = false
        _scanState.value = Resource.None
    }
}