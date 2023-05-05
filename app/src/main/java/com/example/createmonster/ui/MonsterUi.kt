package com.example.createmonster.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.createmonster.data.MonsterUiState
import com.smarttoolfactory.screenshot.ScreenshotBox
import com.smarttoolfactory.screenshot.ScreenshotState
import kotlin.math.roundToInt

@Composable
fun Head(id: Int?){
    id?.let {
        Image(
            painter = painterResource(it),
            contentDescription = "Head",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Inside
        )
    }
}

@Composable
fun Eye(id: Int?, size: Size, eyeOffset: Offset, onPositionChanged: ((Offset) -> Unit)? = null){
    val offsetX = remember { mutableStateOf(eyeOffset.x) }
    val offsetY = remember { mutableStateOf(eyeOffset.y) }
    id?.let {
        Image(
            painter = painterResource(it),
            contentDescription = "Eye",
            modifier = Modifier
                .fillMaxSize()
                .offset {
                    IntOffset(
                        offsetX.value.roundToInt(),
                        offsetY.value.roundToInt()
                    )
                }
                .pointerInput(Unit) {
                    if(onPositionChanged!=null) {
                        detectDragGestures(
                            onDragEnd = {
                                onPositionChanged(Offset(offsetX.value, offsetY.value))
                            },
                            onDrag = { _, dragAmount ->
                                //if (TabPosition.current == Parts.Eye.index)
                                val original = Offset(offsetX.value, offsetY.value)
                                val summed = original + dragAmount
                                val newValue = Offset(
                                    x = summed.x.coerceIn(
                                        (-size.width / 2) + 50.dp.toPx(),
                                        size.width / 2 - 50.dp.toPx()
                                    ),
                                    y = summed.y.coerceIn(
                                        (-size.height / 2) + 50.dp.toPx(),
                                        size.height / 2 - 50.dp.toPx()
                                    )
                                )
                                offsetX.value = newValue.x
                                offsetY.value = newValue.y
                            })
                    }
                },
            contentScale = ContentScale.Inside
        )
    }

}

@Composable
fun Mouth(id: Int?, size: Size, mouthOffset: Offset, onPositionChanged: ((Offset) -> Unit)? = null){
    val offsetX = remember { mutableStateOf(mouthOffset.x) }
    val offsetY = remember { mutableStateOf(mouthOffset.y) }
    id?.let {
        Image(
            painter = painterResource(it),
            contentDescription = "Mouth",
            modifier = Modifier
                .fillMaxSize()
                .offset {
                    IntOffset(
                        offsetX.value.roundToInt(),
                        offsetY.value.roundToInt()
                    )
                }
                .pointerInput(Unit) {
                    if(onPositionChanged!=null) {
                        detectDragGestures(
                            onDragEnd = {
                                onPositionChanged(Offset(offsetX.value, offsetY.value))
                            },
                            onDrag = { _, dragAmount ->
                                //if (TabPosition.current == Parts.Eye.index)
                                val original = Offset(offsetX.value, offsetY.value)
                                val summed = original + dragAmount
                                val newValue = Offset(
                                    x = summed.x.coerceIn(
                                        (-size.width / 2) + 50.dp.toPx(),
                                        size.width / 2 - 50.dp.toPx()
                                    ),
                                    y = summed.y.coerceIn(
                                        (-size.height / 2) + 50.dp.toPx(),
                                        size.height / 2 - 50.dp.toPx()
                                    )
                                )
                                offsetX.value = newValue.x
                                offsetY.value = newValue.y
                            })
                    }
                },
            contentScale = ContentScale.Inside
        )
    }

}

@Composable
fun Acc(id: Int?, size: Size, accOffset: Offset, onPositionChanged: ((Offset) -> Unit)? = null){
    val offsetX = remember { mutableStateOf(accOffset.x) }
    val offsetY = remember { mutableStateOf(accOffset.y) }
    id?.let {
        Image(
            painter = painterResource(it),
            contentDescription = "Acc",
            modifier = Modifier
                .fillMaxSize()
                .offset {
                    IntOffset(
                        offsetX.value.roundToInt(),
                        offsetY.value.roundToInt()
                    )
                }
                .pointerInput(Unit) {
                    if(onPositionChanged != null) {
                        detectDragGestures(
                            onDragEnd = {
                                onPositionChanged(Offset(offsetX.value, offsetY.value))
                            },
                            onDrag = { _, dragAmount ->
                                //if (TabPosition.current == Parts.Eye.index)
                                val original = Offset(offsetX.value, offsetY.value)
                                val summed = original + dragAmount
                                val newValue = Offset(
                                    x = summed.x.coerceIn(
                                        (-size.width / 2) + 50.dp.toPx(),
                                        size.width / 2 - 50.dp.toPx()
                                    ),
                                    y = summed.y.coerceIn(
                                        (-size.height / 2) + 50.dp.toPx(),
                                        size.height / 2 - 50.dp.toPx()
                                    )
                                )
                                offsetX.value = newValue.x
                                offsetY.value = newValue.y
                            })
                    }
                },
            contentScale = ContentScale.Inside
        )
    }
}

@Composable
fun Monster(
    modifier: Modifier = Modifier,
    monsterUiState: MonsterUiState,
    onEyePositionChanged: ((Offset) -> Unit)? = null,
    onMouthPositionChanged: ((Offset) -> Unit)? = null,
    onAccPositionChanged: ((Offset) -> Unit)? = null,
    screenshotState: ScreenshotState? = null
) {
    var size by remember { mutableStateOf(Size.Zero) }

    if(screenshotState!=null){
        ScreenshotBox(
            modifier = modifier,
            screenshotState = screenshotState
        ) {
            Box(
                Modifier.fillMaxSize()
                    .onSizeChanged { size = it.toSize() }
                // TODO: resize head when body not null
                //modifier = Modifier.scale(.5f).offset(y= (-300).dp)
            ){
                Head(monsterUiState.head)
                Eye(monsterUiState.eye, size, monsterUiState.eyeOffset, onEyePositionChanged)
                Mouth(monsterUiState.mouth, size, monsterUiState.mouthOffset, onMouthPositionChanged)
                Acc(monsterUiState.acc, size, monsterUiState.accOffset, onAccPositionChanged)
            }

        }
    }
    else{
        Box(
            modifier = modifier
        ) {
            Box(
                Modifier.fillMaxSize()
                    .onSizeChanged { size = it.toSize() }
                // TODO: resize head when body not null
                //modifier = Modifier.scale(.5f).offset(y= (-300).dp)
            ){
                Head(monsterUiState.head)
                Eye(monsterUiState.eye, size, monsterUiState.eyeOffset, onEyePositionChanged)
                Mouth(monsterUiState.mouth, size, monsterUiState.mouthOffset, onMouthPositionChanged)
                Acc(monsterUiState.acc, size, monsterUiState.accOffset, onAccPositionChanged)
            }

        }
    }

}
