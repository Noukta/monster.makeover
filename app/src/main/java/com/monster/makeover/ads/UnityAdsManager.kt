package com.monster.makeover.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.monster.makeover.ads.UnityAdsConfig.adUnits
import com.monster.makeover.ads.UnityAdsConfig.testMode
import com.monster.makeover.ads.UnityAdsConfig.unityAppID
import com.unity3d.ads.IUnityAdsInitializationListener
import com.unity3d.ads.IUnityAdsLoadListener
import com.unity3d.ads.IUnityAdsShowListener
import com.unity3d.ads.UnityAds
import com.unity3d.services.banners.BannerErrorInfo
import com.unity3d.services.banners.BannerView
import com.unity3d.services.banners.BannerView.IListener
import com.unity3d.services.banners.UnityBannerSize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class AdState {
    READY, STARTED, CLICKED, COMPLETED, SKIPPED, NOT_AVAILABLE, LOAD_FAILED, SHOW_FAILED
}

enum class AdUnit {
    Banner_Start, Banner_Create, Banner_End, Rewarded_Coins, Interstitial_Start_Create, Interstitial_Create_End
}

object UnityAdsConfig {
    const val testMode: Boolean = true
    const val unityAppID: String = "5274719"

    var adUnits = mutableMapOf(
        AdUnit.Banner_Start to AdState.NOT_AVAILABLE,
        AdUnit.Banner_Create to AdState.NOT_AVAILABLE,
        AdUnit.Banner_End to AdState.NOT_AVAILABLE,
        AdUnit.Rewarded_Coins to AdState.NOT_AVAILABLE,
        AdUnit.Interstitial_Start_Create to AdState.NOT_AVAILABLE,
        AdUnit.Interstitial_Create_End to AdState.NOT_AVAILABLE
    )
}

object UnityAdsManager : IUnityAdsInitializationListener {
    private var initializationComplete = false
    private val loadListener = LoadListener()
    private val showListener = ShowListener()
    private val bannerListener = BannerListener()
    lateinit var rewardShowListener: IUnityAdsShowListener

    private val adsScope = CoroutineScope(Dispatchers.IO)

    fun load(adUnitId: AdUnit) {
        Log.d("UnityAdsManager", "$adUnitId: ${adUnits[adUnitId]}")
        adsScope.launch {
            if (adUnits[adUnitId] != AdState.READY) UnityAds.load(
                adUnitId.name,
                loadListener
            )
        }
    }

    fun show(adUnitId: AdUnit, activity: Activity, reward: Boolean = false) {
        adsScope.launch {
            if (adUnits[adUnitId] == AdState.READY) UnityAds.show(
                activity,
                adUnitId.name,
                if (reward) rewardShowListener else showListener
            )
        }
    }

    fun loadBanner(
        activity: Activity,
        adUnitId: AdUnit,
        size: UnityBannerSize = UnityBannerSize(320, 50)
    ): BannerView {
        val bannerView = BannerView(activity, adUnitId.name, size)
        bannerView.listener = bannerListener
        adsScope.launch { bannerView.load() }
        return bannerView
    }

    private class LoadListener : IUnityAdsLoadListener {
        override fun onUnityAdsAdLoaded(placementId: String?) {
            placementId?.let {
                adUnits[AdUnit.valueOf(it)] = AdState.READY
                Log.d("UnityAdsManager", "$placementId: ${adUnits[AdUnit.valueOf(it)]}")
            }
        }

        override fun onUnityAdsFailedToLoad(
            placementId: String?, error: UnityAds.UnityAdsLoadError?, message: String?
        ) {
            placementId?.let {
                adUnits[AdUnit.valueOf(it)] = AdState.LOAD_FAILED
                Log.e(
                    "UnityAdsManager",
                    "Unity Ads failed to show ad for $placementId with error: [$error] $message"
                )
            }
        }
    }

    private class ShowListener : IUnityAdsShowListener {
        override fun onUnityAdsShowFailure(
            placementId: String?, error: UnityAds.UnityAdsShowError?, message: String?
        ) {
            placementId?.let {
                adUnits[AdUnit.valueOf(it)] = AdState.SHOW_FAILED
                Log.e(
                    "UnityAdsManager",
                    "Unity Ads failed to show ad for $placementId with error: [$error] $message"
                )
            }
        }

        override fun onUnityAdsShowStart(placementId: String?) {
            placementId?.let {
                adUnits[AdUnit.valueOf(it)] = AdState.STARTED
                Log.d(
                    "UnityAdsManager", "$it: ${adUnits[AdUnit.valueOf(it)]}"
                )
            }
        }

