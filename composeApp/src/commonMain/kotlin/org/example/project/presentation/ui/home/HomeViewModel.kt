package org.example.project.presentation.ui.home

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.api.ApiEndpoints
import org.example.project.data.api.HttpMethod
import org.example.project.data.mockresponses.mockMerchantDashboardResponse
import org.example.project.data.mockresponses.mockResponse
import org.example.project.domain.RemoteRepository
import org.example.project.domain.models.Resource
import org.example.project.domain.models.home.CustomerHomeResponse
import org.example.project.domain.models.home.MerchantDashboardData
import org.example.project.domain.models.home.MerchantDashboardResponse
import org.example.project.domain.models.home.OutletSummaryData
import org.example.project.presentation.common.BaseViewModel
import org.example.project.presentation.common.constent.GlobalVar.isMock
import org.example.project.presentation.ui.coupons.CouponData

class HomeViewModel(
    private val remoteRepository: RemoteRepository
) : BaseViewModel() {

    // Customer Home States
    private val _homeState = MutableStateFlow<Resource<CustomerHomeResponse>>(Resource.None)
    val homeState: StateFlow<Resource<CustomerHomeResponse>> = _homeState.asStateFlow()

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

    // Merchant Dashboard States
    private val _merchantDashboardState = MutableStateFlow<Resource<MerchantDashboardResponse>>(Resource.None)
    val merchantDashboardState: StateFlow<Resource<MerchantDashboardResponse>> = _merchantDashboardState.asStateFlow()

    private val _merchantInfo = MutableStateFlow<MerchantDashboardData.MerchantInfo?>(null)
    val merchantInfo: StateFlow<MerchantDashboardData.MerchantInfo?> = _merchantInfo.asStateFlow()

    private val _todayStats = MutableStateFlow<MerchantDashboardData.TodayStats?>(null)
    val todayStats: StateFlow<MerchantDashboardData.TodayStats?> = _todayStats.asStateFlow()

    private val _recentTransactions = MutableStateFlow<List<TransactionData>>(emptyList())
    val recentTransactions: StateFlow<List<TransactionData>> = _recentTransactions.asStateFlow()

    private val _activeOutlets = MutableStateFlow<List<OutletSummaryData>>(emptyList())
    val activeOutlets: StateFlow<List<OutletSummaryData>> = _activeOutlets.asStateFlow()

    // Customer Home Data Loading
    fun loadHomeData() {
        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = null,
                endpoint = ApiEndpoints.CUSTOMER_HOME,
                httpMethod = HttpMethod.GET
            ).collectAsResource<CustomerHomeResponse>(
                onEmit = { _homeState.value = it },
                mockResponse = mockResponse,
                useMock = isMock
            )
        }
    }

    fun processHomeData(response: CustomerHomeResponse) {
        response.data?.let { data ->
            _userName.value = data.user?.name ?: ""
            _userPoints.value = data.user?.points ?: 0
            _userTier.value = data.user?.tier ?: ""

            _promotions.value = data.promotions?.mapNotNull { promotion ->
                promotion.toPromotionData()
            } ?: emptyList()

            _availableCoupons.value = data.availableCoupons?.mapNotNull { coupon ->
                coupon.toCouponData()
            } ?: emptyList()

            _recentActivity.value = data.recentActivity?.mapNotNull { activity ->
                activity.toActivityData()
            } ?: emptyList()
        }
    }

    // Merchant Dashboard Data Loading
    fun loadMerchantDashboard() {
        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = null,
                endpoint = ApiEndpoints.MERCHANT_DASHBOARD,
                httpMethod = HttpMethod.GET
            ).collectAsResource<MerchantDashboardResponse>(
                onEmit = { _merchantDashboardState.value = it },
                mockResponse = mockMerchantDashboardResponse,
                useMock = isMock
            )
        }
    }

    // Add these lines to the HomeViewModel.kt after line 100 (after loadMerchantDashboard function)

    fun processMerchantDashboard(response: MerchantDashboardResponse) {
        response.data?.let { data ->
            // Set merchant info
            _merchantInfo.value = data.merchantInfo

            // Set today's stats
            _todayStats.value = data.todayStats

            // Convert recent transactions to TransactionData
            _recentTransactions.value = data.recentTransactions?.mapNotNull { transaction ->
                TransactionData(
                    id = transaction.id ?: "",
                    customerName = transaction.customerName ?: "Unknown Customer",
                    points = transaction.points ?: 0,
                    location = transaction.outletName ?: transaction.location ?: "Unknown Location",
                    timestamp = formatTimestamp(transaction.timestamp ?: "")
                )
            } ?: emptyList()

            // Convert outlet summaries with proper null handling
            _activeOutlets.value = data.activeOutlets?.mapNotNull { outlet ->
                OutletSummaryData(
                    id = outlet.id ?: return@mapNotNull null,
                    name = outlet.name ?: "Unknown Outlet",
                    location = outlet.location ?: "Unknown Location",
                    isActive = outlet.isActive ?: false,
                    scansToday = outlet.scansToday ?: 0,
                    totalCustomers = outlet.totalCustomers ?: 0
                )
            } ?: emptyList()
        }
    }

    /**
     * Format timestamp to relative time
     */
    private fun formatTimestamp(timestamp: String): String {
        return try {
            // Simple formatting - you can enhance this with proper date/time library
            // For now, just return a cleaned version
            when {
                timestamp.contains("T") -> {
                    val time = timestamp.substringAfter("T").substringBefore("Z")
                    val parts = time.split(":")
                    "${parts[0]}:${parts[1]}"
                }
                else -> timestamp
            }
        } catch (e: Exception) {
            timestamp
        }
    }

    fun refreshHome() {
        loadHomeData()
    }

    fun refreshMerchantDashboard() {
        loadMerchantDashboard()
    }

    fun clearState() {
        // Customer states
        _homeState.value = Resource.None
        _userName.value = ""
        _userPoints.value = 0
        _userTier.value = ""
        _promotions.value = emptyList()
        _availableCoupons.value = emptyList()
        _recentActivity.value = emptyList()

        // Merchant states
        _merchantDashboardState.value = Resource.None
        _merchantInfo.value = null
        _todayStats.value = null
        _recentTransactions.value = emptyList()
        _activeOutlets.value = emptyList()
    }
}

