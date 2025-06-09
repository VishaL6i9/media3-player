package com.vishal.media3player.player

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow // Import
import kotlinx.coroutines.flow.StateFlow // Import

private const val KEY_CURRENT_URI = "current_uri"
private const val KEY_PLAYBACK_POSITION = "playback_position"
private const val KEY_PLAY_WHEN_READY = "play_when_ready"

class PlayerViewModel(application: Application, private val savedStateHandle: SavedStateHandle) : AndroidViewModel(application) {

    val player: ExoPlayer = ExoPlayer.Builder(application).build()

    private var currentMediaUri: Uri? = savedStateHandle.get<String>(KEY_CURRENT_URI)?.let { Uri.parse(it) }
    private var playbackPosition: Long = savedStateHandle.get(KEY_PLAYBACK_POSITION) ?: 0L
    private var playWhenReady: Boolean = savedStateHandle.get(KEY_PLAY_WHEN_READY) ?: true

    // New StateFlow to indicate if a video is loaded
    private val _hasVideoLoaded = MutableStateFlow(currentMediaUri != null)
    val hasVideoLoaded: StateFlow<Boolean> = _hasVideoLoaded

    init {
        currentMediaUri?.let { uri ->
            val mediaItem = MediaItem.fromUri(uri)
            player.setMediaItem(mediaItem)
            player.seekTo(playbackPosition)
            player.playWhenReady = playWhenReady
            player.prepare()
            _hasVideoLoaded.value = true
        }
    }

    fun play(uri: Uri) {
        savePlayerState()

        currentMediaUri = uri
        playbackPosition = 0L
        playWhenReady = true

        val mediaItem = MediaItem.fromUri(uri)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
        _hasVideoLoaded.value = true
    }

    fun setMediaItem(uri: Uri, startPosition: Long = 0L, playWhenReady: Boolean = true) {
        currentMediaUri = uri
        playbackPosition = startPosition
        this.playWhenReady = playWhenReady

        val mediaItem = MediaItem.fromUri(uri)
        player.setMediaItem(mediaItem)
        player.seekTo(startPosition)
        player.playWhenReady = playWhenReady
        player.prepare()
        _hasVideoLoaded.value = true
    }

    private fun savePlayerState() {
        savedStateHandle[KEY_CURRENT_URI] = currentMediaUri?.toString()
        savedStateHandle[KEY_PLAYBACK_POSITION] = player.currentPosition
        savedStateHandle[KEY_PLAY_WHEN_READY] = player.playWhenReady
    }

    override fun onCleared() {
        super.onCleared()
        savePlayerState()
        player.release()
        _hasVideoLoaded.value = false
    }
}