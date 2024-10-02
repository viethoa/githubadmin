package hoa.kv.githubadmin.repositoryimpl.user

import hoa.kv.githubadmin.repository.model.User
import hoa.kv.githubadmin.repository.utils.ResourceUtils
import hoa.kv.githubadmin.repository.utils.toUserMap
import hoa.kv.githubadmin.repositoryimpl.base.NetworkBaseTest
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.HttpMethod
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class UserRemoteResourceTest : NetworkBaseTest() {
    private val tessalol = User(
        ResourceUtils
            .getMockData("user_tessalol.json")
            .toUserMap()
    )
    private val araujowill = User(
        ResourceUtils
            .getMockData("user_araujowill.json")
            .toUserMap()
    )

    @Test
    fun `getUsers with expected result`() = runTest {
        val engine = MockEngine { request ->
            handleGetUsersRequest(request, "users.json", 20, 1)
        }
        val resource = UserRemoteResource(engine)
        val expectedResult = listOf(tessalol, araujowill)
        val result = resource.getUsers(20, 1)
        assertEquals(expectedResult, result)
    }

    @Test
    fun `getUsers with empty result`() = runTest {
        val engine = MockEngine { request ->
            handleGetUsersRequest(request, "emptyList.json", 20, 1)
        }
        val resource = UserRemoteResource(engine)
        val result = resource.getUsers(20, 1)
        assertEquals(emptyList<User>(), result)
    }

    @Test
    fun `getUser with expected result`() = runTest {
        val engine = MockEngine { request ->
            handleGetUserRequest(request, "user_tessalol.json", "Tessalol")
        }
        val resource = UserRemoteResource(engine)
        val result = resource.getUser("Tessalol")
        assertEquals(tessalol, result)
    }

    private fun MockRequestHandleScope.handleGetUserRequest(
        request: HttpRequestData,
        resourceFileName: String,
        loginName: String
    ): HttpResponseData {
        if (request.method != HttpMethod.Get) {
            return badRequest()
        }
        if (!request.url.encodedPath.endsWith("/users/$loginName")) {
            return badRequest()
        }

        return respond(resourceFileName)
    }

    private fun MockRequestHandleScope.handleGetUsersRequest(
        request: HttpRequestData,
        resourceFileName: String,
        perPage: Int,
        since: Int
    ): HttpResponseData {
        if (request.method != HttpMethod.Get) {
            return badRequest()
        }
        if (!request.url.encodedPath.contains("/users")) {
            return badRequest()
        }
        val paramPerPage = request.url.parameters["per_page"] ?: ""
        val paramSince = request.url.parameters["since"] ?: ""
        if (paramPerPage != perPage.toString() || paramSince != since.toString()) {
            return badRequest()
        }

        return respond(resourceFileName)
    }
}