package org.example.project.presentation.ui.coupons

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.api.ApiEndpoints
import org.example.project.data.api.HttpMethod
import org.example.project.domain.RemoteRepository
import org.example.project.domain.models.*
import org.example.project.presentation.common.BaseViewModel

class CouponViewModel(
    private val remoteRepository: RemoteRepository
) : BaseViewModel() {

    // Coupons List State
    private val _couponsListState = MutableStateFlow<Resource<CouponsListResponse>>(Resource.None)
    val couponsListState: StateFlow<Resource<CouponsListResponse>> = _couponsListState.asStateFlow()

    // Available Coupons (processed)
    private val _availableCoupons = MutableStateFlow<List<CouponData>>(emptyList())
    val availableCoupons: StateFlow<List<CouponData>> = _availableCoupons.asStateFlow()

    // Redeemed Coupons (processed)
    private val _redeemedCoupons = MutableStateFlow<List<RedeemedCouponData>>(emptyList())
    val redeemedCoupons: StateFlow<List<RedeemedCouponData>> = _redeemedCoupons.asStateFlow()

    // Coupon Detail State
    private val _couponDetailState = MutableStateFlow<Resource<CouponDetails>>(Resource.None)
    val couponDetailState: StateFlow<Resource<CouponDetails>> = _couponDetailState.asStateFlow()

    // Redeem Coupon State
    private val _redeemCouponState = MutableStateFlow<Resource<RedeemCouponResponse>>(Resource.None)
    val redeemCouponState: StateFlow<Resource<RedeemCouponResponse>> = _redeemCouponState.asStateFlow()

    // Current coupon data cache
    private val _currentCoupon = MutableStateFlow<CouponDetails?>(null)
    val currentCoupon: StateFlow<CouponDetails?> = _currentCoupon.asStateFlow()

    /**
     * Load all coupons (available and redeemed)
     */
    fun loadCoupons(useMock: Boolean = false) {
        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = null,
                endpoint = ApiEndpoints.GET_ALL_Customer_COUPON,
                httpMethod = HttpMethod.GET,
                isMock = useMock
            ).collectAsResource<CouponsListResponse>(
                onEmit = { result ->
                    _couponsListState.value = result
                    if (result is Resource.Success) {
                        processCouponsData(result.data)
                    }
                },
                useMock = useMock,
                mockResponse = org.example.project.data.mockresponses.mockCouponsList
            )
        }
    }

    /**
     * Process coupons data and separate into available and redeemed
     */
    private fun processCouponsData(response: CouponsListResponse) {
        // Process available coupons
        val available = response.availableCoupons?.map { coupon ->
            CouponData(
                id = coupon.id ?: "",
                title = coupon.title ?: "",
                description = coupon.description ?: "",
                pointsRequired = coupon.pointsRequired ?: 0,
                expiryDate = coupon.expiryDate ?: "",
                isRedeemable = coupon.status?.lowercase() == "active"
            )
        } ?: emptyList()

        _availableCoupons.value = available

        // Process redeemed coupons
        val redeemed = response.redeemedCoupons?.map { coupon ->
            RedeemedCouponData(
                id = coupon.id ?: "",
                title = coupon.title ?: "",
                redeemedDate = coupon.redeemedDate ?: "",
                status = coupon.status ?: "Used"
            )
        } ?: emptyList()

        _redeemedCoupons.value = redeemed
    }

    /**
     * Load coupon details by ID
     */
    fun loadCouponDetails(couponId: String, useMock: Boolean = false) {
        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = null,
                endpoint = ApiEndpoints.couponById(couponId),
                httpMethod = HttpMethod.GET,
                isMock = useMock
            ).collectAsResource<CouponDetails>(
                onEmit = { result ->
                    _couponDetailState.value = result
                    if (result is Resource.Success) {
                        _currentCoupon.value = result.data
                    }
                },
                useMock = useMock,
                mockResponse = org.example.project.data.mockresponses.mockCouponDetail
            )
        }
    }

    /**
     * Redeem a coupon
     */
    fun redeemCoupon(couponId: String, useMock: Boolean = false) {
        val request = RedeemCouponRequest(couponId = couponId)

        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = request,
                endpoint = ApiEndpoints.REDEEM_COUPON,
                httpMethod = HttpMethod.POST,
                isMock = useMock
            ).collectAsResource<RedeemCouponResponse>(
                onEmit = { result ->
                    _redeemCouponState.value = result
                },
                useMock = useMock,
                mockResponse = org.example.project.data.mockresponses.mockRedeemCouponResponse
            )
        }
    }

    /**
     * Clear coupon detail state
     */
    fun clearCouponDetailState() {
        _couponDetailState.value = Resource.None
    }

    /**
     * Clear redeem state
     */
    fun clearRedeemState() {
        _redeemCouponState.value = Resource.None
    }

    /**
     * Clear all states
     */
    fun clearAllStates() {
        _couponsListState.value = Resource.None
        _couponDetailState.value = Resource.None
        _redeemCouponState.value = Resource.None
        _currentCoupon.value = null
        _availableCoupons.value = emptyList()
        _redeemedCoupons.value = emptyList()
    }

    /**
     * Refresh coupons list
     */
    fun refreshCoupons(useMock: Boolean = false) {
        loadCoupons(useMock)
    }
}