package com.vishal.media3player.player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

class PlayerController(context: Context) {
    val player = ExoPlayer.Builder(context).build()

    fun play(uri: String) {
        player.setMediaItem(MediaItem.fromUri(uri))
        player.prepare()
        player.play()
    }

    fun release() {
        player.release()
    }
}