package hoa.kv.githubadmin.repositoryimpl.user

import hoa.kv.githubadmin.repository.ApiResponse
import hoa.kv.githubadmin.repository.model.User
import hoa.kv.githubadmin.repository.user.UserRepository
import hoa.kv.githubadmin.repositoryimpl.network.toApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val userLocalResource: UserLocalResource,
    private val userRemoteResource: UserRemoteResource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserRepository {

    override suspend fun getUsers(perPage: Int, since: Int): ApiResponse<List<User>> = withContext(ioDispatcher) {
        if (perPage <= 0 || since < 0) {
            return@withContext ApiResponse.Error(IllegalArgumentException("Illegal argument please check perPage: $perPage since: $since"))
        }

        try {
            val cacheUsers = userLocalResource.getUsers(perPage, since)
            if (cacheUsers.isNotEmpty()) {
                return@withContext ApiResponse.Success(cacheUsers)
            }

            userRemoteResource
                .getUsers(perPage, since)
                .also { users ->
                    userLocalResource.insertUsers(users, since)
                }
                .let { users ->
                    ApiResponse.Success(users)
                }
        } catch (throwable: Throwable) {
            throwable.toApiResponse()
        }
    }

    override suspend fun getUser(loginName: String): ApiResponse<User> = withContext(ioDispatcher) {
        if (loginName.isEmpty()) {
            return@withContext ApiResponse.Error(IllegalArgumentException("Login name cannot be empty"))
        }

        try {
            val user = userRemoteResource.getUser(loginName)
            ApiResponse.Success(user)
        } catch (throwable: Throwable) {
            throwable.toApiResponse()
        }
    }
}