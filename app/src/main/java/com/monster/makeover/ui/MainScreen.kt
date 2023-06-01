package com.monster.makeover.ui

import android.app.Activity
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.monster.makeover.BuildConfig
import com.monster.makeover.MainViewModel
import com.monster.makeover.R
import com.monster.makeover.ads.admob.AdmobConstant
import com.monster.makeover.ads.admob.AdmobHelper
import com.monster.makeover.ads.admob.ConsentDialog
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
import com.monster.makeover.ui.screens.SplashScreen
import com.monster.makeover.ui.screens.StartScreen
import com.monster.makeover.utils.PreferencesHelper
import com.monster.makeover.utils.SoundHelper
import com.smarttoolfactory.screenshot.rememberScreenshotState

enum class MonsterMakeoverScreen {
    Splash,
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
        backStackEntry?.destination?.route ?: MonsterMakeoverScreen.Splash.name
    )
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
    if (currentScreen != MonsterMakeoverScreen.Splash && isRewardAvailable)
        AdmobHelper.loadRewarded(
            context,
            AdmobConstant.REWARDED_COINS,
            onAdShowed = {SoundHelper.pauseMusic()},
            onAdDismissed = {SoundHelper.playMusic()}
        )

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
                            availableCoins += Game.DailyGift
                        } else {
                            AdmobHelper.showRewarded(context, AdmobConstant.REWARDED_COINS){
                                SoundHelper.playSound(SoundHelper.coinSound)
                                PreferencesHelper.resetLastRewardTime()
                                PreferencesHelper.addCoins(Game.Reward)
                                isRewardAvailable = false
                                availableCoins += Game.Reward
                            }
                        }
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
                            availableCoins += Game.DailyGift
                        } else {
                            AdmobHelper.showRewarded(context, AdmobConstant.REWARDED_COINS){
                                SoundHelper.playSound(SoundHelper.coinSound)
                                PreferencesHelper.resetLastRewardTime()
                                PreferencesHelper.addCoins(Game.Reward)
                                isRewardAvailable = false
                                availableCoins += Game.Reward
                            }
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = MonsterMakeoverScreen.Splash.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = MonsterMakeoverScreen.Splash.name){
               SplashScreen(context, viewModel.isConsentAccepted){
                   navController.navigate(MonsterMakeoverScreen.Start.name)
               }
            }
            composable(route = MonsterMakeoverScreen.Start.name) {
                AdmobHelper.loadInterstitial(
                    context,
                    AdmobConstant.INTERSTITIAL_START_CREATE,
                    onAdShowed = {SoundHelper.pauseMusic()},
                    onAdDismissed = {SoundHelper.playMusic()}
                )
                isRewardAvailable = PreferencesHelper.isRewardAvailable()
                StartScreen {
                    SoundHelper.playSound(SoundHelper.startSound)
                    navController.navigate(MonsterMakeoverScreen.Create.name)
                    AdmobHelper.showInterstitial(context, AdmobConstant.INTERSTITIAL_START_CREATE)
                }
            }
            composable(route = MonsterMakeoverScreen.Create.name) {
                AdmobHelper.loadInterstitial(
                    context,
                    AdmobConstant.INTERSTITIAL_CREATE_END,
                    onAdShowed = {SoundHelper.pauseMusic()},
                    onAdDismissed = {SoundHelper.playMusic()}
                )
                isRewardAvailable = PreferencesHelper.isRewardAvailable()
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
                        AdmobHelper.showInterstitial(context, AdmobConstant.INTERSTITIAL_CREATE_END)
                    },
                    onNextButtonClicked = {
                        SoundHelper.playSound(SoundHelper.nextSound)
                    }
                )

            }
            composable(route = MonsterMakeoverScreen.End.name) {
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
        if(currentScreen == MonsterMakeoverScreen.Start)
            SoundHelper.prepare(SoundHelper.startMusic, context)
        if(currentScreen != MonsterMakeoverScreen.Splash)
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

    if(!viewModel.isConsentAccepted){
        ConsentDialog(onDismiss = {
            viewModel.isConsentAccepted = true
            PreferencesHelper.acceptConsent()
        })
    }
}

private fun remakeNewMonster(
    viewModel: MainViewModel,
    navController: NavHostController
) {
    viewModel.resetMonster()
    navController.popBackStack(MonsterMakeoverScreen.Start.name, inclusive = false)
}