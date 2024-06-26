package com.monster.makeover.ui.screens

import android.util.Log
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.monster.makeover.R
import com.monster.makeover.ads.admob.AdBanner
import com.monster.makeover.ads.admob.AdmobConstant
import com.monster.makeover.ads.admob.AdmobHelper
import com.monster.makeover.data.DataSource.allParts
import com.monster.makeover.data.MonsterState
import com.monster.makeover.ui.animations.AnimatedImage
import com.monster.makeover.ui.components.MonsterCanvas
import com.monster.makeover.ui.components.PrimaryButton
import com.monster.makeover.utils.SoundHelper
import kotlinx.coroutines.delay

fun randomMonster(): MonsterState {
    return MonsterState(
        head = allParts[0][(allParts[0].indices).random()].second,
        eye = allParts[1][(allParts[1].indices).random()].second,
        mouth = allParts[2][(allParts[2].indices).random()].second,
        acc = allParts[3][(allParts[3].indices).random()].second
    )
}

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    onNextButtonClicked: () -> Unit = {}
) {
    val context = LocalContext.current
    var randomMonster by remember {
        mutableStateOf(randomMonster())
    }
    LaunchedEffect(key1 = Unit, block = {
        while (true){
            delay(500)
            randomMonster = randomMonster()
        }
    })
    AdBanner(
        bannerId = AdmobConstant.BANNER_START,
        modifier = Modifier.wrapContentSize()
    )
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
        MonsterCanvas(
            modifier = Modifier
                .weight(.6f)
                .padding(16.dp),
            monsterState = randomMonster,
            selectedItem = 0
        )
        Box(
            Modifier.weight(.1f), Alignment.Center
        ) {
            StartButton(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 96.dp)
            )
            {
                onNextButtonClicked()
                AdmobHelper.showInterstitial(context, AdmobConstant.INTERSTITIAL_START_CREATE)
            }
        }
    }

    LaunchedEffect(Unit) {
        AdmobHelper.loadInterstitial(
            context,
            AdmobConstant.INTERSTITIAL_START_CREATE,
            onAdShowed = { SoundHelper.pauseMusic() },
            onAdDismissed = { SoundHelper.playMusic() }
        )
        Log.d("Interstitial", "load INTERSTITIAL_START_CREATE")
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