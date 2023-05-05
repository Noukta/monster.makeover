package com.example.createmonster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.createmonster.ui.theme.CreateMonsterTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CreateMonsterTheme {
                CreateMonsterApp()
            }
        }
    }

}