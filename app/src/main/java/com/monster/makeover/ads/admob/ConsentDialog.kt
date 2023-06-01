package com.monster.makeover.ads.admob

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import com.monster.makeover.R
import com.monster.makeover.ext.openPrivacyPolicy
import com.monster.makeover.ui.theme.MonsterMakeoverTheme
import com.monster.makeover.ui.theme.Roboto

@Composable
fun ConsentDialog(onDismiss: ()-> Unit, modifier : Modifier = Modifier){
    val context = LocalContext.current
    val properties = DialogProperties(
        dismissOnBackPress = false,
        dismissOnClickOutside = false
    )
    AlertDialog(
        onDismissRequest = {},
        modifier = modifier,
        confirmButton = {
            Button(onClick = onDismiss) {
                Text(
                    text = stringResource(id = R.string.accept)
                )
            }
        },
        title = {
            Text(
                text = stringResource(id = R.string.ready),
                textAlign = TextAlign.Center,
                fontFamily = Roboto
            )
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.consent_text),
                    textAlign = TextAlign.Center,
                    fontFamily = Roboto
                )
                ClickableText(
                    text = AnnotatedString(stringResource(id = R.string.and_privacy_policy)),
                    onClick = {
                        openPrivacyPolicy(context)
                    }
                )
            }

        },
        properties = properties
    )
}

@Preview(showBackground = true)
@Composable
fun ConsentDialogPreview() {
    MonsterMakeoverTheme {
        ConsentDialog({}
        )
    }
}