package com.example.createmonster.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.smarttoolfactory.screenshot.ScreenshotState

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
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Monster(
            modifier = Modifier
                .weight(.6f)
                .padding(16.dp),
            monsterUiState = monsterUiState,
            screenshotState = screenshotState
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .weight(.4f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            RemakeButton(
                Modifier
                    .padding(8.dp)
                    .weight(1f)) { onRemakeButtonClicked() }
            ShareButton(
                Modifier
                    .padding(8.dp)
                    .weight(1f)){ onShareButtonClicked()}
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
fun EndPreview(){
    EndScreen()
}