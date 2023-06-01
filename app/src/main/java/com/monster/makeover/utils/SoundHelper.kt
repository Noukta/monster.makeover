package com.monster.makeover.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.Log
import androidx.annotation.RawRes
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.monster.makeover.R
import com.monster.makeover.data.Sound
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object SoundHelper{
    val selectSounds = listOf(
        Sound(R.raw.btn_select_1),
        Sound(R.raw.btn_select_2),
        Sound(R.raw.btn_select_3),
        Sound(R.raw.btn_select_4),
        Sound(R.raw.btn_select_5),
        Sound(R.raw.btn_select_6),
        Sound(R.raw.btn_select_7),
        Sound(R.raw.btn_select_8),
        Sound(R.raw.btn_select_9),
        Sound(R.raw.btn_select_10),
        Sound(R.raw.btn_select_11),
        Sound(R.raw.btn_select_12),
        Sound(R.raw.btn_select_13),
        Sound(R.raw.btn_select_14),
        Sound(R.raw.btn_select_15),
        Sound(R.raw.btn_select_16)
    )
    val unlockSound = Sound(R.raw.btn_unlock)
    val commonSound = Sound(R.raw.btn_common)
    val nextSound = Sound(R.raw.btn_next)
    val doneSound = Sound(R.raw.btn_done)
    val startSound = Sound(R.raw.btn_start_remake)
    val coinSound = Sound(R.raw.sfx_coin)

    val startMusic = R.raw.bgm_start_create
    val endMusic = R.raw.bgm_end

    var isSoundMute: Boolean = false

    private lateinit var soundPool: SoundPool
    private lateinit var exoPlayer: ExoPlayer
    private val soundScope = CoroutineScope(Dispatchers.IO)

    fun init(context: Context) {
        //Init ExoPlayer
        exoPlayer = ExoPlayer.Builder(context).build()
        exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
        //Init SoundPool
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setMaxStreams(2)
            .setAudioAttributes(audioAttributes)
            .build()
        soundPool.setOnLoadCompleteListener { _, id, status ->
            if (status == 0) {
                Log.i("LOAD SOUNDS", "Sound $id was loaded")
            } else {
                Log.e("LOAD SOUNDS", "Could not load sound $id, status is $status")
            }
        }
        //load sound effects
        loadSounds(context)
    }

    private fun loadSounds(context: Context) {
        soundScope.launch {
            selectSounds.forEach {
                val soundId = soundPool.load(context, it.resId, 1)
                it.soundId = soundId
            }
            unlockSound.soundId = soundPool.load(context, unlockSound.resId,1)
            commonSound.soundId = soundPool.load(context, commonSound.resId,1)
            nextSound.soundId = soundPool.load(context, nextSound.resId,1)
            doneSound.soundId = soundPool.load(context, doneSound.resId,1)
            startSound.soundId = soundPool.load(context, startSound.resId,1)
            coinSound.soundId = soundPool.load(context, coinSound.resId,1)
        }
    }

    fun playSound(sound: Sound) {
        soundScope.launch {
            if (!isSoundMute) {
                sound.soundId?.let { soundPool.play(it, 1f, 1f, 0, 0, 1f) }
            }
        }
    }

    fun prepare(@RawRes musicId: Int, context: Context){
        val audioUri = "rawresource://${context.packageName}/$musicId"
        val mediaItem = MediaItem.Builder().setUri(audioUri).build()
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
    }
    fun playMusic(){
        if(!isSoundMute && !exoPlayer.isPlaying)
            exoPlayer.play()
    }

    fun pauseMusic(){
        exoPlayer.pause()
    }
}