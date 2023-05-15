package com.monster.makeover.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun openPrivacyPolicy(context: Context) {
    val url = "https://wkeystudio.blogspot.com/2023/04/privacy-policy.html"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
}