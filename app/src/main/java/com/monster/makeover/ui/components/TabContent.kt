package com.monster.makeover.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.monster.makeover.R
import com.monster.makeover.data.DataSource
import com.monster.makeover.db.DatabaseHolder
import com.monster.makeover.ext.query

@Composable
fun TabContent(index: Int, onMonsterItemChanged: (Int, Boolean) -> Boolean){
    Surface(
        modifier = Modifier.padding(top = 8.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.onPrimaryContainer)
    ) {
        LazyHorizontalGrid(
            rows = GridCells.Fixed(3), contentPadding = PaddingValues(8.dp)
        ) {
            items(DataSource.allParts[index]) { part ->
                var locked by remember { mutableStateOf(false) }
                LaunchedEffect(true) {
                    query {
                        locked = DatabaseHolder.Database.lockedItemsDao().exists(part.second)
                    }
                }
                Box(
                    Modifier.clickable(role = Role.Button) {
                        locked = onMonsterItemChanged(part.second, locked)
                    }, Alignment.Center

                ) {
                    val icon = if(locked) R.drawable.btn_locked else R.drawable.btn_item
                    Image(
                        painterResource(id = icon),
                        contentDescription = "",
                        Modifier.padding(horizontal = 4.dp)
                    )
                    Image(
                        painter = painterResource(id = part.first),
                        contentDescription = "",
                        modifier = Modifier
                            .width(48.dp)
                            .height(48.dp)
                    )
                    if(locked){
                        Image(
                            painter = painterResource(id = R.drawable.coins_lock),
                            contentDescription = "",
                            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth()
                        )

                    }
                }
            }
        }
    }
}