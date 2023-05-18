package com.monster.makeover

import android.Manifest
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import com.monster.makeover.data.DataSource.sounds
import com.monster.makeover.receivers.isAppRunning
import com.monster.makeover.receivers.scheduleDailyNotification
import com.monster.makeover.ui.MonsterMakeoverApp
import com.monster.makeover.ui.theme.MonsterMakeoverTheme
import com.monster.makeover.utils.PreferencesHelper
import com.monster.makeover.utils.loadSounds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private var soundPool: SoundPool? = createSoundPool()
    private val soundScope = CoroutineScope(Dispatchers.IO)
    private var loadingCounter by mutableStateOf(0)
    private val isMusicReady by derivedStateOf { loadingCounter == sounds.size }

    private var isPostNotificationsGranted = false
    private var nextRequestTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadSounds(this, soundPool)
        nextRequestTime = with(PreferencesHelper(this)) { getNextPermissionRequestTime() }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            grantPostNotificationPermission()
        }
        setContent {
            MonsterMakeoverTheme {
                MonsterMakeoverApp(soundPool, isMusicReady)
            }
        }
        isAppRunning = true
        Log.d("WORK", "isAppRunning $isAppRunning")
    }

    override fun onRestart() {
        super.onRestart()
        soundScope.launch { soundPool?.autoResume() }
    }

    override fun onStop() {
        super.onStop()
        soundScope.launch { soundPool?.autoPause() }
        Log.d("NOTIFICATION", "Post Notifications Granted $isPostNotificationsGranted")
        if (!isPostNotificationsGranted) {
            val currentTime = System.currentTimeMillis()
            if (nextRequestTime <= currentTime)
            //val nextRequestTime = System.currentTimeMillis() + AlarmManager.INTERVAL_DAY * 2
            //for test
                nextRequestTime = System.currentTimeMillis() + 30000
            Log.d("NOTIFICATION", "next request at ${nextRequestTime - currentTime}")
            with(PreferencesHelper(this)) {
                setNextPermissionRequestTime(nextRequestTime)
            }
        } else {
            scheduleDailyNotification(this)
        }
    }

    private fun createSoundPool(): SoundPool {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()
        val soundPool =
            SoundPool.Builder().setMaxStreams(2).setAudioAttributes(audioAttributes).build()

        soundPool?.setOnLoadCompleteListener { _, id, status ->
            if (status == 0) {
                Log.i("LOAD SOUNDS", "Progress ${++loadingCounter}/${sounds.size}")
            } else {
                Log.e("LOAD SOUNDS", "Could not load sound $id, status is $status")
            }
        }
        return soundPool
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun grantPostNotificationPermission() {
        val permission = Manifest.permission.POST_NOTIFICATIONS
        isPostNotificationsGranted = (ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED)
        val shouldRequest = nextRequestTime <= System.currentTimeMillis()
        Log.d("NOTIFICATION", "isPostNotificationsGranted $isPostNotificationsGranted")
        Log.d("NOTIFICATION", "shouldRequest $shouldRequest")


        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                isPostNotificationsGranted = isGranted
                with(PreferencesHelper(this)) {
                    setPostNotificationsGranted(isGranted)
                }
                Log.d(
                    "NOTIFICATION",
                    "Post Notifications Permission Granted $isPostNotificationsGranted"
                )
            }

        if (!isPostNotificationsGranted && shouldRequest) when {
            shouldShowRequestPermissionRationale(permission) -> {
                Log.d("NOTIFICATION", "rational dialog")
                val explanation = "Get daily free rewards by allowing notifications"
                val dialog = AlertDialog.Builder(this)
                    .setTitle("Daily Rewards")
                    .setMessage(explanation)
                    .setPositiveButton("OK") { _, _ ->
                        Log.d("NOTIFICATION", "permission dialog")
                        requestPermissionLauncher.launch(permission)
                    }
                    .create()
                dialog.show()
            }

            else -> {
                Log.d("NOTIFICATION", "permission dialog")
                requestPermissionLauncher.launch(permission)
            }
        }
    }
}