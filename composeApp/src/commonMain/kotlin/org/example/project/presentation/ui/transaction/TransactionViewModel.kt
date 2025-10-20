package org.example.project.presentation.ui.transaction

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.api.ApiEndpoints
import org.example.project.data.api.HttpMethod
import org.example.project.data.mockresponses.getMockTransaction
import org.example.project.data.mockresponses.getMockTransactionsList
import org.example.project.domain.RemoteRepository
import org.example.project.domain.models.CreateTransactionRequest
import org.example.project.domain.models.Resource
import org.example.project.domain.models.TransactionResponse
import org.example.project.presentation.common.BaseViewModel
import org.example.project.presentation.common.constent.GlobalVar

class TransactionViewModel(
    private val remoteRepository: RemoteRepository
) : BaseViewModel() {

    // All Transactions State
    private val _transactionsListState = MutableStateFlow<Resource<List<TransactionResponse>>>(Resource.None)
    val transactionsListState: StateFlow<Resource<List<TransactionResponse>>> = _transactionsListState.asStateFlow()

    // Single Transaction State
    private val _transactionDetailState = MutableStateFlow<Resource<TransactionResponse>>(Resource.None)
    val transactionDetailState: StateFlow<Resource<TransactionResponse>> = _transactionDetailState.asStateFlow()

    // Create Transaction State
    private val _createTransactionState = MutableStateFlow<Resource<TransactionResponse>>(Resource.None)
    val createTransactionState: StateFlow<Resource<TransactionResponse>> = _createTransactionState.asStateFlow()

    // Update Transaction State
    private val _updateTransactionState = MutableStateFlow<Resource<TransactionResponse>>(Resource.None)
    val updateTransactionState: StateFlow<Resource<TransactionResponse>> = _updateTransactionState.asStateFlow()

    // Delete Transaction State
    private val _deleteTransactionState = MutableStateFlow<Resource<Unit>>(Resource.None)
    val deleteTransactionState: StateFlow<Resource<Unit>> = _deleteTransactionState.asStateFlow()

    // Processed transactions for UI
    private val _transactions = MutableStateFlow<List<TransactionHistoryData>>(emptyList())
    val transactions: StateFlow<List<TransactionHistoryData>> = _transactions.asStateFlow()

    /**
     * Load all transactions
     */
    fun loadTransactions(useMock: Boolean = GlobalVar.isMock) {
        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = null,
                endpoint = ApiEndpoints.TRANSACTIONS,
                httpMethod = HttpMethod.GET,
                isMock = useMock
            ).collectAsResource<List<TransactionResponse>>(
                onEmit = { result ->
                    _transactionsListState.value = result
                    if (result is Resource.Success) {
                        processTransactions(result.data)
                    }
                },
                useMock = useMock,
                mockResponse = getMockTransactionsList()
            )
        }
    }

    /**
     * Load transaction by ID
     */
    fun loadTransactionById(transactionId: String, useMock: Boolean = GlobalVar.isMock) {
        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = null,
                endpoint = ApiEndpoints.transactionById(transactionId),
                httpMethod = HttpMethod.GET,
                isMock = useMock
            ).collectAsResource<TransactionResponse>(
                onEmit = { result ->
                    _transactionDetailState.value = result
                },
                useMock = useMock,
                mockResponse = getMockTransaction()
            )
        }
    }

    /**
     * Create new transaction
     */
    fun createTransaction(request: CreateTransactionRequest, useMock: Boolean = GlobalVar.isMock) {
        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = request,
                endpoint = ApiEndpoints.TRANSACTIONS,
                httpMethod = HttpMethod.POST,
                isMock = useMock
            ).collectAsResource<TransactionResponse>(
                onEmit = { result ->
                    _createTransactionState.value = result
                    if (result is Resource.Success) {
                        // Reload transactions after successful creation
                        loadTransactions(useMock)
                    }
                },
                useMock = useMock,
                mockResponse = getMockTransaction()
            )
        }
    }

    /**
     * Update existing transaction
     */
    fun updateTransaction(
        transactionId: String,
        request: CreateTransactionRequest,
        useMock: Boolean = GlobalVar.isMock
    ) {
        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = request,
                endpoint = ApiEndpoints.transactionById(transactionId),
                httpMethod = HttpMethod.PUT,
                isMock = useMock
            ).collectAsResource<TransactionResponse>(
                onEmit = { result ->
                    _updateTransactionState.value = result
                    if (result is Resource.Success) {
                        // Reload transactions after successful update
                        loadTransactions(useMock)
                    }
                },
                useMock = useMock,
                mockResponse = getMockTransaction()
            )
        }
    }

    /**
     * Delete transaction
     */
    fun deleteTransaction(transactionId: String, useMock: Boolean = GlobalVar.isMock) {
        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = null,
                endpoint = ApiEndpoints.transactionById(transactionId),
                httpMethod = HttpMethod.DELETE,
                isMock = useMock
            ).collectAsResource<Unit>(
                onEmit = { result ->
                    _deleteTransactionState.value = result
                    if (result is Resource.Success) {
                        // Reload transactions after successful deletion
                        loadTransactions(useMock)
                    }
                },
                useMock = useMock,
                mockResponse = Unit
            )
        }
    }

    /**
     * Process transactions and convert to UI model
     */
    private fun processTransactions(transactionsList: List<TransactionResponse>) {
        _transactions.value = transactionsList.map { transaction ->
            TransactionHistoryData(
                id = transaction.id?:"0",
                customerName = "${transaction.userName.take(8)}", // You may need to fetch user details
                points = transaction.points?:0,
                dateTime = formatDateTime(transaction.createdAt?:""),
                type = if (transaction.points!! > 0) "awarded" else "redeemed",
                description = if (transaction.coupon != null) "Coupon redeemed" else "Points awarded",
                outletName = "Outlet ${transaction.outlet?.take(8)}" // You may need to fetch outlet details
            )
        }
    }

    /**
     * Format ISO datetime to readable format
     */
    private fun formatDateTime(isoDateTime: String): String {
        return try {
            // Basic formatting - you can enhance this with proper date formatting
            isoDateTime.replace("T", " ").substringBefore(".")
        } catch (e: Exception) {
            isoDateTime
        }
    }

    /**
     * Filter transactions by date range
     */
    fun filterByDateRange(startDate: String, endDate: String) {
        val currentList = (_transactionsListState.value as? Resource.Success)?.data ?: return
        val filtered = currentList.filter { transaction ->
            transaction.createdAt !!>= startDate && transaction.createdAt <= endDate
        }
        processTransactions(filtered)
    }

    /**
     * Filter transactions by outlet
     */
    fun filterByOutlet(outletId: String) {
        val currentList = (_transactionsListState.value as? Resource.Success)?.data ?: return
        val filtered = currentList.filter { it.outlet == outletId }
        processTransactions(filtered)
    }

    /**
     * Search transactions
     */
    fun searchTransactions(query: String) {
        val currentList = (_transactionsListState.value as? Resource.Success)?.data ?: return
        val filtered = currentList.filter { transaction ->
            transaction.id?.contains(query, ignoreCase = true) == true ||
                    transaction.user.contains(query, ignoreCase = true) ||
                    transaction.outlet?.contains(query, ignoreCase = true) == true
        }
        processTransactions(filtered)
    }

    /**
     * Clear all states
     */
    fun clearAllStates() {
        _transactionsListState.value = Resource.None
        _transactionDetailState.value = Resource.None
        _createTransactionState.value = Resource.None
        _updateTransactionState.value = Resource.None
        _deleteTransactionState.value = Resource.None
        _transactions.value = emptyList()
    }

    /**
     * Refresh transactions
     */
    fun refreshTransactions(useMock: Boolean = GlobalVar.isMock) {
        loadTransactions(useMock)
    }


}