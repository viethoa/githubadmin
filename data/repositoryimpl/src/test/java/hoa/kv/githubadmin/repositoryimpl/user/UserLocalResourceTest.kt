package hoa.kv.githubadmin.repositoryimpl.user

import hoa.kv.githubadmin.repository.model.User
import hoa.kv.githubadmin.repository.model.UserEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class UserLocalResourceTest {
    private val userDao: UserDao = mockk(relaxed = true)
    private val resource = UserLocalResource(userDao)

    @Test
    fun `getUsers with expected result`() = runTest {
        val userEntities = listOf(
            UserEntity(1, "name1", "avatar1", "landingPageUrl1", 0),
            UserEntity(2, "name2", "avatar2", "landingPageUrl2", 1)
        )
        val expectedUsers = listOf(
            User(1, "name1", "avatar1", "landingPageUrl1"),
            User(2, "name2", "avatar2", "landingPageUrl2")
        )
        coEvery { userDao.getUsers(20, 0) } returns userEntities
        val result = resource.getUsers(20, 0)

        assertEquals(expectedUsers, result)
        coVerify(exactly = 1) { userDao.getUsers(20, 0) }
        confirmVerified(userDao)
    }

    @Test
    fun `getUsers got empty result`() = runTest {
        coEvery { userDao.getUsers(20, 0) } returns emptyList()
        val result = resource.getUsers(20, 0)

        assertEquals(emptyList<User>(), result)
        coVerify(exactly = 1) { userDao.getUsers(20, 0) }
        confirmVerified(userDao)
    }

    @Test
    fun `insertUsers expect user into db`() = runTest {
        val users = listOf(
            User(1, "name1", "avatar1", "landingPageUrl1"),
            User(2, "name2", "avatar2", "landingPageUrl2")
        )
        val expectedUserEntities = listOf(
            UserEntity(1, "name1", "avatar1", "landingPageUrl1", 100),
            UserEntity(2, "name2", "avatar2", "landingPageUrl2", 101)
        )
        resource.insertUsers(users, 100)

        coVerify(exactly = 1) { userDao.insertUsers(*expectedUserEntities.toTypedArray()) }
        confirmVerified(userDao)
    }

    @Test
    fun `insertUsers with empty list`() = runTest {
        resource.insertUsers(emptyList(), 100)
        coVerify(exactly = 0) { userDao.insertUsers(any()) }
        confirmVerified(userDao)
    }
}