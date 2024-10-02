package hoa.kv.githubadmin.repository

sealed class ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error(val throwable: Throwable) : ApiResponse<Nothing>()

    data class HttpRequestException(val errorMessage: String) : Throwable(errorMessage)
    object NoNetworkException : Throwable() {
        private fun readResolve(): Any = NoNetworkException
    }
}