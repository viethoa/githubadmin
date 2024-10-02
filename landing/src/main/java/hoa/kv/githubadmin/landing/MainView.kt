package hoa.kv.githubadmin.landing

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hoa.kv.githubadmin.landing.tag.MainScreenTestTag
import hoa.kv.githubadmin.landing.utils.toUserCardViewModel
import hoa.kv.githubadmin.repository.model.User
import hoa.kv.githubadmin.systemdesign.lazycolumn.LazyColumnPaging
import hoa.kv.githubadmin.systemdesign.loading.CircularLoadingProgress
import hoa.kv.githubadmin.systemdesign.usercard.UserCardView
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainView(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = koinViewModel<MainViewModel>(),
    onItemUserClicked: (User) -> Unit
) {
    val users = remember { mutableStateListOf<User>() }
    val uiState by viewModel.uiState.observeAsState()

    MainViewContent(
        modifier = modifier,
        users = users,
        loadMore = { viewModel.getMoreUsers() },
        onItemUserClicked = { user -> onItemUserClicked(user) }
    )

    when (uiState) {
        is MainUiState.Loading -> CircularLoadingProgress()
        is MainUiState.GetUsersSuccess -> {
            users.clear()
            users.addAll((uiState as MainUiState.GetUsersSuccess).users)
        }
        is MainUiState.GetUsersError -> {
            val errorMessageRes = (uiState as MainUiState.GetUsersError).errorMessage
            Toast.makeText(LocalContext.current, stringResource(errorMessageRes), Toast.LENGTH_SHORT).show()
        }
        else -> {
            // no-operation
        }
    }
}

@Composable
private fun MainViewContent(
    modifier: Modifier = Modifier,
    users: List<User>,
    loadMore: () -> Unit,
    onItemUserClicked: (User) -> Unit
) {
    LazyColumnPaging(
        modifier = modifier.testTag(MainScreenTestTag.USER_LAZY_COLUMN),
        items = users,
        firstItemContent = { user ->
            MainUserCardView(user = user, topPadding = 12) {
                onItemUserClicked(user)
            }
        },
        itemContent = { user ->
            MainUserCardView(user = user, topPadding = 6) {
                onItemUserClicked(user)
            }
        },
        loadMore = loadMore
    )
}

@Composable
private fun MainUserCardView(
    user: User,
    topPadding: Int,
    onItemUserClicked: () -> Unit
) {
    UserCardView(
        modifier = Modifier
            .padding(top = topPadding.dp, bottom = 6.dp, start = 16.dp, end = 16.dp)
            .shadow(
                elevation = 8.dp,
                ambientColor = Color.Black,
                spotColor = Color.Black,
                shape = RoundedCornerShape(8.dp)
            ),
        data = user.toUserCardViewModel(),
        onCardViewClicked = { onItemUserClicked() }
    )
}

@Preview
@Composable
fun PreviewUserCardView() {
    MainViewContent(
        loadMore = {},
        onItemUserClicked = {},
        users = listOf(
            User(
                12,
                "Hoa with text is too long for testing purpose",
                "",
                "https://github.com/Tessalol",
                "Vietnam"
            ),
            User(
                12,
                "Hoa with text is too long for testing purpose",
                "",
                "https://github.com/Tessalol",
                "Vietnam"
            )
        )
    )
}