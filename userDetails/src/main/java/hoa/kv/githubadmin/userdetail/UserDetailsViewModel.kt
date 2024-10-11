package hoa.kv.githubadmin.userdetail

import androidx.annotation.StringRes
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

class UserDetailViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserDetailUiState>(UserDetailUiState.Loading)
    val uiState: StateFlow<UserDetailUiState> = _uiState

    fun getUser(loginName: String) {
        viewModelScope.launch {
            _uiState.value = UserDetailUiState.Loading
            when (val response = userRepository.getUser(loginName)) {
                is ApiResponse.Success -> {
                    _uiState.value = UserDetailUiState.GetUserSuccess(response.data)
                }
                is ApiResponse.Error -> {
                    _uiState.value = UserDetailUiState.GetUserError(response.getErrorMessage())
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
}

sealed class UserDetailUiState {
    data object Loading : UserDetailUiState()
    data class GetUserSuccess(val user: User) : UserDetailUiState()
    data class GetUserError(val errorMessage: Int) : UserDetailUiState()
}