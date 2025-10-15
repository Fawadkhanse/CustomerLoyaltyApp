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