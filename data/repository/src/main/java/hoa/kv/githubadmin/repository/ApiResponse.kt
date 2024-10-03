package hoa.kv.githubadmin.repository

/**
 * A generic class for API response which hold a generic value [T]
 * @param <T>
 */
sealed class ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error(val throwable: Throwable) : ApiResponse<Nothing>()

    /**
     * Represent for http exception such as 404, 400, 500
     * The error [ApiResponse.Error] will be type of [HttpRequestException]
     *
     * @note Just the for example application, for production need to setup in more details
     */
    data class HttpRequestException(val errorMessage: String) : Throwable(errorMessage)

    /**
     * When user make an API call without network connection
     * the error [ApiResponse.Error] will be type of [NoNetworkException]
     */
    object NoNetworkException : Throwable() {
        private fun readResolve(): Any = NoNetworkException
    }
}