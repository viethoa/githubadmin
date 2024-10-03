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

/**
 * Global FullScreen Loading composable
 *
 * In the application in which place need fullscreen loading, should use it for consistency
 */
@Composable
fun CircularLoadingProgress() {
    val interactionSource = remember { MutableInteractionSource() }
    val progressDescription = stringResource(R.string.loading_content_description)
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize(1.0F)
            .background(Color.Black.copy(alpha = 0.4f))
            .semantics { contentDescription = progressDescription }
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                // no-op to disable ripple effect
            }
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(60.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}