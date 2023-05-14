package com.example.createmonster.utils

import android.content.Context
import android.media.SoundPool
import com.example.createmonster.data.DataSource

fun loadSounds(context: Context, soundPool: SoundPool?) {
    DataSource.sounds.forEach {
        val soundId = soundPool?.load(context, it.resId, 1)
        it.soundId = soundId
    }
}

fun playSound(soundPool: SoundPool?, soundId: Int?, isSoundMute: Boolean): Int {
    if (soundPool != null && soundId != null && !isSoundMute) {
        return soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
    }
    return 0
}

fun playRandomSound(soundPool: SoundPool?, isSoundMute: Boolean): Int {
    return playSound(soundPool, DataSource.sounds[(0..15).random()].soundId, isSoundMute)
}

fun playSoundInfinite(soundPool: SoundPool?, soundId: Int?): Int {
    if (soundPool != null && soundId != null) {
        return soundPool.play(soundId, 1f, 1f, 1, -1, 1f)
    }
    return 0
}