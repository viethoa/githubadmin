package hoa.kv.githubadmin.userdetail

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import hoa.kv.githubadmin.repository.model.User
import hoa.kv.githubadmin.systemdesign.theme.GithubAdminTheme
import hoa.kv.githubadmin.userdetail.rule.KoinTestRule
import hoa.kv.githubadmin.userdetail.tag.UserDetailsTestTag
import hoa.kv.githubadmin.userdetail.utils.AssetUtils
import hoa.kv.githubadmin.userdetail.utils.toUserMap
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.java.KoinJavaComponent.inject

@RunWith(AndroidJUnit4::class)
class UserDetailsScreenTest {

    @get:Rule
    val koinTestRule by lazy { KoinTestRule() }

    @get:Rule
    val composeTestRule = createComposeRule()

    private val viewModel: UserDetailViewModel by inject(UserDetailViewModel::class.java)

    @Test
    fun verifyCanSeeQuickUserInformation() {
        val user = User(
            AssetUtils
                .getMockData("user_tessalol.json")
                .toUserMap()
        )
        composeTestRule.setContent {
            GithubAdminTheme {
                UserDetailsView(user = user)
            }
        }
        // Verify login name
        composeTestRule
            .onNodeWithTag("Tessalol", useUnmergedTree = true)
            .assertTextEquals("Tessalol")
            .assertIsDisplayed()
        // Verify location
        composeTestRule
            .onNodeWithText("Vietnam", useUnmergedTree = true)
            .assertDoesNotExist()
        // Verify follow information
        composeTestRule
            .onNodeWithTag(UserDetailsTestTag.USER_FOLLOWER, useUnmergedTree = true)
            .assertTextEquals("0+")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag(UserDetailsTestTag.USER_FOLLOWING, useUnmergedTree = true)
            .assertTextEquals("0+")
            .assertIsDisplayed()
        // Verify blog
        composeTestRule
            .onNodeWithTag("https://github.com/Tessalol", useUnmergedTree = true)
            .assertTextEquals("https://github.com/Tessalol")
            .assertIsDisplayed()
    }

    @Test
    fun verifyCanSeeRightUserDetailsInformation() {
        val user = User(
            AssetUtils
                .getMockData("user_tessalol.json")
                .toUserMap()
        )
        composeTestRule.setContent {
            GithubAdminTheme {
                UserDetailsView(user = user, viewModel = viewModel)
            }
            viewModel.getUser(user.loginName)
        }
        // Verify login name
        composeTestRule
            .onNodeWithTag(user.loginName, useUnmergedTree = true)
            .assertTextEquals("Tessalol")
            .assertIsDisplayed()
        // Verify location
        composeTestRule
            .onNodeWithTag("Vietnam", useUnmergedTree = true)
            .assertTextEquals("Vietnam")
            .assertIsDisplayed()
        // Verify follower
        composeTestRule
            .onNodeWithTag(UserDetailsTestTag.USER_FOLLOWER, useUnmergedTree = true)
            .assertTextEquals("100+")
            .assertIsDisplayed()
        // Verify following
        composeTestRule
            .onNodeWithTag(UserDetailsTestTag.USER_FOLLOWING, useUnmergedTree = true)
            .assertTextEquals("234+")
            .assertIsDisplayed()
        // Verify blog
        composeTestRule
            .onNodeWithTag(user.landingPageUrl, useUnmergedTree = true)
            .assertTextEquals("https://github.com/Tessalol")
            .assertIsDisplayed()
    }
}