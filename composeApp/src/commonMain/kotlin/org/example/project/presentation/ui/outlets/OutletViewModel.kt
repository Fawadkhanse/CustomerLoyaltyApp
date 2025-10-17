package org.example.project.presentation.ui.outlets


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
import org.example.project.presentation.common.constent.GlobalVar
import kotlin.math.*

class OutletViewModel(
    private val remoteRepository: RemoteRepository
) : BaseViewModel() {

    // All Outlets State
    private val _outletsListState = MutableStateFlow<Resource<List<OutletResponse>>>(Resource.None)
    val outletsListState: StateFlow<Resource<List<OutletResponse>>> = _outletsListState.asStateFlow()

    // Single Outlet State
    private val _outletDetailState = MutableStateFlow<Resource<OutletResponse>>(Resource.None)
    val outletDetailState: StateFlow<Resource<OutletResponse>> = _outletDetailState.asStateFlow()

    // Create Outlet State
    private val _createOutletState = MutableStateFlow<Resource<OutletResponse>>(Resource.None)
    val createOutletState: StateFlow<Resource<OutletResponse>> = _createOutletState.asStateFlow()

    // Update Outlet State
    private val _updateOutletState = MutableStateFlow<Resource<OutletResponse>>(Resource.None)
    val updateOutletState: StateFlow<Resource<OutletResponse>> = _updateOutletState.asStateFlow()

    // Delete Outlet State
    private val _deleteOutletState = MutableStateFlow<Resource<Unit>>(Resource.None)
    val deleteOutletState: StateFlow<Resource<Unit>> = _deleteOutletState.asStateFlow()

    // Current outlet being viewed/edited
    private val _currentOutlet = MutableStateFlow<OutletResponse?>(null)
    val currentOutlet: StateFlow<OutletResponse?> = _currentOutlet.asStateFlow()
    // Search query
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Map view toggle
    private val _isMapView = MutableStateFlow(false)
    val isMapView: StateFlow<Boolean> = _isMapView.asStateFlow()

    // Selected outlet
    private val _selectedOutlet = MutableStateFlow<OutletMapData?>(null)
    val selectedOutlet: StateFlow<OutletMapData?> = _selectedOutlet.asStateFlow()

    // User location
    private val _userLocation = MutableStateFlow<LocationData?>(null)
    val userLocation: StateFlow<LocationData?> = _userLocation.asStateFlow()

    // Processed outlets for UI
    private val _outlets = MutableStateFlow<List<OutletMapData>>(emptyList())
    val outlets: StateFlow<List<OutletMapData>> = _outlets.asStateFlow()

    /**
     * Load all outlets
     */
    fun loadOutlets(useMock: Boolean = GlobalVar.isMock) {
        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = null,
                endpoint = ApiEndpoints.GET_ALL_OUTLETS,
                httpMethod = HttpMethod.GET,
                isMock = useMock
            ).collectAsResource<List<OutletResponse>>(
                onEmit = { result ->
                    _outletsListState.value = result
                },
                useMock = useMock,
                mockResponse = getMockOutletsList()
            )
        }
    }

    /**
     * Load outlet by ID
     */
    fun loadOutletById(outletId: String, useMock: Boolean = GlobalVar.isMock) {
        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = null,
                endpoint = ApiEndpoints.outletById(outletId),
                httpMethod = HttpMethod.GET,
                isMock = useMock
            ).collectAsResource<OutletResponse>(
                onEmit = { result ->
                    _outletDetailState.value = result
                    if (result is Resource.Success) {
                        _currentOutlet.value = result.data
                    }
                },
                useMock = useMock,
                mockResponse = getMockOutlet()
            )
        }
    }

    /**
     * Create new outlet
     */
    fun createOutlet(request: CreateOutletRequest, useMock: Boolean = GlobalVar.isMock) {
        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = request,
                endpoint = ApiEndpoints.OUTLETS_DETAIL,
                httpMethod = HttpMethod.POST,
                isMock = useMock
            ).collectAsResource<OutletResponse>(
                onEmit = { result ->
                    _createOutletState.value = result
                    if (result is Resource.Success) {
                        loadOutlets(useMock)
                    }
                },
                useMock = useMock,
                mockResponse = getMockOutlet()
            )
        }
    }

    /**
     * Update existing outlet
     */
    fun updateOutlet(
        outletId: String,
        request: UpdateOutletRequest,
        useMock: Boolean = GlobalVar.isMock
    ) {
        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = request,
                endpoint = ApiEndpoints.outletById(outletId),
                httpMethod = HttpMethod.PUT,
                isMock = useMock
            ).collectAsResource<OutletResponse>(
                onEmit = { result ->
                    _updateOutletState.value = result
                    if (result is Resource.Success) {
                        _currentOutlet.value = result.data
                        loadOutlets(useMock)
                    }
                },
                useMock = useMock,
                mockResponse = getMockOutlet()
            )
        }
    }

    /**
     * Delete outlet
     */
    fun deleteOutlet(outletId: String, useMock: Boolean = GlobalVar.isMock) {
        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = null,
                endpoint = ApiEndpoints.outletById(outletId),
                httpMethod = HttpMethod.DELETE,
                isMock = useMock
            ).collectAsResource<Unit>(
                onEmit = { result ->
                    _deleteOutletState.value = result
                    if (result is Resource.Success) {
                        loadOutlets(useMock)
                    }
                },
                useMock = useMock,
                mockResponse = Unit
            )
        }
    }

    /**
     * Calculate distance from user location
     */

    private fun calculateDistance(lat: Double, lng: Double): Double {
        val userLat = _userLocation.value?.latitude ?: return 0.0
        val userLng = _userLocation.value?.longitude ?: return 0.0

        val earthRadius = 6371.0 // km

        val dLat = (lat - userLat).toRadians()
        val dLng = (lng - userLng).toRadians()

        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(userLat.toRadians()) * cos(lat.toRadians()) *
                sin(dLng / 2) * sin(dLng / 2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return earthRadius * c
    }

    // Extension function for clarity
    private fun Double.toRadians(): Double = this * PI / 180


    /**
     * Update user location
     */
    fun updateUserLocation(latitude: Double, longitude: Double) {
        _userLocation.value = LocationData(latitude, longitude)
        // Recalculate distances
        val currentList = (_outletsListState.value as? Resource.Success)?.data
        if (currentList != null) {
            processOutlets(currentList)
        }
    }
    private fun processOutlets(outletsList: List<OutletResponse>) {
        _outlets.value = outletsList.mapNotNull { outlet ->
            val lat = outlet.latitude?.toDoubleOrNull()
            val lng = outlet.longitude?.toDoubleOrNull()

            if (lat != null && lng != null) {
                OutletMapData(
                    id = outlet.id ?: "",
                    name = outlet.name ?: "",
                    address = "${outlet.address ?: ""}, ${outlet.city ?: ""}, ${outlet.state ?: ""}",
                    city = outlet.city ?: "",
                    phone = outlet.contactNumber ?: "",
                    latitude = lat,
                    longitude = lng,
                    distance = calculateDistance(lat, lng)
                )
            } else null
        }
    }
    /**
     * Search outlets
     */
    fun searchOutlets(query: String) {
        _searchQuery.value = query
        val currentList = (_outletsListState.value as? Resource.Success)?.data ?: return

        if (query.isBlank()) {
            processOutlets(currentList)
        } else {
            val filtered = currentList.filter { outlet ->
                outlet.name?.contains(query, ignoreCase = true) == true ||
                        outlet.address?.contains(query, ignoreCase = true) == true ||
                        outlet.city?.contains(query, ignoreCase = true) == true
            }
            processOutlets(filtered)
        }
    }

    /**
     * Toggle map view
     */
    fun toggleMapView() {
        _isMapView.value = !_isMapView.value
    }

    /**
     * Select outlet
     */
    fun selectOutlet(outlet: OutletMapData?) {
        _selectedOutlet.value = outlet
    }

    /**
     * Get nearest outlets
     */
    fun getNearestOutlets(count: Int = 5): List<OutletMapData> {
        return _outlets.value.sortedBy { it.distance }.take(count)
    }

    /**
     * Refresh outlets
     */


    /**
     * Clear states
     */
    fun clearCreateOutletState() {
        _createOutletState.value = Resource.None
    }

    fun clearUpdateOutletState() {
        _updateOutletState.value = Resource.None
    }

    fun clearDeleteOutletState() {
        _deleteOutletState.value = Resource.None
    }

    fun clearOutletDetailState() {
        _outletDetailState.value = Resource.None
        _currentOutlet.value = null
    }

    fun clearAllStates() {
        _outletsListState.value = Resource.None
        _outletDetailState.value = Resource.None
        _createOutletState.value = Resource.None
        _updateOutletState.value = Resource.None
        _deleteOutletState.value = Resource.None
        _currentOutlet.value = null
    }

    /**
     * Refresh outlets
     */
    fun refreshOutlets(useMock: Boolean = GlobalVar.isMock) {
        loadOutlets(useMock)
    }
}

