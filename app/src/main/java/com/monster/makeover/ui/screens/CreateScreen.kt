package com.monster.makeover.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.monster.makeover.R
import com.monster.makeover.data.MonsterState
import com.monster.makeover.ui.components.CustomTabRow
import com.monster.makeover.ui.components.MonsterCanvas
import com.monster.makeover.ui.components.PrimaryButton
import com.monster.makeover.ui.components.SecondaryButton
import com.monster.makeover.ui.components.TabContent
import com.monster.makeover.ui.theme.MonsterMakeoverTheme

@Composable
fun CreateScreen(
    monsterState: MonsterState = MonsterState(),
    onMonsterHeadChanged: (Int, Boolean) -> Boolean = { _: Int, _: Boolean -> true},
    onMonsterEyeChanged: (Int, Boolean) -> Boolean = { _: Int, _: Boolean -> false},
    onMonsterMouthChanged: (Int, Boolean) -> Boolean = { _: Int, _: Boolean -> true},
    onMonsterAccChanged: (Int, Boolean) -> Boolean = { _: Int, _: Boolean -> false},
    onMonsterBodyChanged: (Int, Boolean) -> Boolean = { _: Int, _: Boolean -> true},
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
            MonsterCanvas(
                monsterState = monsterState,
                onEyePositionChanged = onEyePositionChanged,
                onMouthPositionChanged = onMouthPositionChanged,
                onAccPositionChanged = onAccPositionChanged,
                selectedItem = selectedTabIndex
            )
        }
        Column(
            modifier = Modifier
                .weight(.4f)
                .padding(16.dp)
        ) {
            CustomTabRow(selectedTabIndex) {
                when (selectedTabIndex) {
                    0 -> NextButton(monsterState.head != null) {
                        onNextButtonClicked()
                        selectedTabIndex++
                    }

                    1 -> NextButton(monsterState.eye != null) {
                        onNextButtonClicked()
                        selectedTabIndex++
                    }

                    2 -> NextButton(monsterState.mouth != null) {
                        onNextButtonClicked()
                        selectedTabIndex++
                    }

                    3 -> NextButton(monsterState.acc != null) {
                        onNextButtonClicked()
                        selectedTabIndex++
                    }

                    4 -> DoneButton(monsterState.body != null) { onDoneButtonClicked() }
                }
            }

            when (selectedTabIndex) {
                0 -> TabContent(selectedTabIndex, onMonsterHeadChanged)
                1 -> TabContent(selectedTabIndex, onMonsterEyeChanged)
                2 -> TabContent(selectedTabIndex, onMonsterMouthChanged)
                3 -> TabContent(selectedTabIndex, onMonsterAccChanged)
                else -> TabContent(selectedTabIndex, onMonsterBodyChanged)
            }
        }
    }
}

@Composable
fun NextButton(enabled: Boolean, onClick: () -> Unit) {
    SecondaryButton(onClick, enabled = enabled){
        Text(text = stringResource(R.string.next))
    }
}

@Composable
fun DoneButton(enabled: Boolean, onClick: () -> Unit) {
    PrimaryButton(R.string.done, onClick, enabled = enabled)
}

@Preview(showBackground = true)
@Composable
fun CreatePreview() {
    MonsterMakeoverTheme {
        CreateScreen(
            monsterState = MonsterState(
                head = R.drawable.head_04,
                eye = R.drawable.eye_11,
                mouth = R.drawable.mouth_20,
                acc = R.drawable.acc_00,
                body = R.drawable.body_26
            )
        )
    }
}
