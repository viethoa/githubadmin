package hoa.kv.githubadmin.landing

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollToNode
import androidx.test.ext.junit.runners.AndroidJUnit4
import hoa.kv.githubadmin.landing.rule.KoinTestRule
import hoa.kv.githubadmin.landing.tag.MainScreenTestTag
import hoa.kv.githubadmin.systemdesign.theme.GithubAdminTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainScreenTest {

    @get:Rule
    val koinTestRule by lazy { KoinTestRule() }

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun verifyCanSeeListUserInMainScreen() {
        composeTestRule.setContent {
            GithubAdminTheme {
                MainView { /* no-operation */ }
            }
        }
        composeTestRule
            .onNodeWithTag(MainScreenTestTag.USER_LAZY_COLUMN, useUnmergedTree = true)
            .performScrollToNode(
                // Scroll to last user of first page
                (hasText("fanvsfan") and hasTestTag("fanvsfan"))
            )
            .assertIsDisplayed()
    }

    @Test
    fun verifyLoadMoreUsersWhenScrollToBottom() {
        composeTestRule.setContent {
            GithubAdminTheme {
                MainView { /* no-operation */ }
            }
        }
        composeTestRule
            .onNodeWithTag(MainScreenTestTag.USER_LAZY_COLUMN, useUnmergedTree = true)
            .performScrollToNode(
                // Scroll to random user of second page
                (hasText("nitay") and hasTestTag("nitay"))
            )
            .assertIsDisplayed()
    }
}