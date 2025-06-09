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

                    VideoScreen(playerViewModel = playerViewModel, onPickVideo = {
                        pickVideoLauncher.launch(arrayOf("video/*"))
                    })
                }
            }
        }
    }
}

@Composable
fun VideoScreen(playerViewModel: PlayerViewModel, onPickVideo: () -> Unit) {
    val hasVideoLoaded by playerViewModel.hasVideoLoaded.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!hasVideoLoaded) {
            Button(onClick = onPickVideo) {
                Text("Select Video from Storage")
            }
        }

        VideoPlayer(
            player = playerViewModel.player,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )
    }
}