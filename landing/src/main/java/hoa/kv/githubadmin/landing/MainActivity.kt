package hoa.kv.githubadmin.landing

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import hoa.kv.githubadmin.repository.model.User
import hoa.kv.githubadmin.systemdesign.theme.GithubAdminTheme
import hoa.kv.githubadmin.systemdesign.theme.White
import hoa.kv.githubadmin.userdetail.UserDetailsActivity

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubAdminTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        MainTopBar { finish() }
                    }
                ) { innerPadding ->
                    MainView(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .background(color = White)
                    ) { user ->
                        showMachine(user)
                    }
                }
            }
        }
    }

    private fun showMachine(user: User) {
        val intent = Intent(this, UserDetailsActivity::class.java)
        intent.putExtra(UserDetailsActivity.USER_DATA_KEY, user)
        startActivity(intent)
    }
}