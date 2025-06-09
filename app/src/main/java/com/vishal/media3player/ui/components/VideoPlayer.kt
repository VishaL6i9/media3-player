package com.vishal.media3player.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@Composable
fun VideoPlayer(
    player: ExoPlayer,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                this.player = player
                useController = true
                // Optional: If you want the video to always fill the screen and be cropped if aspect ratios don't match, use RESIZE_MODE_ZOOM.
                // Otherwise, RESIZE_MODE_FIT (default) maintains aspect ratio with black bars.
                // resizeMode = com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            }
        },
        update = { playerView ->
            playerView.player = player
        },
        modifier = modifier.fillMaxSize()
    )
}