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
import org.example.project.domain.models.home.CustomerHomeData
import org.example.project.domain.models.home.CustomerHomeResponse
import org.example.project.presentation.common.BaseViewModel
import org.example.project.presentation.common.constent.GlobalVar.isMock

class HomeViewModel(
    private val remoteRepository: RemoteRepository
) : BaseViewModel() {

    private val _homeState = MutableStateFlow<Resource<CustomerHomeResponse>>(Resource.None)
    val homeState: StateFlow<Resource<CustomerHomeResponse>> = _homeState.asStateFlow()

    init {
        loadHomeData()
    }

    fun loadHomeData() {
        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = null,
                endpoint = ApiEndpoints.CUSTOMER_HOME,
                httpMethod = HttpMethod.GET,
                isMock = isMock
            ).collectAsResource<CustomerHomeResponse> { result ->
                if (!isMock) {
                    _homeState.value = result
                } else {
                    _homeState.value = Resource.Success(mockResponse)
                }
            }
        }
    }

    fun refreshHome() {
        loadHomeData()
    }

    fun clearState() {
        _homeState.value = Resource.None
    }





}
