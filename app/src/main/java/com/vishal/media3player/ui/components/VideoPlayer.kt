package com.vishal.media3player.ui.components

import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@Composable
fun VideoPlayer(
    player: ExoPlayer,
    modifier: Modifier = Modifier,
    onControllerVisibilityChanged: (Boolean) -> Unit
) {
    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                this.player = player
                useController = true
                setControllerVisibilityListener(
                    PlayerView.ControllerVisibilityListener {
                        visibility -> onControllerVisibilityChanged(visibility == View.VISIBLE)
                    }
                )
            }
        },
        update = { playerView ->
            playerView.player = player
        },
        modifier = modifier.fillMaxSize()
    )
}