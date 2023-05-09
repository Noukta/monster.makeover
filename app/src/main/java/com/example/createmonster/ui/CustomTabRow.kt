package com.example.createmonster.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.createmonster.data.DataSource.tabs

@Composable
fun CustomTabRow(
    selectedTabIndex: Int,
    lastTab: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(percent = 50)
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        tabs.forEachIndexed { index, tab ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                val icon = if (selectedTabIndex == index) {
                    tab.selectedIconResId
                } else {
                    tab.iconResId
                }
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = stringResource(tab.tabName),
                    tint = Color.Unspecified
                )
            }
        }
        Box(
            modifier = Modifier
                .weight(2f)
                .padding(end = 4.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            lastTab()
        }
    }
}