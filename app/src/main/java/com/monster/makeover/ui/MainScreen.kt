package com.monster.makeover.ui

import android.app.Activity
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
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.monster.makeover.BuildConfig
import com.monster.makeover.MainViewModel
import com.monster.makeover.R
import com.monster.ads.unityAds.AdUnit
import com.monster.ads.unityAds.UnityAdsManager
import com.monster.makeover.constants.Game
import com.monster.makeover.constants.ItemType
import com.monster.makeover.constants.ReviewChoice
import com.monster.makeover.constants.Time.PLAY_TIME_BEFORE_REVIEW
import com.monster.makeover.ext.onMonsterItemChanged
import com.monster.makeover.ext.rateApp
import com.monster.makeover.ext.saveToInternalStorage
import com.monster.makeover.ext.shareImageUri
import com.monster.makeover.notifs.scheduleDailyNotification
import com.monster.makeover.ui.components.ExitDialog
import com.monster.makeover.ui.components.MonsterMakeoverAppBar
import com.monster.makeover.ui.components.ReviewDialog
import com.monster.makeover.ui.screens.CreateScreen
import com.monster.makeover.ui.screens.EndScreen
import com.monster.makeover.ui.screens.StartScreen
import com.monster.makeover.utils.PreferencesHelper
import com.monster.makeover.utils.SoundHelper
import com.smarttoolfactory.screenshot.rememberScreenshotState

enum class MonsterMakeoverScreen {
    Start,
    Create,
    End
}

