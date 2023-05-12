package com.example.createmonster

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.createmonster.ui.theme.CreateMonsterTheme
import com.example.createmonster.utils.UnityAdsManager
import com.example.createmonster.utils.loadSounds

class MainActivity : ComponentActivity() {
    private lateinit var soundPool: SoundPool
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setMaxStreams(4)
            .setAudioAttributes(audioAttributes)
            .build()
        loadSounds(this, soundPool)

        setContent {
            CreateMonsterTheme {
                CreateMonsterApp(soundPool)
            }
        }
        //UnityAdsManager.initialize(applicationContext)
    }

}