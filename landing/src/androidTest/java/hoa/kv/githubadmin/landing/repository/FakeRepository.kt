package hoa.kv.githubadmin.landing.repository

import hoa.kv.githubadmin.landing.utils.AssetUtils
import hoa.kv.githubadmin.landing.utils.toListOfUserMap
import hoa.kv.githubadmin.repository.ApiResponse
import hoa.kv.githubadmin.repository.model.User
import hoa.kv.githubadmin.repository.user.UserRepository

class FakeRepository : UserRepository {
    private val users = arrayListOf<User>()

    override suspend fun getUsers(perPage: Int, since: Int): ApiResponse<List<User>> {
        if (since == 0) {
            AssetUtils
                .getMockData("users_page_1.json")
                .toListOfUserMap()
                .forEach { userMap ->
                    users.add(User(userMap))
                }
        } else {
            AssetUtils
                .getMockData("users_page_2.json")
                .toListOfUserMap()
                .forEach { userMap ->
                    users.add(User(userMap))
                }
        }

        return ApiResponse.Success(users)
    }

    override suspend fun getUser(loginName: String): ApiResponse<User> {
        return ApiResponse.Error(Exception("No supported"))
    }
}