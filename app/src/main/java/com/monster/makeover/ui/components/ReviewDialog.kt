package com.monster.makeover.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.monster.makeover.R
import com.monster.makeover.constants.ReviewChoice
import com.monster.makeover.ui.theme.MonsterMakeoverTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewDialog(onDismissRequest: (ReviewChoice) -> Unit){
    var isAnswered by remember{
        mutableStateOf(false)
    }
    var isEnjoying by remember{
        mutableStateOf(false)
    }
    ModalBottomSheet(
        onDismissRequest = { onDismissRequest(ReviewChoice.REMIND) },
        modifier = Modifier.height(300.dp)
    ) {
        if(!isAnswered) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(R.string.enjoy_ask),
                    modifier = Modifier.padding(bottom = 50.dp),
                    style = MaterialTheme.typography.headlineLarge
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 50.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(onClick = {
                        isEnjoying = false
                        isAnswered = true
                        onDismissRequest(ReviewChoice.NOT_ENJOY)
                    },
                        modifier = Modifier.weight(1f).padding(10.dp)
                    ) {
                        Text(text = stringResource(R.string.no))
                    }
                    Button(onClick = {
                        isEnjoying = true
                        isAnswered = true
                    },
                        modifier = Modifier.weight(1f).padding(10.dp)
                    ) {
                        Text(text = stringResource(R.string.yes))
                    }
                }
            }
        }
        else {
            if (isEnjoying) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.rate_ask),
                        modifier = Modifier.padding(bottom = 50.dp),
                        style = MaterialTheme.typography.headlineLarge
                    )
                    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 50.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedButton(onClick = {
                            onDismissRequest(ReviewChoice.REMIND)
                        },
                            modifier = Modifier.weight(1f).padding(10.dp)
                        ) {
                            Text(text = stringResource(R.string.remind))
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 50.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedButton(onClick = {
                            onDismissRequest(ReviewChoice.NO)
                        },
                            modifier = Modifier.weight(1f).padding(10.dp)
                        ) {
                            Text(text = stringResource(R.string.no))
                        }
                        Button(onClick = {
                            onDismissRequest(ReviewChoice.YES)
                        },
                            modifier = Modifier.weight(1f).padding(10.dp)
                        ) {
                            Text(text = stringResource(R.string.yes))
                        }
                    }
                }
            }
            else{
                //TODO: send a bad review to email
            }
        }
    }
}

@Preview
@Composable
fun ReviewDialogPreview(){
    MonsterMakeoverTheme {
        ReviewDialog {

        }
    }
}
