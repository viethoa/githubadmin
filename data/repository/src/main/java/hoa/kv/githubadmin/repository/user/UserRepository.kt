package hoa.kv.githubadmin.repository.user

import hoa.kv.githubadmin.repository.ApiResponse
import hoa.kv.githubadmin.repository.model.User

interface UserRepository {

    /**
     * Get list of user
     * Will get from localDB first, if not exist then query from remote.
     * The result then also story into local for next time query.
     *
     * @param perPage how many user will be returned per fetched.
     * Minimum is 1, if lower than zero will got [ApiResponse.Error] with [IllegalArgumentException]
     * @param since star position of the list (inclusive).
     * Begin is 0, if negative will got [ApiResponse.Error] with [IllegalArgumentException]
     *
     * E.g We have 100 users from 0..100.
     * Request: PerPage: 5, Since: 10. Then return 5 users start from 10 (inclusive),
     * So result should be 10..14
     *
     * @return [ApiResponse] for the list of [User]
     */
    suspend fun getUsers(perPage: Int, since: Int): ApiResponse<List<User>>

    /**
     * Get user details from remote (not caching data in local).
     *
     * @param loginName is login username, it should not be blank or empty.
     * Otherwise, [ApiResponse.Error] with [IllegalArgumentException]
     *
     * @return [ApiResponse] for a single [User] with details
     */
    suspend fun getUser(loginName: String): ApiResponse<User>
}