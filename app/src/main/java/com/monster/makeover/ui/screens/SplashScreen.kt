package com.monster.makeover.ui.screens

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.monster.makeover.R
import com.monster.makeover.ads.admob.AdmobConstant
import com.monster.makeover.ads.admob.AppOpenAdManager
import com.monster.makeover.ui.theme.MonsterMakeoverTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(context: Context, isConsentAccepted: Boolean, onLoadFinished: () -> Unit) {
    val appOpenAdManager = AppOpenAdManager()
    val scope = rememberCoroutineScope()

    LaunchedEffect(isConsentAccepted) {
        if(isConsentAccepted) {
            for (i in AdmobConstant.COUNTER_TIME * 2 downTo 0) {
                delay(500)
                appOpenAdManager.showAdIfAvailable(
                    context as Activity,
                    AdmobConstant.APP_OPEN
                ) {
                    scope.launch {
                        onLoadFinished()
                    }
                }
            }
            onLoadFinished()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_round),
            contentDescription = null,
            modifier = Modifier.size(200.dp),
        )
        CircularProgressIndicator(modifier = Modifier.size(190.dp))
    }
}

@Preview(showSystemUi = true)
@Composable
fun SplashScreenPreview(){
    MonsterMakeoverTheme {
        SplashScreen(LocalContext.current, true) {}
    }
}