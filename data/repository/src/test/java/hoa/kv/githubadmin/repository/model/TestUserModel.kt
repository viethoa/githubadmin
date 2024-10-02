package hoa.kv.githubadmin.repository.model

import org.junit.Assert.assertEquals
import org.junit.Test

class TestUserModel {

    @Test
    fun `create user model from UserEntity`() {
        val userEntity = UserEntity(123, "name", "avatar", "landingPage", 1231)
        val expectedUser = User(
            id = 123,
            loginName = "name",
            avatarUrl = "avatar",
            landingPageUrl = "landingPage",
            location = null,
            followers = 0,
            following = 0
        )
        assertEquals(expectedUser, User(userEntity))
    }

    @Test
    fun `create user entity from user model`() {
        val user = User(
            id = 213,
            loginName = "login",
            avatarUrl = null,
            landingPageUrl = "landingPage",
            location = null,
            followers = 123,
            following = 45
        )
        val expectUserEntity = UserEntity(
            id = 213,
            loginName = "login",
            avatarUrl = null,
            landingPageUrl = "landingPage",
            rank = 12
        )
        assertEquals(expectUserEntity, UserEntity(user, 12))
    }

    @Test
    fun `create user from empty map data`() {
        val user = User(emptyMap())
        val expectUserEntity = User(
            id = 0,
            loginName = "",
            avatarUrl = null,
            landingPageUrl = "",
            location = null,
            followers = 0,
            following = 0
        )
        assertEquals(expectUserEntity, user)
    }

    @Test
    fun `create right user from empty map data`() {
        val user = User(
            mapOf(
                "id" to 12,
                "login" to "name",
                "avatar_url" to "avatar url",
                "html_url" to "landing page url",
                "location" to "TEST",
                "following" to 120,
                "followers" to 12,
            )
        )
        val expectUserEntity = User(
            id = 12,
            loginName = "name",
            avatarUrl = "avatar url",
            landingPageUrl = "landing page url",
            location = "TEST",
            followers = 12,
            following = 120
        )
        assertEquals(expectUserEntity, user)
    }
}