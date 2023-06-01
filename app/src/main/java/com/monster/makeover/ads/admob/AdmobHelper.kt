package com.monster.makeover.ads.admob

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.monster.makeover.ads.admob.AdmobConstant.INTERSTITIAL_CREATE_END
import com.monster.makeover.ads.admob.AdmobConstant.INTERSTITIAL_START_CREATE
import com.monster.makeover.ads.admob.AdmobConstant.REWARDED_COINS

object AdmobHelper {

    private var mInterstitialAds: MutableMap<String, InterstitialAd?> = mutableMapOf(
        INTERSTITIAL_START_CREATE to null,
        INTERSTITIAL_CREATE_END to null,
    )
    private var mRewardedAds: MutableMap<String, RewardedAd?> = mutableMapOf(
        REWARDED_COINS to null
    )
    private const val TAG = "AdmobHelper"

    fun loadInterstitial(
        context: Context,
        adUnit: String,
        onAdShowed: () -> Unit = {},
        onAdDismissed: () -> Unit = {}
    ) {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(context, adUnit, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, "Interstitial Ad $adError")
                mInterstitialAds[adUnit] = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Interstitial Ad was loaded.")
                mInterstitialAds[adUnit] = interstitialAd
            }
        })
        mInterstitialAds[adUnit]?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d(TAG, "Interstitial Ad was clicked.")
                loadInterstitial(context, adUnit)
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                Log.d(TAG, "Interstitial Ad dismissed fullscreen content.")
                onAdDismissed()
                loadInterstitial(context, adUnit)
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                // Called when ad fails to show.
                Log.e(TAG, "Interstitial Ad failed to show fullscreen content.")
                onAdDismissed()
                loadInterstitial(context, adUnit)
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d(TAG, "Interstitial Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Interstitial Ad showed fullscreen content.")
                onAdShowed()
            }
        }
    }

    fun showInterstitial(context: Context, adUnit: String) {
        if (mInterstitialAds[adUnit] != null) {
            mInterstitialAds[adUnit]?.show(context as Activity)
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.")
        }
    }

    fun loadRewarded(
        context: Context,
        adUnit: String,
        onAdShowed: () -> Unit = {},
        onAdDismissed: () -> Unit = {}
    ) {
        val adRequest = AdRequest.Builder().build()

        RewardedAd.load(context, adUnit, adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, "Rewarded Ad $adError")
                mRewardedAds[adUnit] = null
            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {
                Log.d(TAG, "Rewarded Ad was loaded.")
                mRewardedAds[adUnit] = rewardedAd
            }
        })
        mRewardedAds[adUnit]?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d(TAG, "Rewarded Ad was clicked.")
                loadInterstitial(context, adUnit)
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                Log.d(TAG, "Rewarded Ad dismissed fullscreen content.")
                onAdDismissed()
                loadInterstitial(context, adUnit)
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                // Called when ad fails to show.
                Log.e(TAG, "Rewarded Ad failed to show fullscreen content.")
                onAdDismissed()
                loadInterstitial(context, adUnit)
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d(TAG, "Rewarded Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Rewarded Ad showed fullscreen content.")
                onAdShowed()
            }
        }
    }

    fun showRewarded(context: Context, adUnit: String, onRewarded: ()->Unit) {
        if (mRewardedAds[adUnit] != null) {
            mRewardedAds[adUnit]?.show(context as Activity){
                // Handle the reward.
                onRewarded()
            }
        } else {
            Log.d("TAG", "The rewarded ad wasn't ready yet.")
        }
    }
}