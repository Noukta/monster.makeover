package com.monster.makeover

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.monster.makeover.data.DataSource.sounds
import com.monster.makeover.ui.MonsterMakeoverApp
import com.monster.makeover.ui.theme.CreateMonsterTheme
import com.monster.makeover.utils.PreferencesHelper
import com.monster.makeover.utils.UnityAdsManager
import com.monster.makeover.utils.loadSounds

class MainActivity : ComponentActivity() {
    private lateinit var preferencesHelper: PreferencesHelper
    private var soundPool: SoundPool? = createSoundPool()
    private var loadingCounter by mutableStateOf(0)
    private val isMusicReady by derivedStateOf { loadingCounter == sounds.size }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadSounds(this, soundPool)
        setContent {
            CreateMonsterTheme {
                MonsterMakeoverApp(soundPool, isMusicReady)
            }
        }
        UnityAdsManager.initialize(applicationContext)
        preferencesHelper = PreferencesHelper(this)
        preferencesHelper.incrementAppOpenCount()
    }

    override fun onRestart() {
        super.onRestart()
        soundPool = createSoundPool()
        loadSounds(this, soundPool)
    }

    override fun onStop() {
        super.onStop()
        soundPool?.release()
        soundPool = null
        loadingCounter = 0
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
}