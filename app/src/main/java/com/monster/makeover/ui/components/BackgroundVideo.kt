package com.monster.makeover.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_FILL
import androidx.media3.ui.PlayerView

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun BackgroundVideo(videoResId: Int, content: @Composable () -> Unit) {
    val context = LocalContext.current
    val videoUri = "rawresource://${context.packageName}/${videoResId}"
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem =
                MediaItem.Builder().setUri(videoUri).setMimeType(MimeTypes.VIDEO_MP4).build()
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
            repeatMode = Player.REPEAT_MODE_ONE
        }
    }

    AndroidView(
        factory = {
            PlayerView(it).apply {
                player = exoPlayer
                useController = false
                resizeMode = RESIZE_MODE_FILL
            }
        },
        modifier = Modifier.fillMaxSize()
    )

    Surface(color = Color.Transparent, modifier = Modifier.fillMaxSize()) {
        content()
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }
}