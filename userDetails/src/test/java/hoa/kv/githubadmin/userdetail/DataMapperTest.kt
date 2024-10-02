package hoa.kv.githubadmin.userdetail

import hoa.kv.githubadmin.repository.model.User
import hoa.kv.githubadmin.systemdesign.usercard.UserCardViewModel
import hoa.kv.githubadmin.userdetail.utils.toUserCardViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class DataMapperTest {

    @Test
    fun `make sure the mapping is right`() {
        val user = User(
            id = 12,
            loginName = "name",
            avatarUrl = "avatar url",
            landingPageUrl = "landing page url",
            location = "my location",
            followers = 132,
            following = 321
        )
        val expectedUserCard = UserCardViewModel(
            name = "name",
            avatarUrl = "avatar url",
            landingPageUrl = "",
            location = "my location"
        )
        assertEquals(expectedUserCard, user.toUserCardViewModel())
    }

    @Test
    fun `avatar is empty when no data`() {
        val user = User(
            id = 12,
            loginName = "name",
            avatarUrl = null,
            landingPageUrl = "landing page url",
            location = null,
            followers = 132,
            following = 321
        )
        val expectedUserCard = UserCardViewModel(
            name = "name",
            avatarUrl = "",
            landingPageUrl = "",
            location = null
        )
        assertEquals(expectedUserCard, user.toUserCardViewModel())
    }
}