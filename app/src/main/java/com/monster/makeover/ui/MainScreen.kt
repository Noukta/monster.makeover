package com.monster.makeover.ui

import android.app.Activity
import android.media.SoundPool
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.play.core.review.ReviewManagerFactory
import com.monster.makeover.BuildConfig
import com.monster.makeover.R
import com.monster.makeover.data.DataSource.sounds
import com.monster.makeover.db.DatabaseHolder.Companion.Database
import com.monster.makeover.db.obj.MonsterItem
import com.monster.makeover.ext.query
import com.monster.makeover.ui.components.MonsterMakeoverAppBar
import com.monster.makeover.ui.screens.CreateScreen
import com.monster.makeover.ui.screens.EndScreen
import com.monster.makeover.ui.screens.StartScreen
import com.monster.makeover.utils.AdUnit
import com.monster.makeover.utils.PreferencesHelper
import com.monster.makeover.utils.UnityAdsManager
import com.monster.makeover.utils.playRandomSound
import com.monster.makeover.utils.playSound
import com.monster.makeover.utils.playSoundInfinite
import com.monster.makeover.utils.saveToInternalStorage
import com.monster.makeover.utils.shareImageUri
import com.smarttoolfactory.screenshot.rememberScreenshotState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class MonsterMakeoverScreen {
    Start,
    Create,
    End
}

