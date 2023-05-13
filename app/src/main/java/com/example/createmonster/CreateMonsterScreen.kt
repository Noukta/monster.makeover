package com.example.createmonster

import android.app.Activity
import android.content.Context
import android.media.SoundPool
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.createmonster.data.DataSource.sounds
import com.example.createmonster.ui.CreateMonsterViewModel
import com.example.createmonster.ui.CreateScreen
import com.example.createmonster.ui.EndScreen
import com.example.createmonster.ui.StartScreen
import com.example.createmonster.utils.AdUnit
import com.example.createmonster.utils.UnityAdsManager
import com.example.createmonster.utils.openPrivacyPolicy
import com.example.createmonster.utils.playRandomSound
import com.example.createmonster.utils.playSound
import com.example.createmonster.utils.playSoundInfinite
import com.example.createmonster.utils.saveToInternalStorage
import com.example.createmonster.utils.shareImageUri
import com.smarttoolfactory.screenshot.rememberScreenshotState

enum class CreateMonsterScreen {
    Start,
    Create,
    End
}

@Composable
fun CreateMonsterAppBar(
    context: Context,
    isSoundMute: Boolean,
    onVolumeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier,
        actions = {
            var isVolumeOff by remember { mutableStateOf(isSoundMute) }

            IconButton(onClick = { openPrivacyPolicy(context) }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_policy_24),
                    contentDescription = stringResource(R.string.privacy_policy),
                    tint = Color.Unspecified
                )
            }
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
                        contentDescription = stringResource(R.string.volume_up),
                        tint = Color.Unspecified
                    )
                }
            }
        }
    )
}

