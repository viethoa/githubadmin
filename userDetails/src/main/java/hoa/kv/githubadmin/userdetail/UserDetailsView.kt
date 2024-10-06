package hoa.kv.githubadmin.userdetail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hoa.kv.githubadmin.repository.model.User
import hoa.kv.githubadmin.systemdesign.loading.CircularLoadingProgress
import hoa.kv.githubadmin.systemdesign.theme.Black
import hoa.kv.githubadmin.systemdesign.theme.Transparent
import hoa.kv.githubadmin.systemdesign.theme.White
import hoa.kv.githubadmin.systemdesign.usercard.UserCardView
import hoa.kv.githubadmin.userdetail.tag.UserDetailsTestTag.USER_FOLLOWER
import hoa.kv.githubadmin.userdetail.tag.UserDetailsTestTag.USER_FOLLOWING
import hoa.kv.githubadmin.userdetail.utils.toUserCardViewModel
import hoa.kv.githubadmin.userdetail.widget.FollowWidget
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserDetailsView(
    modifier: Modifier = Modifier,
    viewModel: UserDetailViewModel = koinViewModel<UserDetailViewModel>(),
    user: User
) {
    var userModel by remember { mutableStateOf(user) }
    val uiState by viewModel.uiState.observeAsState()

    UserDetailsContent(user = userModel, modifier = modifier)

    when (uiState) {
        is UserDetailUiState.Loading -> CircularLoadingProgress()
        is UserDetailUiState.GetUserSuccess -> {
            userModel = (uiState as UserDetailUiState.GetUserSuccess).user
        }
        is UserDetailUiState.GetUserError -> {
            val errorMessageRes = (uiState as UserDetailUiState.GetUserError).errorMessage
            Toast.makeText(LocalContext.current, stringResource(errorMessageRes), Toast.LENGTH_SHORT).show()
        }
        else -> {
            // no-operation
        }
    }
}

@Composable
private fun UserDetailsContent(
    modifier: Modifier = Modifier,
    user: User
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        UserCardView(
            modifier = Modifier
                .padding(16.dp, 16.dp, 16.dp, 24.dp)
                .shadow(
                    elevation = 12.dp,
                    ambientColor = Black,
                    spotColor = Black,
                    shape = RoundedCornerShape(8.dp)
                ),
            titleFontSize = 20,
            data = user.toUserCardViewModel()
        )
        FollowContent(user.followers, user.following)
        BlogText(user.landingPageUrl)
    }
}

@Composable
private fun FollowContent(followers: Int, following: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 20.dp)
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.Center,
    ) {
        VerticalDivider(color = Transparent, modifier = Modifier.weight(1f))
        FollowWidget(
            iconRes = R.drawable.ic_follower,
            text = stringResource(R.string.user_detail_follower),
            number = followers,
            testTag = USER_FOLLOWER
        )
        VerticalDivider(color = Transparent, modifier = Modifier.weight(0.8f))
        FollowWidget(
            iconRes = R.drawable.ic_following,
            text = stringResource(R.string.user_detail_following),
            number = following,
            testTag = USER_FOLLOWING
        )
        VerticalDivider(color = Transparent, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun BlogText(landingPage: String) {
    Text(
        text = stringResource(R.string.user_detail_blog),
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    Text(
        text = landingPage,
        fontSize = 12.sp,
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .testTag(landingPage)
    )
}

@Preview
@Composable
fun UserDetailsContentPreview() {
    UserDetailsContent(
        user = User(
            12,
            "Hoa with text is too long for testing purpose",
            "avatar",
            "https://github.com/Tessalol-safasdf-sdf-sadfsadf-asdf-asdf-sadfsdaf-afasfsadfd-fsadf",
            "Vietnam",
            123,
            1423
        )
    )
}