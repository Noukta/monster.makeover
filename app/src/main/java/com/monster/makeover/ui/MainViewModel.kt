package com.monster.makeover.ui

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import com.monster.makeover.data.MonsterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MonsterUiState())
    val uiState: StateFlow<MonsterUiState> = _uiState.asStateFlow()

    var currentStreamId = 0
    var isMusicPlaying = false
    var isSoundMute = false

    fun updateMonsterHead(id: Int) {
        _uiState.update { current ->
            current.copy(head = id)
        }
    }

    fun updateMonsterEye(id: Int) {
        _uiState.update { current ->
            current.copy(eye = id)
        }
    }

    fun updateMonsterMouth(id: Int) {
        _uiState.update { current ->
            current.copy(mouth = id)
        }
    }

    fun updateMonsterAcc(id: Int) {
        _uiState.update { current ->
            current.copy(acc = id)
        }
    }

    fun updateMonsterBody(id: Int) {
        _uiState.update { current ->
            current.copy(body = id)
        }
    }

    fun updateEyePosition(offset: Offset) {
        _uiState.update { current ->
            current.copy(eyeOffset = offset)
        }
    }

    fun updateMouthPosition(offset: Offset) {
        _uiState.update { current ->
            current.copy(mouthOffset = offset)
        }
    }

    fun updateAccPosition(offset: Offset) {
        _uiState.update { current ->
            current.copy(accOffset = offset)
        }
    }

    fun resetMonster() {
        _uiState.value = MonsterUiState()
    }

}
