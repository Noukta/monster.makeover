package com.monster.makeover

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.monster.makeover.ui.MainViewModel
import com.monster.makeover.ui.MonsterMakeoverApp
import com.monster.makeover.ui.theme.MonsterMakeoverTheme

class MainActivity : ComponentActivity() {
    private val viewModel = MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MonsterMakeoverTheme {
                MonsterMakeoverApp(viewModel = viewModel)
            }
        }
        lifecycle.addObserver(viewModel)
    }
}