package com.monster.makeover.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.monster.makeover.R
import com.monster.makeover.data.DataSource.videos
import com.monster.makeover.data.MonsterState
import com.monster.makeover.ui.components.BackgroundVideo
import com.monster.makeover.ui.components.MonsterCanvas
import com.monster.makeover.ui.components.PrimaryButton
import com.monster.makeover.ui.components.SecondaryButton
import com.smarttoolfactory.screenshot.ScreenshotBox
import com.smarttoolfactory.screenshot.ScreenshotState
import com.smarttoolfactory.screenshot.rememberScreenshotState

@Composable
fun EndScreen(
    modifier: Modifier = Modifier,
    monsterState: MonsterState = MonsterState(),
    screenshotState: ScreenshotState? = null,
    onRemakeButtonClicked: () -> Unit = {},
    onShareButtonClicked: () -> Unit = {}
) {
    BackgroundVideo(randomBackground()) {
        Column(
            modifier = modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (screenshotState != null) {
                ScreenshotBox(
                    modifier = Modifier.weight(.6f),
                    screenshotState = screenshotState
                ) {
                    MonsterCanvas(
                        monsterState = monsterState,
                        selectedItem = 0,
                        animate = true
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .weight(.2f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RemakeButton(
                    Modifier
                        .padding(8.dp)
                        .weight(1f)
                ) { onRemakeButtonClicked() }
                ShareButton(
                    Modifier
                        .padding(8.dp)
                        .weight(1f)
                ) { onShareButtonClicked() }
            }
        }
    }
}

fun randomBackground(): Int {
    return videos[(videos.indices).random()]
}

@Composable
fun ShareButton(modifier: Modifier, onClick: () -> Unit) {
    SecondaryButton(R.string.share, onClick = onClick, modifier = modifier)
}

@Composable
fun RemakeButton(modifier: Modifier, onClick: () -> Unit) {
    PrimaryButton(R.string.remake, onClick = onClick, modifier = modifier)
}

@Preview
@Composable
fun EndPreview() {
    EndScreen(
        monsterState = MonsterState(
            head = R.drawable.head_04,
            eye = R.drawable.eye_11,
            mouth = R.drawable.mouth_20,
            acc = R.drawable.acc_00,
            body = R.drawable.body_26
        ), screenshotState = rememberScreenshotState()
    )
}