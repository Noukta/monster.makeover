package com.example.createmonster.utils

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.SoundPool
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import com.example.createmonster.BuildConfig
import com.example.createmonster.data.DataSource.sounds
import java.io.File
import java.io.FileOutputStream

fun Bitmap.saveToInternalStorage(context: Context, fileName: String): Uri {
    val imagesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val imageFile = File(imagesDir, fileName)
    val outputStream = FileOutputStream(imageFile)
    this.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    outputStream.flush()
    outputStream.close()
    return FileProvider.getUriForFile(
        context,
        "${BuildConfig.APPLICATION_ID}.provider",
        imageFile
    )
}

fun shareImageUri(context: Context, uri: Uri, message: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        clipData = ClipData.newRawUri(null, uri)
        putExtra(Intent.EXTRA_STREAM, uri)
        putExtra(Intent.EXTRA_TEXT, message)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        type = "image/png"
    }
    context.startActivity(Intent.createChooser(intent, "Share Monster"))
}

fun openPrivacyPolicy(context: Context) {
    val url = "https://wkeystudio.blogspot.com/2023/04/privacy-policy.html"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
}

fun loadSounds(context: Context, soundPool: SoundPool?) {
    sounds.forEach {
        val soundId = soundPool?.load(context, it.resId, 1)
        it.soundId = soundId
    }
}

fun playSound(soundPool: SoundPool?, soundId: Int?): Int{
    if (soundPool != null && soundId != null) {
            return soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
    }
    return 0
}

fun playRandomSound(soundPool: SoundPool?): Int{
    return playSound(soundPool, sounds[(0..15).random()].soundId)
}

fun playSoundInfinite(soundPool: SoundPool?, soundId:Int?): Int{
    if (soundPool != null && soundId != null) {
            return soundPool.play(soundId, 1f, 1f, 1, -1, 1f)
        }
    return 0
}