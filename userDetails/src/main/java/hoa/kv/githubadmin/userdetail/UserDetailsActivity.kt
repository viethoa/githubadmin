package hoa.kv.githubadmin.userdetail

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import hoa.kv.githubadmin.repository.model.User
import hoa.kv.githubadmin.systemdesign.theme.GithubAdminTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailsActivity : ComponentActivity() {

    private val viewModel by viewModel<UserDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = if (isTiramisuAndAbove()) {
            intent.extras?.getParcelable(USER_DATA_KEY, User::class.java)
        } else {
            intent.extras?.getParcelable<User>(USER_DATA_KEY)
        }
        if (user == null) {
            throw Exception("Must set user argument before open this screen")
        }

        viewModel.getUser(user.loginName)

        enableEdgeToEdge()
        setContent {
            GithubAdminTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { UserDetailsTopBar { finish() } }
                ) { innerPadding ->
                    UserDetailsView(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel,
                        user = user
                    )
                }
            }
        }
    }

    private fun isTiramisuAndAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

    companion object {
        const val USER_DATA_KEY = "USER_DATA_KEY"
    }
}