@Composable
fun CreateMonsterApp(
    soundPool: SoundPool?,
    isMusicReady: Boolean,
    modifier: Modifier = Modifier,
    viewModel: CreateMonsterViewModel = viewModel()
) {
    val context = LocalContext.current
    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = CreateMonsterScreen.valueOf(
        backStackEntry?.destination?.route ?: CreateMonsterScreen.Start.name
    )
    var adBannerId by remember { mutableStateOf(AdUnit.Banner_Start) }
    var canNavigateBack by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            if (currentScreen == CreateMonsterScreen.Start) {
                CreateMonsterAppBar(
                    context = context,
                    isSoundMute = viewModel.isSoundMute,
                    onVolumeClick = {
                        if (viewModel.isSoundMute) playBackgroundMusic(
                            currentScreen,
                            soundPool,
                            viewModel
                        )
                        else stopBackgroundMusic(soundPool, viewModel)

                        val soundId = sounds.find { it.resId == R.raw.btn_common }?.soundId
                        playSound(soundPool, soundId, viewModel.isSoundMute)

                        viewModel.isSoundMute = !viewModel.isSoundMute
                    }
                )
            }
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()
        if (currentScreen != CreateMonsterScreen.Create) {
            Box(modifier.fillMaxWidth(), Alignment.TopCenter) {
                AndroidView(
                    modifier = Modifier.fillMaxWidth(),
                    factory = {
                        UnityAdsManager.loadBanner(context as Activity, adBannerId)
                    },
                    update = {
                        UnityAdsManager.loadBanner(context as Activity, adBannerId)
                    }
                )
            }
        }

        NavHost(
            navController = navController,
            startDestination = CreateMonsterScreen.Start.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = CreateMonsterScreen.Start.name) {
                UnityAdsManager.load(AdUnit.Interstitial_Start_Create)
                adBannerId = AdUnit.Banner_Start
                StartScreen {
                    val soundId = sounds.find { it.resId == R.raw.btn_start_remake }?.soundId
                    playSound(soundPool, soundId, viewModel.isSoundMute)
                    navController.navigate(CreateMonsterScreen.Create.name)
                    UnityAdsManager.show(
                        AdUnit.Interstitial_Start_Create,
                        context as Activity
                    )
                    canNavigateBack = true
                }
            }
            composable(route = CreateMonsterScreen.Create.name) {
                UnityAdsManager.load(AdUnit.Interstitial_Create_End)
                CreateScreen(
                    monsterUiState = uiState,
                    onMonsterHeadChanged = { id ->
                        viewModel.updateMonsterHead(id)
                        playRandomSound(soundPool, viewModel.isSoundMute)
                    },
                    onMonsterEyeChanged = { id ->
                        viewModel.updateMonsterEye(id)
                        playRandomSound(soundPool, viewModel.isSoundMute)
                    },
                    onMonsterMouthChanged = { id ->
                        viewModel.updateMonsterMouth(id)
                        playRandomSound(soundPool, viewModel.isSoundMute)
                    },
                    onMonsterAccChanged = { id ->
                        viewModel.updateMonsterAcc(id)
                        playRandomSound(soundPool, viewModel.isSoundMute)
                    },
                    onMonsterBodyChanged = { id ->
                        viewModel.updateMonsterBody(id)
                        playRandomSound(soundPool, viewModel.isSoundMute)
                    },
                    onEyePositionChanged = {
                        viewModel.updateEyePosition(it)
                    },
                    onMouthPositionChanged = {
                        viewModel.updateMouthPosition(it)
                    },
                    onAccPositionChanged = {
                        viewModel.updateAccPosition(it)
                    },
                    onDoneButtonClicked = {
                        val soundId = sounds.find { it.resId == R.raw.btn_done }?.soundId
                        playSound(soundPool, soundId, viewModel.isSoundMute)
                        navController.navigate(CreateMonsterScreen.End.name)
                        UnityAdsManager.show(
                            AdUnit.Interstitial_Create_End,
                            context as Activity
                        )
                    },
                    onNextButtonClicked = {
                        val soundId = sounds.find { it.resId == R.raw.btn_next }?.soundId
                        playSound(soundPool, soundId, viewModel.isSoundMute)
                    }
                )

            }
            composable(route = CreateMonsterScreen.End.name) {
                adBannerId = AdUnit.Banner_End
                val screenshotState = rememberScreenshotState()
                // Show dialog only when bitmap not null
                LaunchedEffect(key1 = screenshotState.bitmap) {
                    screenshotState.bitmap?.let {
                        val uri = it.saveToInternalStorage(context, "monster.png")
                        val message =
                            "did you like my monster? create yours: https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
                        shareImageUri(context, uri, message)
                    }
                }

                EndScreen(
                    monsterUiState = uiState,
                    screenshotState = screenshotState,
                    onRemakeButtonClicked = {
                        val soundId = sounds.find { it.resId == R.raw.btn_start_remake }?.soundId
                        playSound(soundPool, soundId, viewModel.isSoundMute)
                        remakeNewMonster(viewModel, navController)
                        canNavigateBack = false
                    }
                ) {
                    val soundId = sounds.find { it.resId == R.raw.btn_common }?.soundId
                    playSound(soundPool, soundId, viewModel.isSoundMute)
                    screenshotState.capture()
                }
            }
        }
    }
    //Triggered when screen changed
    if (isMusicReady && !viewModel.isSoundMute)
        playBackgroundMusic(currentScreen, soundPool, viewModel)
}

private fun playBackgroundMusic(
    currentScreen: CreateMonsterScreen,
    soundPool: SoundPool?,
    viewModel: CreateMonsterViewModel
) {
    if (viewModel.currentStreamId != 0) {
        stopBackgroundMusic(soundPool, viewModel)
    }
    val soundId: Int? = if (currentScreen == CreateMonsterScreen.End) {
        sounds.find { it.resId == R.raw.bgm_end }?.soundId
    } else {
        sounds.find { it.resId == R.raw.bgm_start_create }?.soundId
    }
    if (!viewModel.isMusicPlaying) {
        viewModel.currentStreamId = playSoundInfinite(soundPool, soundId)
        viewModel.isMusicPlaying = true
    }
}

private fun stopBackgroundMusic(soundPool: SoundPool?, viewModel: CreateMonsterViewModel) {
    soundPool?.stop(viewModel.currentStreamId)
    viewModel.isMusicPlaying = false
    Log.i("SOUND", "streamId stopped: ${viewModel.currentStreamId}")
}

private fun remakeNewMonster(
    viewModel: CreateMonsterViewModel,
    navController: NavHostController
) {
    viewModel.resetMonster()
    navController.popBackStack(CreateMonsterScreen.Start.name, inclusive = false)
}