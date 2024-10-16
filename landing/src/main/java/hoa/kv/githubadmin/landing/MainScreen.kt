package hoa.kv.githubadmin.landing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hoa.kv.githubadmin.landing.tag.MainScreenTestTag
import hoa.kv.githubadmin.landing.utils.toUserCardViewModel
import hoa.kv.githubadmin.repository.model.User
import hoa.kv.githubadmin.systemdesign.lazycolumn.LazyColumnPaging
import hoa.kv.githubadmin.systemdesign.loading.CircularLoadingProgress
import hoa.kv.githubadmin.systemdesign.theme.Black
import hoa.kv.githubadmin.systemdesign.theme.White
import hoa.kv.githubadmin.systemdesign.usercard.UserCardView
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel = koinViewModel<MainViewModel>(),
    snackbarHostState: SnackbarHostState,
    onBackPressed: () -> Unit,
    onOpenUserDetails: (User) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(
        lifecycleOwner = LocalLifecycleOwner.current
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            MainTopBar { onBackPressed() }
        }
    ) { innerPadding ->
        MainViewContainer(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = White),
            snackbarHostState = snackbarHostState,
            uiState = uiState,
            onLoadMore = { viewModel.getMoreUsers() }
        ) { user ->
            onOpenUserDetails(user)
        }
    }
}

@Composable
private fun MainViewContainer(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    uiState: MainUiState,
    onLoadMore: () -> Unit,
    onItemUserClicked: (User) -> Unit
) {
    val users = remember { mutableStateListOf<User>() }

    MainViewContent(
        modifier = modifier,
        users = users,
        loadMore = { onLoadMore() },
        onItemUserClicked = { user -> onItemUserClicked(user) }
    )

    when (uiState) {
        is MainUiState.Loading -> CircularLoadingProgress()
        is MainUiState.GetUsersSuccess -> {
            users.clear()
            users.addAll(uiState.users)
        }
        is MainUiState.GetUsersError -> {
            val errorMessage = stringResource(uiState.errorMessage)
            LaunchedEffect(uiState) {
                snackbarHostState.showSnackbar(errorMessage)
            }
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
                ambientColor = Black,
                spotColor = Black,
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