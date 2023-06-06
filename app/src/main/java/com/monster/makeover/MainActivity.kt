package com.monster.makeover

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.monster.makeover.ui.MonsterMakeoverApp
import com.monster.makeover.ui.theme.MonsterMakeoverTheme

class MainActivity : ComponentActivity() {
    private val viewModel = MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MonsterMakeoverTheme {
                MonsterMakeoverApp(viewModel = viewModel)
                //TestCrashButton()
            }
        }
        lifecycle.addObserver(viewModel)
    }
}


//for firebase crashlytics initialization
/*
@Composable
fun TestCrashButton(){
    Button(onClick = { throw RuntimeException("Test Crash")}) {
        Text(text = "Test Crash")
    }
}*/
