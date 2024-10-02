package hoa.kv.githubadmin.userdetail.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hoa.kv.githubadmin.systemdesign.theme.Black
import hoa.kv.githubadmin.systemdesign.theme.DarkGray
import hoa.kv.githubadmin.systemdesign.theme.LightGray
import hoa.kv.githubadmin.userdetail.R

@Composable
fun FollowWidget(
    modifier: Modifier = Modifier,
    testTag: String = "",
    @DrawableRes iconRes: Int,
    text: String,
    number: Int
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = "follow-widget-icon",
            modifier = Modifier
                .padding(14.dp)
                .size(24.dp)
                .drawBehind {
                    drawCircle(
                        color = LightGray,
                        radius = this.size.maxDimension
                    )
                }
        )
        Text(
            text = "$number+",
            fontSize = 14.sp,
            color = Black,
            modifier = Modifier
                .padding(top = 4.dp)
                .testTag(testTag)
        )
        Text(
            text = text,
            fontSize = 14.sp,
            color = DarkGray,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Preview
@Composable
fun FollowWidgetPreview() {
    FollowWidget(iconRes = R.drawable.ic_following, text = "Following", number = 123)
}