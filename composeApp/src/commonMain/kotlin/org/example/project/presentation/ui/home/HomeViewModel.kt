package org.example.project.presentation.ui.home


import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.api.ApiEndpoints
import org.example.project.data.api.HttpMethod
import org.example.project.data.mockresponses.mockResponse
import org.example.project.domain.RemoteRepository
import org.example.project.domain.models.Resource

import org.example.project.domain.models.home.CustomerHomeResponse
import org.example.project.presentation.common.BaseViewModel
import org.example.project.presentation.common.constent.GlobalVar.isMock
import org.example.project.presentation.ui.coupons.CouponData

class HomeViewModel(
    private val remoteRepository: RemoteRepository
) : BaseViewModel() {

    private val _homeState = MutableStateFlow<Resource<CustomerHomeResponse>>(Resource.None)
    val homeState: StateFlow<Resource<CustomerHomeResponse>> = _homeState.asStateFlow()

    // Processed data states for the UI
    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val _userPoints = MutableStateFlow(0)
    val userPoints: StateFlow<Int> = _userPoints.asStateFlow()

    private val _userTier = MutableStateFlow("")
    val userTier: StateFlow<String> = _userTier.asStateFlow()

    private val _promotions = MutableStateFlow<List<PromotionData>>(emptyList())
    val promotions: StateFlow<List<PromotionData>> = _promotions.asStateFlow()

    private val _availableCoupons = MutableStateFlow<List<CouponData>>(emptyList())
    val availableCoupons: StateFlow<List<CouponData>> = _availableCoupons.asStateFlow()

    private val _recentActivity = MutableStateFlow<List<ActivityData>>(emptyList())
    val recentActivity: StateFlow<List<ActivityData>> = _recentActivity.asStateFlow()

    // Add token management
    private var authToken: String? = null

    init {
        loadHomeData()
    }

    fun loadHomeData() {
        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = null,
                endpoint = ApiEndpoints.CUSTOMER_HOME,
                httpMethod = HttpMethod.GET
            ).collectAsResource<CustomerHomeResponse>(
                onEmit = { _homeState.value = it

                         },
                mockResponse = mockResponse,   // your fake data
                useMock = isMock               // flag from ViewModel or BuildConfig
            )
        }
    }

    fun refreshHome() {
        loadHomeData()
    }

     fun processHomeData(response: CustomerHomeResponse) {
         response.data?.let { data ->
             // Update user info
             _userName.value = data.user?.name ?: ""
             _userPoints.value = data.user?.points ?: 0
             _userTier.value = data.user?.tier ?: ""

             // Convert and update promotions
             _promotions.value = data.promotions?.mapNotNull { promotion ->
                 promotion.toPromotionData()
             } ?: emptyList()

             // Convert and update coupons
             _availableCoupons.value = data.availableCoupons?.mapNotNull { coupon ->
                 coupon.toCouponData()
             } ?: emptyList()

             // Convert and update activities
             _recentActivity.value = data.recentActivity?.mapNotNull { activity ->
                 activity.toActivityData()
             } ?: emptyList()
         }
    }


    fun clearState() {
        _homeState.value = Resource.None
        _userName.value = ""
        _userPoints.value = 0
        _userTier.value = ""
        _promotions.value = emptyList()
        _availableCoupons.value = emptyList()
        _recentActivity.value = emptyList()
    }



}
