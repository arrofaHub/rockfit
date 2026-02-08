package com.rockfit.ksa

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.rockfit.ksa.ui.RockfitApp
import com.rockfit.ksa.data.sync.SyncScheduler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        SyncScheduler.schedule(this)
        setContent { RockfitApp() }
    }
}
