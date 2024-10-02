package hoa.kv.githubadmin.userdetail

import androidx.lifecycle.testing.TestLifecycleOwner
import hoa.kv.githubadmin.repository.ApiResponse
import hoa.kv.githubadmin.repository.ApiResponse.NoNetworkException
import hoa.kv.githubadmin.repository.ApiResponse.HttpRequestException
import hoa.kv.githubadmin.repository.model.User
import hoa.kv.githubadmin.repository.user.UserRepository
import hoa.kv.githubadmin.systemdesign.R
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class UserDetailsViewModelTest : BaseViewModelTest() {
    private val testLifecycleOwner: TestLifecycleOwner = TestLifecycleOwner()
    private val userRepository: UserRepository = mockk(relaxed = true)
    private val araujowill = User(
        100000002,
        "Araujowill",
        "https://avatars.githubusercontent.com/u/100000002?v=4",
        "https://github.com/Araujowill"
    )

    private val viewModel = UserDetailViewModel(userRepository)

    @Test
    fun `getUser successful`() {
        val expectedUiState = listOf(UserDetailUiState.Loading, UserDetailUiState.GetUserSuccess(araujowill))
        coEvery { userRepository.getUser("Araujowill") } returns ApiResponse.Success(araujowill)

        val actualUiState = arrayListOf<UserDetailUiState>()
        viewModel.uiState.observe(testLifecycleOwner) { actualUiState.add(it) }
        viewModel.getUser("Araujowill")

        assertEquals(expectedUiState, actualUiState)
        coVerify(exactly = 1) { userRepository.getUser("Araujowill") }
        confirmVerified(userRepository)
    }

    @Test
    fun `getUser with no network error`() {
        val expectedUiState = listOf(UserDetailUiState.Loading, UserDetailUiState.GetUserError(R.string.no_network_connection))
        coEvery { userRepository.getUser(any()) } returns ApiResponse.Error(NoNetworkException)

        val actualUiState = arrayListOf<UserDetailUiState>()
        viewModel.uiState.observe(testLifecycleOwner) { actualUiState.add(it) }
        viewModel.getUser("Araujowill")

        assertEquals(expectedUiState, actualUiState)
        coVerify(exactly = 1) { userRepository.getUser("Araujowill") }
        confirmVerified(userRepository)
    }

    @Test
    fun `getUser got http exception`() {
        val expectedUiState = listOf(UserDetailUiState.Loading, UserDetailUiState.GetUserError(R.string.something_when_wrong))
        coEvery { userRepository.getUser(any()) } returns ApiResponse.Error(HttpRequestException("Test"))

        val actualUiState = arrayListOf<UserDetailUiState>()
        viewModel.uiState.observe(testLifecycleOwner) { actualUiState.add(it) }
        viewModel.getUser("Araujowill")

        assertEquals(expectedUiState, actualUiState)
        coVerify(exactly = 1) { userRepository.getUser("Araujowill") }
        confirmVerified(userRepository)
    }
}