        override fun onUnityAdsShowClick(placementId: String?) {
            placementId?.let {
                adUnits[AdUnit.valueOf(it)] = AdState.CLICKED
                Log.d(
                    "UnityAdsManager", "$it: ${adUnits[AdUnit.valueOf(it)]}"
                )
            }
        }

        override fun onUnityAdsShowComplete(
            placementId: String?, state: UnityAds.UnityAdsShowCompletionState?
        ) {
            placementId?.let {
                if (state == UnityAds.UnityAdsShowCompletionState.COMPLETED) {
                    adUnits[AdUnit.valueOf(it)] = AdState.COMPLETED

                } else {
                    adUnits[AdUnit.valueOf(it)] = AdState.SKIPPED
                }
                Log.d(
                    "UnityAdsManager", "$it: ${adUnits[AdUnit.valueOf(it)]}"
                )
            }
        }

    }

    class RewardShowListener(val onRewardShowComplete: () -> Unit) : IUnityAdsShowListener {
        override fun onUnityAdsShowFailure(
            placementId: String?, error: UnityAds.UnityAdsShowError?, message: String?
        ) {
            placementId?.let {
                adUnits[AdUnit.valueOf(it)] = AdState.SHOW_FAILED
                Log.e(
                    "UnityAdsManager",
                    "Unity Ads failed to show ad for $placementId with error: [$error] $message"
                )
            }
        }

        override fun onUnityAdsShowStart(placementId: String?) {
            placementId?.let {
                adUnits[AdUnit.valueOf(it)] = AdState.STARTED
                Log.d(
                    "UnityAdsManager", "$it: ${adUnits[AdUnit.valueOf(it)]}"
                )
            }
        }

        override fun onUnityAdsShowClick(placementId: String?) {
            placementId?.let {
                adUnits[AdUnit.valueOf(it)] = AdState.CLICKED
                Log.d(
                    "UnityAdsManager", "$it: ${adUnits[AdUnit.valueOf(it)]}"
                )
            }
        }

        override fun onUnityAdsShowComplete(
            placementId: String?, state: UnityAds.UnityAdsShowCompletionState?
        ) {
            placementId?.let {
                if (state == UnityAds.UnityAdsShowCompletionState.COMPLETED) {
                    adUnits[AdUnit.valueOf(it)] = AdState.COMPLETED
                    onRewardShowComplete()
                } else {
                    adUnits[AdUnit.valueOf(it)] = AdState.SKIPPED
                }
                load(AdUnit.valueOf(it))
                Log.d(
                    "UnityAdsManager", "$it: ${adUnits[AdUnit.valueOf(it)]}"
                )
            }
        }

    }

    private class BannerListener : IListener {
        override fun onBannerLoaded(bannerAdView: BannerView?) {
            bannerAdView?.let {
                adUnits[AdUnit.valueOf(it.placementId)] = AdState.READY
                Log.d(
                    "UnityAdsManager",
                    "${it.placementId}: ${adUnits[AdUnit.valueOf(it.placementId)]}"
                )
            }
        }

        override fun onBannerClick(bannerAdView: BannerView?) {
            bannerAdView?.let {
                adUnits[AdUnit.valueOf(it.placementId)] = AdState.CLICKED
                Log.d(
                    "UnityAdsManager",
                    "${it.placementId}: ${adUnits[AdUnit.valueOf(it.placementId)]}"
                )
            }
        }

        override fun onBannerFailedToLoad(bannerAdView: BannerView?, errorInfo: BannerErrorInfo?) {
            bannerAdView?.let {
                adUnits[AdUnit.valueOf(it.placementId)] = AdState.LOAD_FAILED
                Log.e(
                    "UnityAdsManager",
                    "Unity Ads failed to show ad for ${it.placementId} with error: $errorInfo"
                )
            }
        }

        override fun onBannerLeftApplication(bannerAdView: BannerView?) {
            bannerAdView?.let {
                adUnits[AdUnit.valueOf(it.placementId)] = AdState.CLICKED
                Log.d(
                    "UnityAdsManager",
                    "${it.placementId}: ${adUnits[AdUnit.valueOf(it.placementId)]}"
                )
            }
        }
    }

    fun initialize(context: Context) {
        UnityAds.initialize(context, unityAppID, testMode, this)
    }

    override fun onInitializationComplete() {
        initializationComplete = true
        Log.d(
            "UnityAdsManager", "Unity Ads initialization complete"
        )
    }

    override fun onInitializationFailed(
        error: UnityAds.UnityAdsInitializationError?, message: String?
    ) {
        initializationComplete = false
        Log.e(
            "UnityAdsManager", "Unity Ads failed to initialize with error: [$error] $message"
        )
    }
}
