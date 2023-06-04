package com.monster.makeover

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.monster.makeover.data.MonsterState
import com.monster.makeover.notifs.requestNotificationsPermission
import com.monster.makeover.notifs.scheduleDailyNotification
import com.monster.makeover.utils.PreferencesHelper
import com.monster.makeover.utils.SoundHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel(), DefaultLifecycleObserver {

    //uiState
    private val _uiState = MutableStateFlow(MonsterState())
    val uiState: StateFlow<MonsterState> = _uiState.asStateFlow()
    //review app
    private var startTime: Long = 0
    //exit
    var showExit by mutableStateOf(false)
    var consentStatus by mutableStateOf(PreferencesHelper.getConsentStatus())

    //UI State/Monster control
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
        _uiState.value = MonsterState()
    }

    //MainActivity LifeCycle Observing
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        requestNotificationsPermission(owner as Activity)
        if(!PreferencesHelper.isDailyGiftAvailable())
            scheduleDailyNotification(owner as Activity)

        (owner as MainActivity).onBackPressedDispatcher
            .addCallback(owner, object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                Log.d("NAVIGATION", "back pressed")
                showExit = !showExit
            }
        })
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        startTime = System.currentTimeMillis()
        SoundHelper.playMusic()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        var playTime = System.currentTimeMillis() - startTime
        playTime += PreferencesHelper.getTotalPlayTime()
        PreferencesHelper.setTotalPlayTime(playTime)
        SoundHelper.pauseMusic()
    }
}
