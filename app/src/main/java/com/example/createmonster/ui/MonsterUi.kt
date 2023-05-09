package com.example.createmonster.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.zIndex
import com.example.createmonster.R
import com.example.createmonster.data.MonsterUiState
import kotlin.math.roundToInt

@Composable
fun Head(id: Int?) {
    id?.let {
        Image(
            painter = painterResource(it),
            contentDescription = "Head",
        )
    }
}

@Composable
fun Eye(
    id: Int?,
    size: Size,
    eyeOffset: Offset,
    onPositionChanged: ((Offset) -> Unit)? = null
) {
    val offsetX = remember { mutableStateOf(eyeOffset.x) }
    val offsetY = remember { mutableStateOf(eyeOffset.y) }
    id?.let {
        Image(
            painter = painterResource(it),
            contentDescription = "Eye",
            modifier = Modifier
                .offset {
                    IntOffset(
                        offsetX.value.roundToInt(),
                        offsetY.value.roundToInt()
                    )
                }
                .pointerInput(Unit) {
                    if (onPositionChanged != null) {
                        detectDragGestures(
                            onDragEnd = {
                                onPositionChanged(Offset(offsetX.value, offsetY.value))
                            },
                            onDrag = { _, dragAmount ->
                                val original = Offset(offsetX.value, offsetY.value)
                                val summed = original + dragAmount
                                val newValue = Offset(
                                    x = summed.x.coerceIn(
                                        (-size.width / 2) + 50.dp.toPx(),
                                        size.width / 2 - 50.dp.toPx()
                                    ),
                                    y = summed.y.coerceIn(
                                        (-size.height / 2),
                                        size.width / 2 - 100.dp.toPx()
                                    )
                                )
                                offsetX.value = newValue.x
                                offsetY.value = newValue.y
                            })
                    }
                }
        )
    }

}

@Composable
fun Mouth(
    id: Int?,
    size: Size,
    mouthOffset: Offset,
    onPositionChanged: ((Offset) -> Unit)? = null
) {
    val offsetX = remember { mutableStateOf(mouthOffset.x) }
    val offsetY = remember { mutableStateOf(mouthOffset.y) }
    id?.let {
        Image(
            painter = painterResource(it),
            contentDescription = "Mouth",
            modifier = Modifier
                .offset {
                    IntOffset(
                        offsetX.value.roundToInt(),
                        offsetY.value.roundToInt()
                    )
                }
                .pointerInput(Unit) {
                    if (onPositionChanged != null) {
                        detectDragGestures(
                            onDragEnd = {
                                onPositionChanged(Offset(offsetX.value, offsetY.value))
                            },
                            onDrag = { _, dragAmount ->
                                val original = Offset(offsetX.value, offsetY.value)
                                val summed = original + dragAmount
                                val newValue = Offset(
                                    x = summed.x.coerceIn(
                                        (-size.width / 2) + 50.dp.toPx(),
                                        size.width / 2 - 50.dp.toPx()
                                    ),
                                    y = summed.y.coerceIn(
                                        (-size.height / 2),
                                        size.width / 2 - 100.dp.toPx()
                                    )
                                )
                                offsetX.value = newValue.x
                                offsetY.value = newValue.y
                            })
                    }
                },
        )
    }

}

@Composable
fun Acc(
    id: Int?,
    size: Size,
    accOffset: Offset,
    onPositionChanged: ((Offset) -> Unit)? = null
) {
    val offsetX = remember { mutableStateOf(accOffset.x) }
    val offsetY = remember { mutableStateOf(accOffset.y) }
    id?.let {
        Image(
            painter = painterResource(it),
            contentDescription = "Acc",
            modifier = Modifier
                .offset {
                    IntOffset(
                        offsetX.value.roundToInt(),
                        offsetY.value.roundToInt()
                    )
                }
                .pointerInput(Unit) {
                    if (onPositionChanged != null) {
                        detectDragGestures(
                            onDragEnd = {
                                onPositionChanged(Offset(offsetX.value, offsetY.value))
                            },
                            onDrag = { _, dragAmount ->
                                val original = Offset(offsetX.value, offsetY.value)
                                val summed = original + dragAmount
                                val newValue = Offset(
                                    x = summed.x.coerceIn(
                                        (-size.width / 2) + 50.dp.toPx(),
                                        size.width / 2 - 50.dp.toPx()
                                    ),
                                    y = summed.y.coerceIn(
                                        (-size.height / 2),
                                        size.width / 2 - 100.dp.toPx()
                                    )
                                )
                                offsetX.value = newValue.x
                                offsetY.value = newValue.y
                            })
                    }
                }
        )
    }
}

@Composable
fun Body(id: Int) {
    Image(
        painter = painterResource(id),
        contentDescription = "Body"
    )
}

@Composable
fun Monster(
    modifier: Modifier = Modifier,
    monsterUiState: MonsterUiState,
    onEyePositionChanged: ((Offset) -> Unit)? = null,
    onMouthPositionChanged: ((Offset) -> Unit)? = null,
    onAccPositionChanged: ((Offset) -> Unit)? = null
) {
    var size by remember { mutableStateOf(Size.Zero) }
    //var sizeRatio by remember { mutableStateOf(1f) }
    //sizeRatio = if (monsterUiState.body == null) 1f else .5f
    var headOffset by remember { mutableStateOf((-100).dp) }
    headOffset = if (monsterUiState.body == null) (-100).dp else 15.dp

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .onSizeChanged { size = it.toSize() }
                .offset(y = headOffset)
                .zIndex(2f)
                .weight(1f),
            BottomCenter
        ) {
            Head(monsterUiState.head)
            Eye(monsterUiState.eye, size, monsterUiState.eyeOffset, onEyePositionChanged)
            Mouth(
                monsterUiState.mouth,
                size,
                monsterUiState.mouthOffset,
                onMouthPositionChanged
            )
            Acc(monsterUiState.acc, size, monsterUiState.accOffset, onAccPositionChanged)
        }
        monsterUiState.body?.let {
            Box(
                Modifier
                    .fillMaxSize()
                    .weight(1f),
                TopCenter
            ) {
                Body(monsterUiState.body)
            }
        }

    }


}

@Preview
@Composable
fun MonsterUiPreview() {
    Monster(
        monsterUiState = MonsterUiState(
            head = R.drawable.head_10,
            eye = R.drawable.eye_12,
            mouth = R.drawable.mouth_24,
            acc = R.drawable.acc_10,
            body = R.drawable.body_25
        )
    )
}