// Mock data functions
private fun getMockOutletsList(): List<OutletResponse> = listOf(
    OutletResponse(
        id = "0201d2d6-c524-4821-9262-6eb9e780ec42",
        merchant = "37314e7f-9c1d-4515-bdf4-0752c8318c2b",
        name = "Outlet A",
        address = "123 St",
        city = "Isb",
        state = "ICT",
        country = "PK",
        latitude = "33.68440000",
        longitude = "73.04790000",
        contactNumber = "03001234567",
        createdAt = "2025-09-19T13:53:53.953848Z",
        updatedAt = "2025-09-19T13:53:53.953848Z"
    ),
    OutletResponse(
        id = "0201d2d6-c524-4821-9262-6eb9e780ec43",
        merchant = "37314e7f-9c1d-4515-bdf4-0752c8318c2b",
        name = "Outlet B",
        address = "456 Avenue",
        city = "Rwp",
        state = "Punjab",
        country = "PK",
        latitude = "33.59950000",
        longitude = "73.04180000",
        contactNumber = "03001234568",
        createdAt = "2025-09-20T13:53:53.953848Z",
        updatedAt = "2025-09-20T13:53:53.953848Z"
    )
)

private fun getMockOutlet(): OutletResponse = OutletResponse(
    id = "0201d2d6-c524-4821-9262-6eb9e780ec42",
    merchant = "37314e7f-9c1d-4515-bdf4-0752c8318c2b",
    name = "Outlet A",
    address = "123 St",
    city = "Isb",
    state = "ICT",
    country = "PK",
    latitude = "33.68440000",
    longitude = "73.04790000",
    contactNumber = "03001234567",
    createdAt = "2025-09-19T13:53:53.953848Z",
    updatedAt = "2025-09-19T13:53:53.953848Z"
)
// Data classes
data class OutletMapData(
    val id: String,
    val name: String,
    val address: String,
    val city: String,
    val phone: String,
    val latitude: Double,
    val longitude: Double,
    val distance: Double = 0.0
)

data class LocationData(
    val latitude: Double,
    val longitude: Double
)