package com.vishal.media3player.utils

import androidx.annotation.OptIn
import androidx.media3.common.MimeTypes
import androidx.media3.common.util.UnstableApi

object CodecUtils {
    @OptIn(UnstableApi::class)
    fun getPreferredVideoCodecs(): Array<String> {
        return arrayOf(
            // Prioritize H.265 (HEVC)
            MimeTypes.VIDEO_H265,
            // Then H.264 (AVC)
            MimeTypes.VIDEO_H264,
            // Fallback to other codecs
            MimeTypes.VIDEO_VP9,
            MimeTypes.VIDEO_AV1,
            MimeTypes.VIDEO_VP8,
            MimeTypes.VIDEO_MP4V,
            MimeTypes.VIDEO_MPEG2,
            MimeTypes.VIDEO_MPEG
        )
    }

    fun getPreferredAudioCodecs(): Array<String> {
        return arrayOf(
            // Lossless formats
            MimeTypes.AUDIO_TRUEHD,
            MimeTypes.AUDIO_DTS_HD,
            // Lossy formats
            MimeTypes.AUDIO_E_AC3_JOC,
            MimeTypes.AUDIO_E_AC3,
            MimeTypes.AUDIO_AC3,
            MimeTypes.AUDIO_DTS,
            // Standard formats
            MimeTypes.AUDIO_AAC,
            MimeTypes.AUDIO_OPUS,
            MimeTypes.AUDIO_VORBIS,
            MimeTypes.AUDIO_MPEG,
            MimeTypes.AUDIO_MPEG_L1,
            MimeTypes.AUDIO_MPEG_L2
        )
    }


}