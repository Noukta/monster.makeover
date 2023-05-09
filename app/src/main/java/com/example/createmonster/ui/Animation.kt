package com.example.createmonster.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.math.sin


@Composable
fun AnimatedImage(
    painter: Painter,
    modifier: Modifier = Modifier,
    animationSpeed: Float = 1f,
    contentDescription: String? = null,
    maxAngle: Float = 5f,
    maxTranslation: Float = 3f,
    maxScale: Float = .1f
) {
    var translation by remember { mutableStateOf(0f) }
    var scale by remember { mutableStateOf(1f) }
    var angle by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(40)
            if (maxAngle != 0f) angle =
                sin(System.currentTimeMillis() / 1000.0 * animationSpeed).toFloat() * maxAngle
            if (maxTranslation != 0f) translation =
                sin(System.currentTimeMillis() / 1000.0 * animationSpeed).toFloat() * maxTranslation
            if (maxScale != 0f) scale =
                1.0f + abs(sin(System.currentTimeMillis() / 1000.0 * animationSpeed)).toFloat() * maxScale
        }
    }

    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier
            .graphicsLayer {
                translationX = translation
                translationY = translation
                scaleX = scale
                scaleY = scale
                rotationZ = angle
            }
    )
}