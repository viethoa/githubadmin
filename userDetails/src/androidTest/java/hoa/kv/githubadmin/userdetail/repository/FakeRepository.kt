package hoa.kv.githubadmin.userdetail.repository

import hoa.kv.githubadmin.repository.ApiResponse
import hoa.kv.githubadmin.repository.model.User
import hoa.kv.githubadmin.repository.user.UserRepository
import hoa.kv.githubadmin.userdetail.utils.AssetUtils
import hoa.kv.githubadmin.userdetail.utils.toUserMap

class FakeRepository : UserRepository {

    override suspend fun getUsers(perPage: Int, since: Int): ApiResponse<List<User>> {
        return ApiResponse.Success(emptyList())
    }

    override suspend fun getUser(loginName: String): ApiResponse<User> {
        return when (loginName) {
            "Tessalol" -> {
                val user = User(
                    AssetUtils
                        .getMockData("user_tessalol_details.json")
                        .toUserMap()
                )
                ApiResponse.Success(user)
            }
            else -> {
                ApiResponse.Error(Exception("Not yet mock this user $loginName"))
            }
        }
    }
}