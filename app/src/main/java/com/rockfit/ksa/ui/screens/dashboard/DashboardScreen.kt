package com.rockfit.ksa.ui.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rockfit.ksa.model.AthleteProfileCard
import com.rockfit.ksa.model.SampleData
import com.rockfit.ksa.R
import com.rockfit.ksa.ui.components.InfoRow
import com.rockfit.ksa.ui.components.InsightCarousel
import com.rockfit.ksa.ui.components.MediaCardData
import com.rockfit.ksa.ui.components.MediaCarousel
import com.rockfit.ksa.ui.components.SectionHeader

@Composable
fun DashboardScreen(activeAthlete: AthleteProfileCard?) {
    val athlete = SampleData.athleteSnapshot
    val profile = SampleData.athleteProfile
    val displayName = activeAthlete?.name ?: athlete.name
    val displaySport = activeAthlete?.sport ?: athlete.sport
    val displayHydration = activeAthlete?.lifestyle?.hydrationLiters ?: profile.hydrationLiters
    val displaySleep = activeAthlete?.lifestyle?.avgSleepHours?.toInt() ?: profile.sleepScore
    val dailyFocusItems = listOf(
        stringResource(id = R.string.daily_focus_item_one),
        stringResource(id = R.string.daily_focus_item_two),
        stringResource(id = R.string.daily_focus_item_three)
    )

    LazyColumn(
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = displayName, style = MaterialTheme.typography.headlineLarge)
                    Text(text = displaySport, style = MaterialTheme.typography.bodyLarge)
                    InfoRow(label = stringResource(id = R.string.recovery_score), value = "${athlete.recoveryScore}%")
                    InfoRow(label = stringResource(id = R.string.readiness), value = athlete.readiness)
                    InfoRow(label = stringResource(id = R.string.injury_risk), value = athlete.injuryRisk)
                }
            }
        }

        item {
            SectionHeader(title = stringResource(id = R.string.today_overview))
        }

        item {
            Card {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    InfoRow(label = stringResource(id = R.string.training_load), value = "78")
                    InfoRow(label = stringResource(id = R.string.hydration), value = "${displayHydration} L")
                    InfoRow(label = stringResource(id = R.string.sleep_score), value = "${displaySleep}")
                    InfoRow(label = stringResource(id = R.string.bmi_label), value = "${profile.bmi}")
                }
            }
        }

        item {
            SectionHeader(title = stringResource(id = R.string.quick_playbooks))
        }

        item {
            MediaCarousel(
                items = listOf(
                    MediaCardData(
                        title = stringResource(id = R.string.playbook_recovery_title),
                        subtitle = stringResource(id = R.string.playbook_recovery_subtitle),
                        icon = Icons.Filled.Bedtime,
                        colors = listOf(Color(0xFF3B6FD8), Color(0xFF7BAAF7))
                    ),
                    MediaCardData(
                        title = stringResource(id = R.string.playbook_hydration_title),
                        subtitle = stringResource(id = R.string.playbook_hydration_subtitle),
                        icon = Icons.Filled.LocalDrink,
                        colors = listOf(Color(0xFF1FAE8C), Color(0xFF66E6C7))
                    ),
                    MediaCardData(
                        title = stringResource(id = R.string.playbook_speed_title),
                        subtitle = stringResource(id = R.string.playbook_speed_subtitle),
                        icon = Icons.Filled.DirectionsRun,
                        colors = listOf(Color(0xFFF49B42), Color(0xFFF8C07C))
                    )
                )
            )
        }

        item {
            SectionHeader(title = stringResource(id = R.string.daily_focus))
        }

        items(dailyFocusItems) { item ->
            Card {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = item,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        item {
            SectionHeader(title = stringResource(id = R.string.smart_alerts))
        }

        items(SampleData.alerts.take(2)) { alert ->
            Card {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = stringResource(id = alert.titleRes), style = MaterialTheme.typography.titleMedium)
                    Text(text = stringResource(id = alert.detailRes), style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        item {
            SectionHeader(title = stringResource(id = R.string.ai_insights))
        }

        item {
            InsightCarousel(insights = SampleData.aiInsights.take(4))
        }

        item {
            SectionHeader(title = stringResource(id = R.string.athlete_passport), action = stringResource(id = R.string.open))
        }

        item {
            Card {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = stringResource(id = R.string.passport_line))
                    Text(text = stringResource(id = R.string.passport_hint), style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
