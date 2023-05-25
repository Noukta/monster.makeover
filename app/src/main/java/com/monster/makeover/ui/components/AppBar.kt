package com.monster.makeover.ui.components

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.monster.makeover.R
import com.monster.makeover.ui.theme.MonsterMakeoverTheme
import com.monster.makeover.utils.openPrivacyPolicy


@Composable
fun MonsterMakeoverAppBar(
    context: Context,
    isSoundMute: Boolean,
    dailyGiftEnabled: Boolean,
    rewardEnabled: Boolean,
    availableCoins: Int,
    onVolumeClick: () -> Unit,
    onRewardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier,
        actions = {
            var isVolumeOff by remember { mutableStateOf(isSoundMute) }
            // privacy policy icon
            IconButton(onClick = { openPrivacyPolicy(context) }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_policy_24),
                    contentDescription = stringResource(R.string.privacy_policy),
                    tint = Color.Unspecified
                )
            }
            // volume on/off icon
            IconButton(onClick = {
                onVolumeClick()
                isVolumeOff = !isVolumeOff
            }) {
                if (isVolumeOff) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_volume_off_24),
                        contentDescription = stringResource(R.string.volume_off),
                        tint = Color.Unspecified
                    )
                } else {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_volume_up_24),
                        contentDescription = stringResource(R.string.volume_on),
                        tint = Color.Unspecified
                    )
                }
            }
            // coins
            Box(
                modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterEnd
            ) {
                PlayerCoins(availableCoins, dailyGiftEnabled, rewardEnabled) { onRewardClick() }
            }

        },
        containerColor = Color.Transparent
    )
}


@Preview
@Composable
fun MonsterMakeoverAppBarPreview() {
    MonsterMakeoverTheme {
        MonsterMakeoverAppBar(
            LocalContext.current,
            isSoundMute = false,
            dailyGiftEnabled = true,
            rewardEnabled = false,
            availableCoins = 200,
            onVolumeClick = {},
            onRewardClick = {}
        )
    }
}