package com.monster.makeover.ads.admob

import android.app.Activity
import android.content.Context
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentInformation.ConsentStatus
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform

object Consent {
    private const val testMode = false
    private lateinit var consentInformation: ConsentInformation
    private lateinit var params: ConsentRequestParameters
    private lateinit var debugSettings: ConsentDebugSettings

    fun getConsentStatus(context: Context, callback: (Int) -> Unit) {
        initConsentInformation(context)
        consentInformation.requestConsentInfoUpdate(
            context as Activity,
            params,
            {
                if (consentInformation.isConsentFormAvailable) {
                    callback(ConsentStatus.REQUIRED)
                } else {
                    callback(ConsentStatus.NOT_REQUIRED)
                }
            },
            {
                callback(ConsentStatus.UNKNOWN)
            }
        )
    }

    private fun initConsentInformation(context: Context) {
        if (testMode) { // Debugging
            debugSettings = ConsentDebugSettings.Builder(context)
                .setDebugGeography(
                    ConsentDebugSettings
                        .DebugGeography
                        .DEBUG_GEOGRAPHY_EEA
                )
                .addTestDeviceHashedId("BBFA57031F59697BEC83F2A81BC8E900")
                .build()
        }
        // Set tag for underage of consent. Here false means users are not underage.
        val paramsBuilder = ConsentRequestParameters.Builder()
            .setTagForUnderAgeOfConsent(true)
        if (testMode) paramsBuilder.setConsentDebugSettings(debugSettings)
        params = paramsBuilder.build()

        consentInformation = UserMessagingPlatform.getConsentInformation(context)
        if (testMode) consentInformation.reset()
    }
}