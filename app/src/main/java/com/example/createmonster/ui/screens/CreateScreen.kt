package com.example.createmonster.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.createmonster.R
import com.example.createmonster.data.DataSource.allParts
import com.example.createmonster.data.MonsterUiState
import com.example.createmonster.ui.components.CustomTabRow
import com.example.createmonster.ui.components.Monster
import com.example.createmonster.ui.components.PrimaryButton
import com.example.createmonster.ui.components.SecondaryButton

@Composable
fun CreateScreen(
    monsterUiState: MonsterUiState = MonsterUiState(),
    onMonsterHeadChanged: (Int) -> Unit = {},
    onMonsterEyeChanged: (Int) -> Unit = {},
    onMonsterMouthChanged: (Int) -> Unit = {},
    onMonsterAccChanged: (Int) -> Unit = {},
    onMonsterBodyChanged: (Int) -> Unit = {},
    onEyePositionChanged: ((Offset) -> Unit)? = null,
    onMouthPositionChanged: ((Offset) -> Unit)? = null,
    onAccPositionChanged: ((Offset) -> Unit)? = null,
    onDoneButtonClicked: () -> Unit = {},
    onNextButtonClicked: () -> Unit = {}
) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Box(
            modifier = Modifier
                .weight(.6f)
                .padding(16.dp)
        ) {
            Monster(
                monsterUiState = monsterUiState,
                onEyePositionChanged = onEyePositionChanged,
                onMouthPositionChanged = onMouthPositionChanged,
                onAccPositionChanged = onAccPositionChanged
            )
        }
        Column(
            modifier = Modifier
                .weight(.4f)
                .padding(16.dp)
        ) {
            CustomTabRow(selectedTabIndex) {
                when (selectedTabIndex) {
                    0 -> NextButton(monsterUiState.head != null) {
                        onNextButtonClicked()
                        selectedTabIndex++
                    }

                    1 -> NextButton(monsterUiState.eye != null) {
                        onNextButtonClicked()
                        selectedTabIndex++
                    }

                    2 -> NextButton(monsterUiState.mouth != null) {
                        onNextButtonClicked()
                        selectedTabIndex++
                    }

                    3 -> NextButton(monsterUiState.acc != null) {
                        onNextButtonClicked()
                        selectedTabIndex++
                    }

                    4 -> DoneButton(monsterUiState.body != null) { onDoneButtonClicked() }
                }
            }
            TabContent(
                tabIndex = selectedTabIndex,
                onMonsterHeadChanged = onMonsterHeadChanged,
                onMonsterEyeChanged = onMonsterEyeChanged,
                onMonsterMouthChanged = onMonsterMouthChanged,
                onMonsterAccChanged = onMonsterAccChanged,
                onMonsterBodyChanged = onMonsterBodyChanged
            )
        }
    }
}

@Composable
fun NextButton(enabled: Boolean, onClick: () -> Unit) {
    SecondaryButton(R.string.next, onClick, enabled = enabled)
}

@Composable
fun DoneButton(enabled: Boolean, onClick: () -> Unit) {
    PrimaryButton(R.string.done, onClick, enabled = enabled)
}

@Composable
fun TabContent(
    tabIndex: Int,
    onMonsterHeadChanged: (Int) -> Unit,
    onMonsterEyeChanged: (Int) -> Unit,
    onMonsterMouthChanged: (Int) -> Unit,
    onMonsterAccChanged: (Int) -> Unit,
    onMonsterBodyChanged: (Int) -> Unit
) {

    Surface(
        modifier = Modifier.padding(top = 8.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.onPrimaryContainer)
    ) {
        LazyHorizontalGrid(
            rows = GridCells.Fixed(3), contentPadding = PaddingValues(8.dp)
        ) {
            items(allParts[tabIndex]) { part ->
                Box(
                    Modifier.clickable(role = Role.Button) {
                        when (tabIndex) {
                            0 -> onMonsterHeadChanged(part.second)
                            1 -> onMonsterEyeChanged(part.second)
                            2 -> onMonsterMouthChanged(part.second)
                            3 -> onMonsterAccChanged(part.second)
                            else -> onMonsterBodyChanged(part.second)
                        }
                    }, Alignment.Center

                ) {
                    Image(
                        painterResource(id = R.drawable.btn_item),
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
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreatePreview() {
    CreateScreen(
        monsterUiState = MonsterUiState(
            head = R.drawable.head_04,
            eye = R.drawable.eye_11,
            mouth = R.drawable.mouth_20,
            acc = R.drawable.acc_00,
            body = R.drawable.body_26
        )
    )
}
