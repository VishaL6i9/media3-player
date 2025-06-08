package com.vishal.media3player.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView
import com.vishal.media3player.player.PlayerController

@Composable
fun VideoPlayer(
    uri: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val playerController = remember { PlayerController(context) }

    DisposableEffect(Unit) {
        playerController.play(uri)
        onDispose { playerController.release() }
    }

    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = playerController.player
                useController = true
            }
        },
        modifier = modifier
    )
}