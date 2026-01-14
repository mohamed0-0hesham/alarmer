package com.hesham0_0.alarmer.ui.quiz

import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.hesham0_0.alarmer.ui.theme.AlarmerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        showOnLockScreen()
        enableEdgeToEdge()
        
        setContent {
            AlarmerTheme {
                QuizScreen(
                    onDismiss = {
                        finish()
                    }
                )
            }
        }
    }

    private fun showOnLockScreen() {
        setShowWhenLocked(true)
        setTurnScreenOn(true)

        val keyguardManager = getSystemService(KEYGUARD_SERVICE) as KeyguardManager
        keyguardManager.requestDismissKeyguard(this, null)
    }
}
