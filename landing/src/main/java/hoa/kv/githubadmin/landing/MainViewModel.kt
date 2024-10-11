package hoa.kv.githubadmin.landing

import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hoa.kv.githubadmin.repository.ApiResponse
import hoa.kv.githubadmin.repository.ApiResponse.NoNetworkException
import hoa.kv.githubadmin.repository.model.User
import hoa.kv.githubadmin.repository.user.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import hoa.kv.githubadmin.systemdesign.R as sdR

class MainViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    /**
     * Since Param for getting Users Api, begin from 0.
     */
    @VisibleForTesting
    val sinceParam = MutableStateFlow(0)

    @VisibleForTesting
    val users = arrayListOf<User>()

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState: StateFlow<MainUiState> = _uiState

    init {
        // Get use immediately when ViewModel is init
        viewModelScope.launch {
            sinceParam.collect { since ->
                performGetUsers(since)
            }
        }
    }

    fun getMoreUsers() {
        sinceParam.value += PAGE_SIZE
    }

    private fun performGetUsers(since: Int) {
        viewModelScope.launch {
            _uiState.value = MainUiState.Loading
            when (val response = userRepository.getUsers(PAGE_SIZE, since)) {
                is ApiResponse.Success -> {
                    users.addAll(response.data)
                    _uiState.value = MainUiState.GetUsersSuccess(users)
                }
                is ApiResponse.Error -> {
                    _uiState.value = MainUiState.GetUsersError(response.getErrorMessage())
                }
            }
        }
    }

    @StringRes
    private fun ApiResponse.Error.getErrorMessage(): Int {
        return when (this.throwable) {
            is NoNetworkException -> sdR.string.no_network_connection
            else -> sdR.string.something_when_wrong
        }
    }

    companion object {
        @VisibleForTesting
        const val PAGE_SIZE = 20
    }
}

sealed class MainUiState {
    data object Loading : MainUiState()
    data class GetUsersSuccess(val users: List<User>) : MainUiState()
    data class GetUsersError(val errorMessage: Int) : MainUiState()
}