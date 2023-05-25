package com.monster.makeover.ext

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.monster.makeover.constants.URL

fun rateApp(context: Context) {
    val url = URL.PLAY_STORE
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
}