@Composable
fun MonsterMakeoverApp(
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
    //Rewards and gifts
    var isDailyGiftAvailable by remember {
        mutableStateOf(
            PreferencesHelper.isDailyGiftAvailable()
        ) 
    }
    var isRewardAvailable by remember {
        mutableStateOf(
            PreferencesHelper.isRewardAvailable()
        )
    }
    var availableCoins by remember {
        mutableStateOf(
            PreferencesHelper.getAvailableCoins()
        )
    }
    if (isRewardAvailable)
        UnityAdsManager.load(AdUnit.Rewarded_Coins)

    Scaffold(
        topBar = {
            if (currentScreen == MonsterMakeoverScreen.Create) {
                MonsterMakeoverAppBar(
                    context = context,
                    isSoundMute = SoundHelper.isSoundMute,
                    dailyGiftEnabled = isDailyGiftAvailable,
                    rewardEnabled = isRewardAvailable,
                    availableCoins = availableCoins,
                    onVolumeClick = {
                        SoundHelper.isSoundMute = !SoundHelper.isSoundMute
                        if (SoundHelper.isSoundMute) SoundHelper.pauseMusic()
                        else SoundHelper.playMusic()
                        SoundHelper.playSound(SoundHelper.commonSound)
                    },
                    onRewardClick = {
                        if (isDailyGiftAvailable) {
                            SoundHelper.playSound(SoundHelper.coinSound)
                            PreferencesHelper.resetLastDailyGiftTime()
                            PreferencesHelper.addCoins(Game.DailyGift)
                            isDailyGiftAvailable = false
                            scheduleDailyNotification(context)
                        } else {
                            UnityAdsManager.rewardShowListener =
                                UnityAdsManager.RewardShowListener {
                                    SoundHelper.playSound(SoundHelper.coinSound)
                                    PreferencesHelper.resetLastRewardTime()
                                    PreferencesHelper.addCoins(Game.Reward)
                                    isRewardAvailable = false
                                }
                            UnityAdsManager.show(AdUnit.Rewarded_Coins, context as Activity, true)
                        }
                        availableCoins = PreferencesHelper.getAvailableCoins()
                    }
                )
            }
        },
        bottomBar = {
            if (currentScreen == MonsterMakeoverScreen.Start) {
                MonsterMakeoverAppBar(
                    context = context,
                    isSoundMute = SoundHelper.isSoundMute,
                    dailyGiftEnabled = isDailyGiftAvailable,
                    rewardEnabled = isRewardAvailable,
                    availableCoins = availableCoins,
                    onVolumeClick = {
                        SoundHelper.isSoundMute = !SoundHelper.isSoundMute
                        if (SoundHelper.isSoundMute) SoundHelper.pauseMusic()
                        else SoundHelper.playMusic()
                        SoundHelper.playSound(SoundHelper.commonSound)
                    },
                    onRewardClick = {
                        if (isDailyGiftAvailable) {
                            SoundHelper.playSound(SoundHelper.coinSound)
                            PreferencesHelper.resetLastDailyGiftTime()
                            PreferencesHelper.addCoins(Game.DailyGift)
                            isDailyGiftAvailable = false
                            scheduleDailyNotification(context)
                        } else {
                            UnityAdsManager.rewardShowListener =
                                UnityAdsManager.RewardShowListener {
                                    SoundHelper.playSound(SoundHelper.coinSound)
                                    PreferencesHelper.resetLastRewardTime()
                                    PreferencesHelper.addCoins(Game.Reward)
                                    isRewardAvailable = false
                                }
                            UnityAdsManager.show(AdUnit.Rewarded_Coins, context as Activity, true)
                        }
                        availableCoins = PreferencesHelper.getAvailableCoins()
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
                    SoundHelper.playSound(SoundHelper.startSound)
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
                    monsterState = uiState,
                    onMonsterHeadChanged = { id, locked ->
                        return@CreateScreen onMonsterItemChanged(id, locked, ItemType.Head, viewModel){
                            availableCoins -=20
                        }
                    },
                    onMonsterEyeChanged = { id, locked ->
                        return@CreateScreen onMonsterItemChanged(id, locked, ItemType.Eye, viewModel){
                            availableCoins -=20
                        }
                    },
                    onMonsterMouthChanged = { id, locked ->
                        return@CreateScreen onMonsterItemChanged(id, locked, ItemType.Mouth, viewModel){
                            availableCoins -=20
                        }
                    },
                    onMonsterAccChanged = { id, locked ->
                        return@CreateScreen onMonsterItemChanged(id, locked, ItemType.Accessory, viewModel){
                            availableCoins -=20
                        }
                    },
                    onMonsterBodyChanged = { id, locked ->
                        return@CreateScreen onMonsterItemChanged(id, locked, ItemType.Body, viewModel){
                            availableCoins -=20
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
                        SoundHelper.playSound(SoundHelper.doneSound)
                        navController.navigate(MonsterMakeoverScreen.End.name)
                        UnityAdsManager.show(
                            AdUnit.Interstitial_Create_End,
                            context as Activity
                        )
                    },
                    onNextButtonClicked = {
                        SoundHelper.playSound(SoundHelper.nextSound)
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
                    monsterState = uiState,
                    screenshotState = screenshotState,
                    onRemakeButtonClicked = {
                        SoundHelper.playSound(SoundHelper.startSound)
                        remakeNewMonster(viewModel, navController)
                        canNavigateBack = false
                    }
                ) {
                    if(!PreferencesHelper.isSharedRecently()) {
                        SoundHelper.playSound(SoundHelper.coinSound)
                        PreferencesHelper.addCoins(Game.Reward)
                        availableCoins = PreferencesHelper.getAvailableCoins()
                        PreferencesHelper.updateLastShareTime()
                    }
                    SoundHelper.playSound(SoundHelper.commonSound)
                    screenshotState.capture()
                }
            }
        }
    }

    //App review
    var isReviewDialogAvailable by rememberSaveable{
        mutableStateOf(false)
    }

    LaunchedEffect(currentScreen){
        val reviewStatus = PreferencesHelper.getReviewStatus()
        val totalPlayTime = PreferencesHelper.getTotalPlayTime()
        val playtimeBeforeReview: Long = when(reviewStatus){
            ReviewChoice.REMIND -> PLAY_TIME_BEFORE_REVIEW
            else -> 2 * PLAY_TIME_BEFORE_REVIEW
        }
        if (totalPlayTime >= playtimeBeforeReview) {
            if (currentScreen == MonsterMakeoverScreen.End) {
                isReviewDialogAvailable = true
            }
        }

        if(currentScreen == MonsterMakeoverScreen.End)
            SoundHelper.prepare(SoundHelper.endMusic, context)
        else
            SoundHelper.prepare(SoundHelper.startMusic, context)
        SoundHelper.playMusic()
    }

    if(isReviewDialogAvailable){
        ReviewDialog {reviewStatus ->
            isReviewDialogAvailable = false
            PreferencesHelper.setTotalPlayTime(0L)
            PreferencesHelper.setReviewStatus(reviewStatus)
            if(reviewStatus == ReviewChoice.YES) {
                SoundHelper.playSound(SoundHelper.coinSound)
                PreferencesHelper.addCoins(Game.DailyGift * 2)
                availableCoins = PreferencesHelper.getAvailableCoins()
                rateApp(context)
            }
        }
    }

    if(viewModel.showExit){
        ExitDialog(context as Activity) {
            viewModel.showExit = false
        }
    }
}

private fun remakeNewMonster(
    viewModel: MainViewModel,
    navController: NavHostController
) {
    viewModel.resetMonster()
    navController.popBackStack(MonsterMakeoverScreen.Start.name, inclusive = false)
}