@Composable
fun MonsterMakeoverApp(
    soundPool: SoundPool?,
    isMusicReady: Boolean,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
) {
    //Navigation
    val context = LocalContext.current
    val navController: NavHostController = rememberNavController()
    navController.enableOnBackPressed(false)
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = MonsterMakeoverScreen.valueOf(
        backStackEntry?.destination?.route ?: MonsterMakeoverScreen.Start.name
    )
    var canNavigateBack by remember { mutableStateOf(false) }
    //Ads
    var adBannerId by remember { mutableStateOf(AdUnit.Banner_Start) }
    //Sound
    val soundScope = remember { CoroutineScope(Dispatchers.IO) }
    //Review
    val reviewManager = remember { ReviewManagerFactory.create(context) }
    val preferencesHelper = remember { PreferencesHelper(context) }
    val appOpenCount = remember { preferencesHelper.getAppOpenCount() }
    val appOpenMaxToReview = remember { preferencesHelper.getAppOpenMaxToReview() }
    //Reward
    var isDailyGiftAvailable by remember { mutableStateOf(preferencesHelper.checkDailyGift()) }
    var rewardsToClaim by remember { mutableStateOf(preferencesHelper.getRewardsToClaim()) }
    var currentCoins by remember { mutableStateOf(preferencesHelper.getCoins()) }
    if (rewardsToClaim > 0)
        UnityAdsManager.load(AdUnit.Rewarded_Coins)

    LaunchedEffect(currentScreen) {
        if (appOpenCount == appOpenMaxToReview && currentScreen == MonsterMakeoverScreen.End) {
            val request = reviewManager.requestReviewFlow()
            request.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i("REVIEW", "Request review succeeded")
                    val reviewInfo = task.result
                    val flow = reviewManager.launchReviewFlow(context as Activity, reviewInfo)
                    flow.addOnCompleteListener {
                        Log.i("REVIEW", "Review flow finished")
                        preferencesHelper.incrementAppOpenMaxToReview()
                    }
                } else {
                    // There was some problem, log or handle the error code.
                    Log.e("REVIEW", "Request review failed")
                }
            }
        }
    }

    Scaffold(
        bottomBar = {
            if (currentScreen == MonsterMakeoverScreen.Start) {
                MonsterMakeoverAppBar(
                    context = context,
                    isSoundMute = viewModel.isSoundMute,
                    dailyGiftEnabled = isDailyGiftAvailable,
                    rewardEnabled = rewardsToClaim > 0,
                    currentCoins = currentCoins,
                    onVolumeClick = {
                        if (viewModel.isSoundMute) soundPool?.autoResume()
                        else soundPool?.autoPause()

                        val soundId = sounds.find { it.resId == R.raw.btn_common }?.soundId
                        soundScope.launch { playSound(soundPool, soundId, viewModel.isSoundMute) }
                        viewModel.isSoundMute = !viewModel.isSoundMute
                    },
                    onRewardClick = {
                        if (isDailyGiftAvailable) {
                            val soundId =
                                sounds.find { it.resId == R.raw.sfx_coin }?.soundId
                            soundScope.launch {
                                playSound(soundPool, soundId, viewModel.isSoundMute)
                            }
                            preferencesHelper.takeDailyGift()
                            isDailyGiftAvailable = preferencesHelper.checkDailyGift()
                            currentCoins = preferencesHelper.getCoins()
                        } else {
                            UnityAdsManager.rewardShowListener =
                                UnityAdsManager.RewardShowListener {
                                    val soundId =
                                        sounds.find { it.resId == R.raw.sfx_coin }?.soundId
                                    soundScope.launch {
                                        playSound(soundPool, soundId, viewModel.isSoundMute)
                                    }
                                    preferencesHelper.addCoins(20)
                                    currentCoins = preferencesHelper.getCoins()
                                    preferencesHelper.decrementRewardsToClaim()
                                        /*if (preferencesHelper.isPostNotificationsGranted()) {
                                            scheduleRewardNotification(context)
                                        }*/
                                    rewardsToClaim = preferencesHelper.getRewardsToClaim()
                                    /*if (rewardsToClaim != preferencesHelper.initRewardsToClaim)
                                        scheduleRewardReset(context)*/
                                }
                            UnityAdsManager.show(AdUnit.Rewarded_Coins, context as Activity, true)
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()
        if (currentScreen != MonsterMakeoverScreen.Create) {
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
            startDestination = MonsterMakeoverScreen.Start.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = MonsterMakeoverScreen.Start.name) {
                UnityAdsManager.load(AdUnit.Interstitial_Start_Create)
                adBannerId = AdUnit.Banner_Start
                StartScreen {
                    val soundId = sounds.find { it.resId == R.raw.btn_start_remake }?.soundId
                    soundScope.launch { playSound(soundPool, soundId, viewModel.isSoundMute) }
                    navController.navigate(MonsterMakeoverScreen.Create.name)
                    UnityAdsManager.show(
                        AdUnit.Interstitial_Start_Create,
                        context as Activity
                    )
                    canNavigateBack = true
                }
            }
            composable(route = MonsterMakeoverScreen.Create.name) {
                UnityAdsManager.load(AdUnit.Interstitial_Create_End)
                CreateScreen(
                    monsterUiState = uiState,
                    onMonsterHeadChanged = { id, locked ->
                        if(locked){
                            if(preferencesHelper.addCoins(-20)) {
                                currentCoins -=20
                                val soundId = sounds.find { it.resId == R.raw.btn_unlock}?.soundId
                                soundScope.launch { playSound(soundPool, soundId, viewModel.isSoundMute) }
                                query {  Database.lockedItemsDao().delete(MonsterItem(id)) }
                                return@CreateScreen false
                            }
                            else return@CreateScreen true
                        }
                        else {
                            viewModel.updateMonsterHead(id)
                            soundScope.launch { playRandomSound(soundPool, viewModel.isSoundMute) }
                            return@CreateScreen false
                        }
                    },
                    onMonsterEyeChanged = { id, locked ->
                        if(locked){
                            if(preferencesHelper.addCoins(-20)) {
                                currentCoins -=20
                                val soundId = sounds.find { it.resId == R.raw.btn_unlock}?.soundId
                                soundScope.launch { playSound(soundPool, soundId, viewModel.isSoundMute) }
                                query {  Database.lockedItemsDao().delete(MonsterItem(id)) }
                                return@CreateScreen false
                            }
                            else return@CreateScreen true
                        }
                        else {
                            viewModel.updateMonsterEye(id)
                            soundScope.launch { playRandomSound(soundPool, viewModel.isSoundMute) }
                            return@CreateScreen false
                        }
                    },
                    onMonsterMouthChanged = { id, locked ->
                        if(locked){
                            if(preferencesHelper.addCoins(-20)) {
                                currentCoins -=20
                                val soundId = sounds.find { it.resId == R.raw.btn_unlock}?.soundId
                                soundScope.launch { playSound(soundPool, soundId, viewModel.isSoundMute) }
                                query {  Database.lockedItemsDao().delete(MonsterItem(id)) }
                                return@CreateScreen false
                            }
                            else return@CreateScreen true
                        }
                        else {
                            viewModel.updateMonsterMouth(id)
                            soundScope.launch { playRandomSound(soundPool, viewModel.isSoundMute) }
                            return@CreateScreen false
                        }
                    },
                    onMonsterAccChanged = { id, locked ->
                        if(locked){
                            if(preferencesHelper.addCoins(-20)) {
                                currentCoins -=20
                                val soundId = sounds.find { it.resId == R.raw.btn_unlock}?.soundId
                                soundScope.launch { playSound(soundPool, soundId, viewModel.isSoundMute) }
                                query {  Database.lockedItemsDao().delete(MonsterItem(id)) }
                                return@CreateScreen false
                            }
                            else return@CreateScreen true
                        }
                        else {
                            viewModel.updateMonsterAcc(id)
                            soundScope.launch { playRandomSound(soundPool, viewModel.isSoundMute) }
                            return@CreateScreen false
                        }
                    },
                    onMonsterBodyChanged = { id, locked ->
                        if(locked){
                            if(preferencesHelper.addCoins(-20)) {
                                currentCoins -=20
                                val soundId = sounds.find { it.resId == R.raw.btn_unlock}?.soundId
                                soundScope.launch { playSound(soundPool, soundId, viewModel.isSoundMute) }
                                query {  Database.lockedItemsDao().delete(MonsterItem(id)) }
                                return@CreateScreen false
                            }
                            else return@CreateScreen true
                        }
                        else {
                            viewModel.updateMonsterBody(id)
                            soundScope.launch { playRandomSound(soundPool, viewModel.isSoundMute) }
                            return@CreateScreen false
                        }
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
                        soundScope.launch { playSound(soundPool, soundId, viewModel.isSoundMute) }
                        navController.navigate(MonsterMakeoverScreen.End.name)
                        UnityAdsManager.show(
                            AdUnit.Interstitial_Create_End,
                            context as Activity
                        )
                    },
                    onNextButtonClicked = {
                        val soundId = sounds.find { it.resId == R.raw.btn_next }?.soundId
                        soundScope.launch { playSound(soundPool, soundId, viewModel.isSoundMute) }
                    }
                )

            }
            composable(route = MonsterMakeoverScreen.End.name) {
                adBannerId = AdUnit.Banner_End
                val screenshotState = rememberScreenshotState()
                val message = stringResource(R.string.share_message, BuildConfig.APPLICATION_ID)
                LaunchedEffect(key1 = screenshotState.bitmap) {
                    screenshotState.bitmap?.let {
                        val uri = it.saveToInternalStorage(context, "monster.png")
                        shareImageUri(context, uri, message)
                    }
                }

                EndScreen(
                    monsterUiState = uiState,
                    screenshotState = screenshotState,
                    onRemakeButtonClicked = {
                        val soundId = sounds.find { it.resId == R.raw.btn_start_remake }?.soundId
                        soundScope.launch { playSound(soundPool, soundId, viewModel.isSoundMute) }
                        remakeNewMonster(viewModel, navController)
                        canNavigateBack = false
                    }
                ) {
                    val soundId = sounds.find { it.resId == R.raw.btn_common }?.soundId
                    soundScope.launch { playSound(soundPool, soundId, viewModel.isSoundMute) }
                    screenshotState.capture()
                }
            }
        }
    }
    //Triggered when screen changed
    if (isMusicReady && !viewModel.isSoundMute)
        playBackgroundMusic(currentScreen, soundPool, soundScope, viewModel)
}

private fun playBackgroundMusic(
    currentScreen: MonsterMakeoverScreen,
    soundPool: SoundPool?,
    soundScope: CoroutineScope,
    viewModel: MainViewModel
) {
    if (viewModel.currentStreamId != 0) {
        stopBackgroundMusic(soundPool, soundScope, viewModel)
    }
    val soundId: Int? = if (currentScreen == MonsterMakeoverScreen.End) {
        sounds.find { it.resId == R.raw.bgm_end }?.soundId
    } else {
        sounds.find { it.resId == R.raw.bgm_start_create }?.soundId
    }
    if (!viewModel.isMusicPlaying) {
        soundScope.launch { viewModel.currentStreamId = playSoundInfinite(soundPool, soundId) }
        viewModel.isMusicPlaying = true
    }
}

private fun stopBackgroundMusic(
    soundPool: SoundPool?,
    soundScope: CoroutineScope,
    viewModel: MainViewModel
) {
    soundScope.launch { soundPool?.stop(viewModel.currentStreamId) }
    viewModel.isMusicPlaying = false
    Log.i("SOUND", "streamId stopped: ${viewModel.currentStreamId}")
}

private fun remakeNewMonster(
    viewModel: MainViewModel,
    navController: NavHostController
) {
    viewModel.resetMonster()
    navController.popBackStack(MonsterMakeoverScreen.Start.name, inclusive = false)
}