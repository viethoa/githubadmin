package hoa.kv.githubadmin.systemdesign

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import hoa.kv.githubadmin.systemdesign.theme.GithubAdminTheme
import hoa.kv.githubadmin.systemdesign.usercard.UserCardView
import hoa.kv.githubadmin.systemdesign.usercard.UserCardViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserCardViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun verifyCardShowRightData() {
        val cardData = UserCardViewModel(
            name = "user name",
            avatarUrl = "user avatar url",
            landingPageUrl = "landing page url",
            location = "my location"
        )
        composeTestRule.setContent {
            GithubAdminTheme {
                UserCardView(data = cardData)
            }
        }
        composeTestRule
            .onNodeWithContentDescription(cardData.name)
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag(cardData.name, useUnmergedTree = true)
            .assertTextEquals(cardData.name)
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag(cardData.landingPageUrl!!, useUnmergedTree = true)
            .assertTextEquals(cardData.landingPageUrl!!)
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag(cardData.location!!, useUnmergedTree = true)
            .assertTextEquals(cardData.location!!)
            .assertIsDisplayed()
    }
}