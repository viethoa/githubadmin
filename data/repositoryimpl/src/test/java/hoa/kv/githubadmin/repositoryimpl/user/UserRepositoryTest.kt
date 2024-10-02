package hoa.kv.githubadmin.repositoryimpl.user

import hoa.kv.githubadmin.repository.ApiResponse
import hoa.kv.githubadmin.repository.ApiResponse.HttpRequestException
import hoa.kv.githubadmin.repository.ApiResponse.NoNetworkException
import hoa.kv.githubadmin.repository.model.User
import hoa.kv.githubadmin.repository.utils.ResourceUtils
import hoa.kv.githubadmin.repository.utils.toUserMap
import hoa.kv.githubadmin.repositoryimpl.base.NetworkBaseTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class UserRepositoryTest : NetworkBaseTest() {
    private val mockLocalResource: UserLocalResource = mockk(relaxed = true)
    private val mockRemoteResource: UserRemoteResource = mockk(relaxed = true)
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

    private lateinit var repository: UserRepositoryImpl

    @Test
    fun `getUsers from remote and update local`() = runTest {
        val expectedResponse = listOf(tessalol, araujowill)
        coEvery { mockLocalResource.getUsers(any(), any()) } returns emptyList()
        coEvery { mockRemoteResource.getUsers(2, 1) } returns expectedResponse
        repository = UserRepositoryImpl(mockLocalResource, mockRemoteResource)
        val response = repository.getUsers(2, 1)

        assertEquals(true, response is ApiResponse.Success)
        assertEquals(expectedResponse, (response as ApiResponse.Success).data)
        coVerify(exactly = 1) { mockRemoteResource.getUsers(2, 1) }
        coVerify(exactly = 1) { mockLocalResource.getUsers(2, 1) }
        coVerify(exactly = 1) { mockLocalResource.insertUsers(expectedResponse, 1) }
        confirmVerified(mockLocalResource, mockRemoteResource)
    }

    @Test
    fun `getUsers from remote with empty result`() = runTest {
        coEvery { mockLocalResource.getUsers(any(), any()) } returns emptyList()
        coEvery { mockRemoteResource.getUsers(2, 1) } returns emptyList()
        repository = UserRepositoryImpl(mockLocalResource, mockRemoteResource)
        val response = repository.getUsers(2, 1)

        assertEquals(true, response is ApiResponse.Success)
        assertEquals(emptyList<User>(), (response as ApiResponse.Success).data)
        coVerify(exactly = 1) { mockRemoteResource.getUsers(2, 1) }
        coVerify(exactly = 1) { mockLocalResource.getUsers(2, 1) }
        coVerify(exactly = 1) { mockLocalResource.insertUsers(emptyList(), 1) }
        confirmVerified(mockLocalResource, mockRemoteResource)
    }

    @Test
    fun `getUsers from local only`() = runTest {
        val expectedResponse = listOf(tessalol, araujowill)
        coEvery { mockLocalResource.getUsers(2, 1) } returns expectedResponse
        repository = UserRepositoryImpl(mockLocalResource, mockRemoteResource)
        val response = repository.getUsers(2, 1)

        assertEquals(true, response is ApiResponse.Success)
        assertEquals(expectedResponse, (response as ApiResponse.Success).data)
        coVerify(exactly = 0) { mockRemoteResource.getUsers(any(), any()) }
        coVerify(exactly = 1) { mockLocalResource.getUsers(2, 1) }
        confirmVerified(mockLocalResource, mockRemoteResource)
    }

    @Test
    fun `getUsers got illegal argument`() = runTest {
        repository = UserRepositoryImpl(mockLocalResource, mockRemoteResource)

        // With PerPage less than 1
        var response = repository.getUsers(0, 0)
        assertEquals(true, response is ApiResponse.Error)
        assertEquals(true, (response as ApiResponse.Error).throwable is IllegalArgumentException)
        coVerify(exactly = 0) { mockRemoteResource.getUsers(any(), any()) }
        coVerify(exactly = 0) { mockLocalResource.getUsers(any(), any()) }
        confirmVerified(mockLocalResource, mockRemoteResource)

        // With Since less than 0
        response = repository.getUsers(10, -123)
        assertEquals(true, response is ApiResponse.Error)
        assertEquals(true, (response as ApiResponse.Error).throwable is IllegalArgumentException)
        coVerify(exactly = 0) { mockRemoteResource.getUsers(any(), any()) }
        coVerify(exactly = 0) { mockLocalResource.getUsers(any(), any()) }
        confirmVerified(mockLocalResource, mockRemoteResource)
    }

    @Test
    fun `getUsers got no-network exception`() = runTest {
        coEvery { mockLocalResource.getUsers(2, 1) } returns emptyList()
        repository = UserRepositoryImpl(mockLocalResource, UserRemoteResource(mockIOExceptionEngine()))
        val response = repository.getUsers(2, 1)

        assertEquals(true, response is ApiResponse.Error)
        assertEquals(true, (response as ApiResponse.Error).throwable is NoNetworkException)
        coVerify(exactly = 1) { mockLocalResource.getUsers(2, 1) }
        confirmVerified(mockLocalResource)
    }

    @Test
    fun `getUsers got http exception`() = runTest {
        coEvery { mockLocalResource.getUsers(2, 1) } returns emptyList()
        repository = UserRepositoryImpl(mockLocalResource, UserRemoteResource(mockHttpExceptionEngine()))
        val response = repository.getUsers(2, 1)

        assertEquals(true, response is ApiResponse.Error)
        assertEquals(true, (response as ApiResponse.Error).throwable is HttpRequestException)
        coVerify(exactly = 1) { mockLocalResource.getUsers(2, 1) }
        confirmVerified(mockLocalResource)
    }

    @Test
    fun `getUser successful`() = runTest {
        val engine = getMockEngine("user_tessalol.json")
        repository = UserRepositoryImpl(mockLocalResource, UserRemoteResource(engine))
        val response = repository.getUser("TEST")

        assertEquals(true, response is ApiResponse.Success)
        assertEquals(tessalol, (response as ApiResponse.Success).data)
        confirmVerified(mockLocalResource)
    }

    @Test
    fun `getUser got no-network exception`() = runTest {
        repository = UserRepositoryImpl(mockLocalResource, UserRemoteResource(mockIOExceptionEngine()))
        val response = repository.getUser("TEST")

        assertEquals(true, response is ApiResponse.Error)
        assertEquals(true, (response as ApiResponse.Error).throwable is NoNetworkException)
        confirmVerified(mockLocalResource)
    }

    @Test
    fun `getUser got http exception`() = runTest {
        repository = UserRepositoryImpl(mockLocalResource, UserRemoteResource(mockHttpExceptionEngine()))
        val response = repository.getUser("TEST")

        assertEquals(true, response is ApiResponse.Error)
        assertEquals(true, (response as ApiResponse.Error).throwable is HttpRequestException)
        confirmVerified(mockLocalResource)
    }

    @Test
    fun `getUser got illegal argument`() = runTest {
        repository = UserRepositoryImpl(mockLocalResource, mockRemoteResource)
        val response = repository.getUser("")

        assertEquals(true, response is ApiResponse.Error)
        assertEquals(true, (response as ApiResponse.Error).throwable is IllegalArgumentException)
        confirmVerified(mockLocalResource, mockRemoteResource)
    }
}