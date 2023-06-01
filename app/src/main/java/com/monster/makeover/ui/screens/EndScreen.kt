package com.monster.makeover.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.monster.makeover.R
import com.monster.makeover.ads.admob.AdBanner
import com.monster.makeover.ads.admob.AdmobConstant
import com.monster.makeover.data.DataSource.videos
import com.monster.makeover.data.MonsterState
import com.monster.makeover.ui.components.BackgroundVideo
import com.monster.makeover.ui.components.MonsterCanvas
import com.monster.makeover.ui.components.PrimaryButton
import com.monster.makeover.ui.components.SecondaryButton
import com.monster.makeover.utils.PreferencesHelper
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
            modifier = modifier.padding(16.dp)
        ) {
            if (screenshotState != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(.8f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    ScreenshotBox(
                        screenshotState = screenshotState
                    ) {
                        MonsterCanvas(
                            monsterState = monsterState,
                            selectedItem = 0,
                            animate = true
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(.2f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top
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
    AdBanner(
        bannerId = AdmobConstant.BANNER_END,
        modifier = Modifier.wrapContentSize()
    )
}

fun randomBackground(): Int {
    return videos[(videos.indices).random()]
}

@Composable
fun ShareButton(modifier: Modifier, onClick: () -> Unit) {
    SecondaryButton(onClick = onClick, modifier = modifier){
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = stringResource(R.string.share))
        Spacer(modifier = Modifier.width(10.dp))
        if(!PreferencesHelper.isSharedRecently()) {
            Icon(
                painter = painterResource(id = R.drawable.coin_monster),
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize),
                tint = Color.Unspecified
            )
        }
    }
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