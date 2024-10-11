package hoa.kv.githubadmin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import hoa.kv.githubadmin.navigation.AppNavigationGraph
import hoa.kv.githubadmin.systemdesign.theme.GithubAdminTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubAdminTheme {
                AppNavigationGraph(snackbarHostState = remember { SnackbarHostState() }) {
                    finish()
                }
            }
        }
    }
}