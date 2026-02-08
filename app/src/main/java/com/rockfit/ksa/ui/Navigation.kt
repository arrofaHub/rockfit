package com.rockfit.ksa.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.ui.graphics.vector.ImageVector
import com.rockfit.ksa.R

sealed class RockfitDestination(
    val route: String,
    @StringRes val labelRes: Int,
    val icon: ImageVector
) {
    object Dashboard : RockfitDestination("dashboard", R.string.dashboard, Icons.Filled.Home)
    object Health : RockfitDestination("health", R.string.health, Icons.Filled.Favorite)
    object Skills : RockfitDestination("skills", R.string.skills, Icons.Filled.TrackChanges)
    object Performance : RockfitDestination("performance", R.string.performance, Icons.Filled.Analytics)
    object Alerts : RockfitDestination("alerts", R.string.alerts, Icons.Filled.Notifications)
    object Records : RockfitDestination("records", R.string.records, Icons.Filled.Folder)
    object Settings : RockfitDestination("settings", R.string.settings, Icons.Filled.Settings)
    object VRTraining : RockfitDestination("vr_training", R.string.vr_training, Icons.Filled.TrackChanges)
    object TalentEngine : RockfitDestination("talent_engine", R.string.talent_engine, Icons.Filled.Analytics)

    companion object {
        val bottomTabs = listOf(Dashboard, Health, Skills, Performance, Alerts)
    }
}

object RockfitIcons {
    val Settings = Icons.Filled.Settings
}
