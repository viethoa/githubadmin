package hoa.kv.githubadmin.userdetail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import hoa.kv.githubadmin.systemdesign.theme.Black

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun UserDetailsTopBar(onBackButtonClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 52.dp)
                    .wrapContentWidth(align = Alignment.CenterHorizontally),
                text = stringResource(R.string.user_detail_screen_title)
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackButtonClicked() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.user_detail_back_btn_description),
                    tint = Black
                )
            }
        }
    )
}