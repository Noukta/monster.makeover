package com.monster.makeover.notifs

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import com.monster.makeover.MainActivity
import com.monster.makeover.notifs.Settings.POST_NOTIFICATIONS_PERMISSION_REQUEST_INTERVAL
import com.monster.makeover.utils.PreferencesHelper

// Post Notifications Permission Request
fun requestNotificationsPermission(context: Context) {
    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.POST_NOTIFICATIONS
    } else {
        TODO("VERSION.SDK_INT < TIRAMISU")
    }
    val isPermissionGranted = (ContextCompat.checkSelfPermission(context, permission)
            == PackageManager.PERMISSION_GRANTED)

    if(isPermissionGranted)
        return

    val lastPostNotificationsRequestTime = PreferencesHelper.getLastPostNotificationsRequestTime()
    val currentTime = System.currentTimeMillis()
    val isPermissionRequestAvailable = when{
        lastPostNotificationsRequestTime == 0L -> true
        (currentTime - lastPostNotificationsRequestTime
                >= POST_NOTIFICATIONS_PERMISSION_REQUEST_INTERVAL) ->
            true
        else -> false

    }
    if(!isPermissionRequestAvailable)
        return

    val requestPermissionLauncher =
        (context as MainActivity).registerForActivityResult(ActivityResultContracts.RequestPermission()){}

    when {
        shouldShowRequestPermissionRationale(context, permission) -> {
            val explanation = "Get daily free rewards by allowing notifications"
            val dialog = AlertDialog.Builder(context)
                .setTitle("Daily Rewards")
                .setMessage(explanation)
                .setPositiveButton("OK") { _, _ ->
                    requestPermissionLauncher.launch(permission)
                }
                .create()
            dialog.show()
        }

        else -> {
            requestPermissionLauncher.launch(permission)
        }
    }
    PreferencesHelper.resetLastPostNotificationsRequestTime()
}