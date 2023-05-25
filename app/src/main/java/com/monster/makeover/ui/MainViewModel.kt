package com.monster.makeover.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.monster.makeover.constants.Time.POST_NOTIFICATIONS_PERMISSION_REQUEST_INTERVAL
import com.monster.makeover.data.DataSource
import com.monster.makeover.data.MonsterUiState
import com.monster.makeover.utils.PreferencesHelper
import com.monster.makeover.utils.loadSounds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(), DefaultLifecycleObserver {

    //uiState
    private val _uiState = MutableStateFlow(MonsterUiState())
    val uiState: StateFlow<MonsterUiState> = _uiState.asStateFlow()
    //sound
    var currentStreamId = 0
        private set
    var isSoundMute = false
    //sound loading
    var soundPool: SoundPool? = createSoundPool()
    val soundScope = CoroutineScope(Dispatchers.IO)
    private var loadingCounter by mutableStateOf(0)
    val isSoundLoaded by derivedStateOf { loadingCounter == DataSource.sounds.size }
    //review app
    private var startTime: Long = 0

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
        _uiState.value = MonsterUiState()
    }

    // Sound Control
    fun setCurrentStreamId(streamId: Int){
        currentStreamId = streamId
    }
    private fun createSoundPool(): SoundPool {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()
        val soundPool =
            SoundPool.Builder().setMaxStreams(2).setAudioAttributes(audioAttributes).build()

        soundPool?.setOnLoadCompleteListener { _, id, status ->
            if (status == 0) {
                Log.i("LOAD SOUNDS", "Progress ${++loadingCounter}/${DataSource.sounds.size}")
            } else {
                Log.e("LOAD SOUNDS", "Could not load sound $id, status is $status")
            }
        }
        return soundPool
    }

    //MainActivity LifeCycle Observing
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        loadSounds(owner as Activity, soundPool)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            grantPostNotificationPermission(owner as Activity)
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        startTime = System.currentTimeMillis()
        soundScope.launch { soundPool?.autoResume() }
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        soundScope.launch { soundPool?.autoPause() }
        var playTime = System.currentTimeMillis() - startTime
        playTime += PreferencesHelper.getTotalPlayTime()
        PreferencesHelper.setTotalPlayTime(playTime)

    }

    // Post Notifications Permission Request
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun grantPostNotificationPermission(context: Context) {
        val permission = Manifest.permission.POST_NOTIFICATIONS
        val isPostNotificationsGranted = (ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED)

        if(isPostNotificationsGranted)
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

        //TODO: show dialog for permission request

    }
}
