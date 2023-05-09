package com.example.createmonster.ui

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
import com.example.createmonster.R
import com.example.createmonster.data.MonsterUiState
import com.example.createmonster.ui.theme.PrimaryButton
import com.example.createmonster.ui.theme.SecondaryButton
import com.smarttoolfactory.screenshot.ScreenshotBox
import com.smarttoolfactory.screenshot.ScreenshotState
import com.smarttoolfactory.screenshot.rememberScreenshotState

@Composable
fun EndScreen(
    modifier: Modifier = Modifier,
    monsterUiState: MonsterUiState = MonsterUiState(),
    screenshotState: ScreenshotState? = null,
    onRemakeButtonClicked: () -> Unit = {},
    onShareButtonClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (screenshotState != null) {
            ScreenshotBox(
                modifier = Modifier
                    .weight(.6f)
                    .padding(16.dp),
                screenshotState = screenshotState
            ) {
                Monster(
                    monsterUiState = monsterUiState
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
        monsterUiState = MonsterUiState(
            head = R.drawable.head_04,
            eye = R.drawable.eye_11,
            mouth = R.drawable.mouth_20,
            acc = R.drawable.acc_00,
            body = R.drawable.body_26
        ), screenshotState = rememberScreenshotState()
    )
}