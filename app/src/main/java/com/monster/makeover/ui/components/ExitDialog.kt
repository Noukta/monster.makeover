package com.monster.makeover.ui.components

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.monster.makeover.R
import com.monster.makeover.ui.theme.MainFont

@Composable
fun ExitDialog(activity: Activity, onDismiss: () -> Unit){
    AlertDialog(
        onDismissRequest =  onDismiss,
        title = { Text(text = stringResource(R.string.exit), fontFamily = MainFont) },
        text = { Text(text = stringResource(R.string.text_quit), fontFamily = MainFont) },
        confirmButton = {
            Button( onClick = onDismiss,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary)
            ) {
                Text("No")
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = {
                    activity.finish()
                }
            ) {
                Text("Yes")
            }
        }
    )
}