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
import com.monster.makeover.ads.admob.AdmobConstant.INTERSTITIAL_CREATE_END
import com.monster.makeover.ads.admob.AdmobConstant.INTERSTITIAL_START_CREATE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object AdmobHelper {

    private var mInterstitialAds: MutableMap<String, InterstitialAd?> = mutableMapOf(
        INTERSTITIAL_START_CREATE to null,
        INTERSTITIAL_CREATE_END to null,
    )
    private const val TAG = "AdmobHelper"

    fun loadInterstitial(context: Context, adUnit: String) {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(context, adUnit, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError.toString())
                mInterstitialAds[adUnit] = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAds[adUnit] = interstitialAd
                mInterstitialAds[adUnit]?.fullScreenContentCallback =
                    object : FullScreenContentCallback() {
                        override fun onAdClicked() {
                            // Called when a click is recorded for an ad.
                            Log.d(TAG, "Ad was clicked.")
                            loadInterstitial(context, adUnit)
                        }

                        override fun onAdDismissedFullScreenContent() {
                            // Called when ad is dismissed.
                            Log.d(TAG, "Ad dismissed fullscreen content.")
                            loadInterstitial(context, adUnit)
                        }

                        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                            // Called when ad fails to show.
                            Log.e(TAG, "Ad failed to show fullscreen content.")
                            loadInterstitial(context, adUnit)
                        }

                        override fun onAdImpression() {
                            // Called when an impression is recorded for an ad.
                            Log.d(TAG, "Ad recorded an impression.")
                        }

                        override fun onAdShowedFullScreenContent() {
                            // Called when ad is shown.
                            Log.d(TAG, "Ad showed fullscreen content.")
                        }
                    }
            }
        })
    }

    fun showInterstitial(context: Context, adUnit: String) {
        CoroutineScope(Dispatchers.Main).launch {
            if (mInterstitialAds[adUnit] != null) {
                mInterstitialAds[adUnit]?.show(context as Activity)
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.")
            }
        }
    }
}