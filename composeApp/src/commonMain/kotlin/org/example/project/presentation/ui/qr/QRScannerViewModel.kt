package org.example.project.presentation.ui.qr

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.api.ApiEndpoints
import org.example.project.data.api.HttpMethod
import org.example.project.domain.RemoteRepository
import org.example.project.domain.models.AwardPointsRequest
import org.example.project.domain.models.AwardPointsResponse
import org.example.project.domain.models.CustomerQRInfoResponse
import org.example.project.domain.models.QRScanRequest
import org.example.project.domain.models.QRScanResponse
import org.example.project.domain.models.Resource
import org.example.project.presentation.common.BaseViewModel
import org.example.project.presentation.common.constent.GlobalVar

class QRScannerViewModel(
    private val remoteRepository: RemoteRepository
) : BaseViewModel() {

    private val _scanState = MutableStateFlow<Resource<QRScanResponse>>(Resource.None)
    val scanState: StateFlow<Resource<QRScanResponse>> = _scanState.asStateFlow()

    private val _customerInfo = MutableStateFlow<CustomerInfo?>(null)
    val customerInfo: StateFlow<CustomerInfo?> = _customerInfo.asStateFlow()

    private val _awardPointsState = MutableStateFlow<Resource<AwardPointsResponse>>(Resource.None)
    val awardPointsState: StateFlow<Resource<AwardPointsResponse>> = _awardPointsState.asStateFlow()

    private var hasScanned = false
    private var isProcessing = false

    /**
     * Scan QR code and fetch customer info
     */
    fun scanQRCode(qrId: String) {
        if (hasScanned || isProcessing) return

        isProcessing = true
        hasScanned = true
        _scanState.value = Resource.Loading
        val request = QRScanRequest(qrId)

        viewModelScope.launch {
            try {
                remoteRepository.makeApiRequest(
                    requestModel = request,
                    endpoint = ApiEndpoints.SCAN_QR_CODE,
                    httpMethod = HttpMethod.POST,
                    isMock = GlobalVar.isMock
                ).collectAsResource<QRScanResponse>(
                    onEmit = { result ->
                        when (result) {
                            is Resource.Success -> {

                                _scanState.value = result
                            }
                            is Resource.Error -> {
                                _scanState.value = result
                                hasScanned = false
                            }
                            is Resource.Loading -> {
                                _scanState.value = result
                            }
                            else -> {}
                        }
                        isProcessing = false
                    },
                    useMock = GlobalVar.isMock,
                    mockResponse = QRScanResponse(
                        message = "QR code scanned successfully",
                        points_awarded = 10,
                        total_points = 100

                        )
                    )

            } catch (e: Exception) {
                _scanState.value = Resource.Error(e)
                hasScanned = false
                isProcessing = false
            }
        }
    }

    /**
     * Award points to customer
     */
    fun awardPoints(customerId: String, points: Int) {
        if (isProcessing) return

        isProcessing = true
        viewModelScope.launch {
            try {
                val request = AwardPointsRequest(
                    customerId = customerId,
                    points = points
                )

                remoteRepository.makeApiRequest(
                    requestModel = request,
                    endpoint = ApiEndpoints.AWARD_POINTS,
                    httpMethod = HttpMethod.POST,
                    isMock = GlobalVar.isMock
                ).collectAsResource<AwardPointsResponse>(
                    onEmit = { result ->
                        _awardPointsState.value = result
                        if (result is Resource.Success) {
                            clearCustomerInfo()
                        }
                        isProcessing = false
                    },
                    useMock = GlobalVar.isMock,
                    mockResponse = AwardPointsResponse(
                        success = true,
                        message = "Points awarded successfully",
                        newBalance = points + (_customerInfo.value?.points ?: 0)
                    )
                )
            } catch (e: Exception) {
                _awardPointsState.value = Resource.Error(e)
                isProcessing = false
            }
        }
    }

    fun showInvalidQRError() {
        _scanState.value = Resource.Error(Exception("Invalid QR code format"))
        hasScanned = false
        isProcessing = false
    }

    fun clearCustomerInfo() {
        _customerInfo.value = null
        hasScanned = false
        isProcessing = false
        _scanState.value = Resource.None
        _awardPointsState.value = Resource.None
    }

    fun resetScanState() {
        hasScanned = false
        isProcessing = false
        _scanState.value = Resource.None
    }
}

