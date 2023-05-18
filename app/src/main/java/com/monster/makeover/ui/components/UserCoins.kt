package com.monster.makeover.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.monster.makeover.R
import com.monster.makeover.ui.animations.AnimatedImage
import com.monster.makeover.ui.theme.MonsterMakeoverTheme

@Composable
fun UserCoins(
    coins: Int = 10,
    dailyGiftEnabled: Boolean = true,
    rewardEnabled: Boolean = true,
    onRewardClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(20.dp)
            .width(180.dp),
        shape = RoundedCornerShape(50)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .height(IntrinsicSize.Max),
                painter = painterResource(id = R.drawable.coin_monster),
                contentDescription = ""
            )

            Text(
                text = "$coins",
                modifier = Modifier
                    .weight(.5f)
                    .padding(start = 5.dp),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 20.sp,
                textAlign = TextAlign.Left,
            )
            val rewardIcon = if (dailyGiftEnabled) R.drawable.gift else R.drawable.reward
            if (rewardEnabled || dailyGiftEnabled) {
                IconButton(
                    onClick = {
                        onRewardClick()
                    },
                    modifier = Modifier.weight(.5f)
                ) {
                    AnimatedImage(
                        painter = painterResource(id = rewardIcon),
                        contentDescription = "",
                        animationSpeed = 4f,
                        maxTranslation = 0f,
                        maxScale = -.2f
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun UserCoinsPreview() {
    MonsterMakeoverTheme() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UserCoins()
        }
    }
}