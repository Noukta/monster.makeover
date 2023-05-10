package com.example.createmonster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.createmonster.ui.theme.CreateMonsterTheme
import com.example.createmonster.utils.UnityAdsManager

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CreateMonsterTheme {
                CreateMonsterApp()
            }
        }
        UnityAdsManager.initialize(applicationContext)
    }

}