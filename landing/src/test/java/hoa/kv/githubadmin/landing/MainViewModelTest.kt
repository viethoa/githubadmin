package hoa.kv.githubadmin.landing

import hoa.kv.githubadmin.landing.MainViewModel.Companion.PAGE_SIZE
import hoa.kv.githubadmin.repository.ApiResponse
import hoa.kv.githubadmin.repository.ApiResponse.HttpRequestException
import hoa.kv.githubadmin.repository.ApiResponse.NoNetworkException
import hoa.kv.githubadmin.repository.model.User
import hoa.kv.githubadmin.repository.user.UserRepository
import hoa.kv.githubadmin.systemdesign.R
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class MainViewModelTest : BaseViewModelTest() {
    private val userRepository: UserRepository = mockk(relaxed = true)
    private lateinit var viewModel: MainViewModel
    private val tessalol = User(
        100000001,
        "Tessalol",
        "https://avatars.githubusercontent.com/u/100000001?v=4",
        "https://github.com/Tessalol"
    )
    private val araujowill = User(
        100000002,
        "Araujowill",
        "https://avatars.githubusercontent.com/u/100000002?v=4",
        "https://github.com/Araujowill"
    )

    @Test
    fun `verify constant value`() {
        viewModel = MainViewModel(userRepository)
        assertEquals(20, PAGE_SIZE)
        assertEquals(0, viewModel.sinceParam.value)
    }

    @Test
    fun `Should trigger load users immediately when ViewModel is created`() {
        val users = listOf(tessalol, araujowill)
        val expectedUiState = MainUiState.GetUsersSuccess(users)
        coEvery { userRepository.getUsers(PAGE_SIZE, 0) } returns ApiResponse.Success(users)

        viewModel = MainViewModel(userRepository)

        assertEquals(expectedUiState, viewModel.uiState.value)
        assertEquals(users, viewModel.users)
        coVerify(exactly = 1) { userRepository.getUsers(PAGE_SIZE, 0) }
        confirmVerified(userRepository)
    }

    @Test
    fun `Verify get users with no network error`() {
        val expectedUiState = MainUiState.GetUsersError(R.string.no_network_connection)
        coEvery { userRepository.getUsers(PAGE_SIZE, 0) } returns ApiResponse.Error(NoNetworkException)

        viewModel = MainViewModel(userRepository)
        viewModel.users.clear()

        assertEquals(expectedUiState, viewModel.uiState.value)
        assertEquals(emptyList<User>(), viewModel.users)
        coVerify(exactly = 1) { userRepository.getUsers(PAGE_SIZE, 0) }
        confirmVerified(userRepository)
    }

    @Test
    fun `getMoreUsers successful`() {
        val users = listOf(tessalol, araujowill)
        val expectedUsers = listOf(tessalol, araujowill, tessalol, araujowill)
        val expectedUiState = MainUiState.GetUsersSuccess(expectedUsers)
        coEvery { userRepository.getUsers(any(), any()) } returns ApiResponse.Success(users)

        viewModel = MainViewModel(userRepository)
        viewModel.getMoreUsers()

        assertEquals(expectedUiState, viewModel.uiState.value)
        assertEquals(expectedUsers, viewModel.users)
        // Call when init ViewModel
        coVerify(exactly = 1) { userRepository.getUsers(PAGE_SIZE, 0) }
        // Call when load more users
        coVerify(exactly = 1) { userRepository.getUsers(PAGE_SIZE, PAGE_SIZE) }
        confirmVerified(userRepository)
    }

    @Test
    fun `getMoreUsers got http exception`() {
        val expectedUiState = MainUiState.GetUsersError(R.string.something_when_wrong)
        coEvery { userRepository.getUsers(any(), any()) } returns ApiResponse.Error(HttpRequestException("TEST"))

        viewModel = MainViewModel(userRepository)
        viewModel.getMoreUsers()

        assertEquals(expectedUiState, viewModel.uiState.value)
        assertEquals(emptyList<User>(), viewModel.users)
        // Call when init ViewModel
        coVerify(exactly = 1) { userRepository.getUsers(PAGE_SIZE, 0) }
        // Call when load more users
        coVerify(exactly = 1) { userRepository.getUsers(PAGE_SIZE, PAGE_SIZE) }
        confirmVerified(userRepository)
    }
}