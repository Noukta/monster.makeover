package com.example.createmonster.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.createmonster.R
import com.example.createmonster.data.DataSource.allParts
import com.example.createmonster.data.MonsterUiState
import com.example.createmonster.ui.theme.PrimaryButton

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
        Spacer(modifier.height(20.dp))
        AnimatedTitle(
            modifier = Modifier
                .width(300.dp)
                .weight(.3f)
        )
        Monster(
            modifier = Modifier
                .weight(.6f)
                .padding(16.dp),
            monsterUiState = randomMonster()
        )
        Box(
            Modifier.weight(.1f), Alignment.Center
        ) {
            StartButton(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 96.dp)
            )
            { onNextButtonClicked() }
        }
    }
}

@Composable
fun AnimatedTitle(modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedImage(
            painter = painterResource(id = R.drawable.title_monster),
            animationSpeed = 3f,
            maxAngle = 1f
        )
        Spacer(modifier = Modifier.size(10.dp))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            AnimatedImage(
                painter = painterResource(id = R.drawable.title_makeover),
                animationSpeed = 3.5f,
                maxAngle = -1f
            )
            AnimatedImage(
                painter = painterResource(id = R.drawable.title_star),
                modifier = Modifier.offset(95.dp, (-10).dp),
                animationSpeed = 8f
            )
            AnimatedImage(
                painter = painterResource(id = R.drawable.title_star),
                modifier = Modifier
                    .offset((-95).dp, (10).dp)
                    .rotate(180F),
                animationSpeed = -10f
            )
        }
    }
}

@Composable
fun StartButton(modifier: Modifier, onClick: () -> Unit) {
    PrimaryButton(R.string.start, onClick = onClick, modifier = modifier)
}

@Preview
@Composable
fun StartPreview() {
    StartScreen()
}