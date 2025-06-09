package com.vishal.media3player.ui

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vishal.media3player.player.PlayerViewModel
import com.vishal.media3player.ui.components.VideoPlayer
import com.vishal.media3player.ui.theme.MediaPlayerTheme
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.size
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding

class MainActivity : ComponentActivity() {

    private lateinit var playerViewModel: PlayerViewModel

    private val pickVideoLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
        uri?.let {
            val takeFlags: Int = intent.flags and
                    (android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION or android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            contentResolver.takePersistableUriPermission(it, takeFlags)

            playerViewModel.play(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowCompat.getInsetsController(window, window.decorView)
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE


        setContent {
            MediaPlayerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    playerViewModel = viewModel()

                    VideoScreen(
                        playerViewModel = playerViewModel,
                        onPickVideo = { pickVideoLauncher.launch(arrayOf("video/*")) }
                    )
                }
            }
        }
    }
}

@Composable
fun VideoScreen(
    playerViewModel: PlayerViewModel,
    onPickVideo: () -> Unit
) {
    val hasVideoLoaded by playerViewModel.hasVideoLoaded.collectAsState()
    val areControlsVisible by playerViewModel.areControlsVisible.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        VideoPlayer(
            player = playerViewModel.player,
            modifier = Modifier.fillMaxSize(),
            onControllerVisibilityChanged = { visible ->
                playerViewModel.setControlsVisibility(visible)
            }
        )

        AnimatedVisibility(
            visible = (areControlsVisible || !hasVideoLoaded),
            enter = fadeIn(animationSpec = tween(durationMillis = 300)),
            exit = fadeOut(animationSpec = tween(durationMillis = 300))
        ) {
            if (!hasVideoLoaded) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.8f)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                ) {
                    Button(
                        onClick = onPickVideo,
                        modifier = Modifier.size(width = 200.dp, height = 60.dp)
                    ) {
                        Text("Select Video", style = MaterialTheme.typography.titleLarge)
                    }
                }
            } else {

                IconButton(
                    onClick = onPickVideo,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 16.dp, end = 60.dp)
                        .size(48.dp)
                        .background(Color.Black.copy(alpha = 0.5f), MaterialTheme.shapes.small)
                ) {
                    Icon(
                        imageVector = Icons.Default.FolderOpen,
                        contentDescription = "Select File",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}