package hoa.kv.githubadmin.systemdesign.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import hoa.kv.githubadmin.systemdesign.R
import hoa.kv.githubadmin.systemdesign.theme.Black

/**
 * [CircularLoadingProgress] display a fullscreen loading with a circle loading progress in the center
 *
 * @param size Config the size of circle loading progress
 * @param color Config the circle loading progress color
 * @param trackColor Config circle loading progress trackColor
 * @param progressDescription For instrumentation test when you want to find the progress with description
 */
@Composable
fun CircularLoadingProgress(
    size: Int = 60,
    color: Color = MaterialTheme.colorScheme.primary,
    trackColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    progressDescription: String = stringResource(R.string.loading_content_description)
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize(1.0F)
            .background(Black.copy(alpha = 0.4f))
            .semantics { contentDescription = progressDescription }
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                // no-op to disable ripple effect
            }
    ) {
        CircularProgressIndicator(
            color = color,
            trackColor = trackColor,
            modifier = Modifier.width(size.dp),
        )
    }
}