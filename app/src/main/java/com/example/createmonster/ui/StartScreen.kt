package com.example.createmonster.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.createmonster.R
import com.example.createmonster.data.MonsterUiState
import com.example.createmonster.ui.theme.PrimaryButton
import com.example.createmonster.data.DataSource.allParts

fun randomMonster(): MonsterUiState {
    return MonsterUiState(
        head = allParts[0][(0 until allParts[0].size).random()].second,
        eye = allParts[1][(0 until allParts[1].size).random()].second,
        mouth = allParts[2][(0 until allParts[2].size).random()].second,
        acc = allParts[3][(0 until allParts[3].size).random()].second
    )
}

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    onNextButtonClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.title_anim),
            contentDescription = null,
            modifier = Modifier.width(300.dp).weight(.2f)
        )
        Monster(
            modifier = Modifier
                .weight(.6f)
                .padding(16.dp),
            monsterUiState = randomMonster()
        )
        Box(
            Modifier.padding(horizontal = 64.dp).weight(.2f)
        ){
            StartButton (Modifier.fillMaxWidth()
                .padding(horizontal = 32.dp))
            { onNextButtonClicked() }
        }
    }
}

@Composable
fun StartButton(modifier: Modifier, onClick: () -> Unit) {
    PrimaryButton(R.string.start, onClick = onClick, modifier = modifier)
}

@Preview
@Composable
fun StartPreview(){
    StartScreen()
}