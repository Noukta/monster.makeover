package com.monster.makeover.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.monster.makeover.R
import com.monster.makeover.data.MonsterState
import kotlinx.coroutines.delay
import kotlin.math.sin

@Composable
fun MonsterCanvas(
    modifier: Modifier = Modifier,
    monsterState: MonsterState,
    onEyePositionChanged: ((Offset) -> Unit)? = null,
    onMouthPositionChanged: ((Offset) -> Unit)? = null,
    onAccPositionChanged: ((Offset) -> Unit)? = null,
    selectedItem: Int,
    animate: Boolean = false
) {
    val head  = monsterState.head?.let { ImageBitmap.imageResource(it) }
    val eye  = monsterState.eye?.let { ImageBitmap.imageResource(it) }
    val mouth  = monsterState.mouth?.let { ImageBitmap.imageResource(it) }
    val acc  = monsterState.acc?.let { ImageBitmap.imageResource(it) }
    val body  = monsterState.body?.let { ImageBitmap.imageResource(it) }

    var newEyeOffset by remember {
        mutableStateOf(monsterState.eyeOffset)
    }
    var newMouthOffset by remember {
        mutableStateOf(monsterState.mouthOffset)
    }
    var newAccOffset by remember {
        mutableStateOf(monsterState.accOffset)
    }
    var isDragEnded by remember {
        mutableStateOf(false)
    }
    var dragOffset by remember {
        mutableStateOf(Offset.Zero)
    }
    val resizeAnimation = remember {
        Animatable(0f)
    }

    var angle by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(40)
            angle = sin(System.currentTimeMillis() / 1000.0 * 4f).toFloat() * 15f
        }
    }

    LaunchedEffect(body){
        if(body != null && resizeAnimation.value == 0f)
            resizeAnimation.animateTo(1f)
    }
    LaunchedEffect(selectedItem) {
        when (selectedItem) {
            2 -> if (onEyePositionChanged != null) {
                onEyePositionChanged(newEyeOffset)
            }

            3 -> if (onMouthPositionChanged != null) {
                onMouthPositionChanged(newMouthOffset)
            }

            4 -> if (onAccPositionChanged != null) {
                onAccPositionChanged(newAccOffset)
            }
        }
        isDragEnded = false
        dragOffset = Offset.Zero
    }

    Box(modifier = modifier){
        Canvas(modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        isDragEnded = true
                    }
                ) { change, dragAmount ->
                    change.consume()
                    dragOffset += dragAmount
                }
            }
        ){

            body?.let {
                drawImage(
                    image = it,
                    topLeft = center - Offset(
                        it.width.toFloat() / 2,
                        it.height.toFloat() / 2 - head!!.height.toFloat() / 4
                    ),
                    alpha = resizeAnimation.value
                )
            }
            val toStickHead  = 20.dp.toPx()
            dragOffset = Offset(
                x = dragOffset.x.coerceIn(-size.width/2, size.width/2),
                y = dragOffset.y.coerceIn(-size.height/2, size.height/2)
            )
            withTransform(
                {
                    if(head != null  && body != null) {
                        scale(
                            scaleX = 1 - .5f * resizeAnimation.value,
                            scaleY = 1 - .5f * resizeAnimation.value,
                            pivot = center + Offset(0f, head.height.toFloat() / 2)
                        )
                        translate(
                            left = 0f,
                            top = toStickHead -(body.height.toFloat()+ head.height.toFloat()/2) * resizeAnimation.value
                        )
                        if(animate){
                            rotate(angle, center + Offset(0f, head.height.toFloat() / 2))
                        }
                    }
                }
            ) {
                head?.let {
                    drawImage(
                        image = it,
                        topLeft = center - Offset(
                            it.width.toFloat() / 2,
                            it.height.toFloat() / 2
                        )
                    )
                }
                mouth?.let {
                    if (selectedItem == 2) newMouthOffset = monsterState.mouthOffset + dragOffset
                    drawImage(
                        image = it,
                        topLeft = newMouthOffset + center - Offset(
                            it.width.toFloat() / 2,
                            (it.height.toFloat() - head!!.height.toFloat() * .4f) / 2
                        )
                    )
                }
                eye?.let {
                    if (selectedItem == 1) newEyeOffset = monsterState.eyeOffset + dragOffset
                    drawImage(
                        image = it,
                        topLeft = newEyeOffset + center - Offset(
                            it.width.toFloat() / 2,
                            (it.height.toFloat() + head!!.height.toFloat() * .2f) / 2
                        )
                    )
                }
                acc?.let {
                    if (selectedItem == 3) {
                        newAccOffset = monsterState.accOffset + dragOffset
                    }
                    drawImage(
                        image = it,
                        topLeft = newAccOffset + center - Offset(
                            it.width.toFloat() / 2,
                            (it.height.toFloat() + head!!.height.toFloat()) / 2
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MonsterUiPreview() {
    MonsterCanvas(
        monsterState = MonsterState(
            head = R.drawable.head_31,
            eye = R.drawable.eye_16,
            mouth = R.drawable.mouth_30,
            acc = R.drawable.acc_10,
            body = R.drawable.body_25
        ),
        selectedItem = 0,
        animate = true
    )
}
