package hoa.kv.githubadmin.systemdesign.usercard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import hoa.kv.githubadmin.systemdesign.R
import hoa.kv.githubadmin.systemdesign.theme.Blue
import hoa.kv.githubadmin.systemdesign.theme.LightGray
import hoa.kv.githubadmin.systemdesign.theme.White

/**
 * [UserCardView] Display user information in the [Card] view with information following [UserCardViewModel]
 *
 * @param modifier Modifier to be applied to the card
 * @param data Data which to show in the card
 * @param titleFontSize Font size to be applied for title which is [UserCardViewModel.name]
 * @param backgroundColor Background to be applied for card content
 * @param onCardViewClicked Callback event when user click into the card
 */
@Composable
fun UserCardView(
    modifier: Modifier = Modifier,
    data: UserCardViewModel,
    titleFontSize: Int = 18,
    backgroundColor: Color = White,
    onCardViewClicked: () -> Unit = { },
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = { onCardViewClicked() }
    ) {
        Row(
            modifier = Modifier
                .background(color = backgroundColor)
                .padding(12.dp)
        ) {
            UserAvatar(data.avatarUrl, data.name)
            Column(
                modifier = Modifier.padding(start = 12.dp),
                verticalArrangement = Arrangement.Center
            ) {
                UserNameText(data.name, titleFontSize)
                HorizontalDivider(
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 8.dp)
                )
                if (!data.landingPageUrl.isNullOrEmpty()) {
                    LandingPageText(data.landingPageUrl)
                }
                if (!data.location.isNullOrEmpty()) {
                    LocationText(data.location)
                }
            }
        }
    }
}

@Composable
private fun UserNameText(userName: String, titleFontSize: Int) {
    Text(
        text = userName,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        fontSize = titleFontSize.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.testTag(userName)
    )
}

@Composable
private fun UserAvatar(avatarUrl: String, contentDescription: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(avatarUrl)
            .diskCacheKey(avatarUrl)
            .memoryCacheKey(avatarUrl)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.ic_user_placeholder),
        contentDescription = contentDescription,
        modifier = Modifier
            .size(110.dp)
            .background(color = LightGray, shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
    )
}

@Composable
private fun LandingPageText(landingPageUrl: String) {
    Text(
        text = landingPageUrl,
        style = TextStyle(textDecoration = TextDecoration.Underline),
        color = Blue,
        fontSize = 14.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .padding(top = 10.dp)
            .testTag(landingPageUrl)
    )
}

@Composable
private fun LocationText(location: String) {
    Row(
        modifier = Modifier.padding(top = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_location),
            contentDescription = stringResource(R.string.location_content_description),
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = location,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(start = 4.dp)
                .testTag(location)
        )
    }
}

@Preview
@Composable
fun PreviewUserCardView() {
    UserCardView(
        data = UserCardViewModel(
            "Hoa with text is too long for testing purpose",
            "",
            "https://github.com/Tessalol-safasdf-sdf-sadfsadf-asdf-asdf-sadfsdaf-afasfsadfd-fsadf",
            "Vietnam"
        )
    )
}