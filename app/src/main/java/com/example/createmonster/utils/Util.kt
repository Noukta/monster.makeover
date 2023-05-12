package com.example.createmonster.utils

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.SoundPool
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import com.example.createmonster.BuildConfig
import com.example.createmonster.data.DataSource.sounds
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.atomic.AtomicInteger

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

fun loadSounds(context: Context, soundPool: SoundPool) {
    val count = AtomicInteger()
    soundPool.setOnLoadCompleteListener { _, id, status ->
        if (status == 0) {
            Log.i("LOAD SOUNDS", "Progress ${count.incrementAndGet()}/${sounds.size}")
        } else {
            Log.e("LOAD SOUNDS", "Could not load sound $id, status is $status")
        }
    }
    sounds.forEach {
        val soundId = soundPool.load(context, it.resId, 1)
        it.soundId = soundId
        Log.d("LOAD SOUNDS", "${it.soundId}")
    }
}