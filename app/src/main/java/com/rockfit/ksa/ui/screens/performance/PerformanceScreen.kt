package com.rockfit.ksa.ui.screens.performance

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rockfit.ksa.model.AthleteProfileCard
import com.rockfit.ksa.model.SampleData
import com.rockfit.ksa.R
import com.rockfit.ksa.ui.components.InfoRow
import com.rockfit.ksa.ui.components.InsightCarousel
import com.rockfit.ksa.ui.components.RiskSignalCard
import com.rockfit.ksa.ui.components.SectionHeader
import com.rockfit.ksa.ui.components.DigitalTwinExperience
import com.rockfit.ksa.ui.components.WhatIfSimulator
import com.rockfit.ksa.ui.components.PredictiveRiskCard
import com.rockfit.ksa.data.PredictiveEngine
import com.rockfit.ksa.model.PredictiveInput

@Composable
fun PerformanceScreen(activeAthlete: AthleteProfileCard?) {
    LazyColumn(
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SectionHeader(title = stringResource(id = R.string.training_load_vs_output))
        }
        item {
            Card {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = stringResource(id = R.string.load_trend), style = MaterialTheme.typography.titleMedium)
                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .padding(top = 16.dp)
                    ) {
                        val points = listOf(12f, 18f, 16f, 24f, 22f, 28f, 26f)
                        val max = points.maxOrNull() ?: 1f
                        val stepX = size.width / (points.size - 1)
                        points.forEachIndexed { index, value ->
                            if (index == 0) return@forEachIndexed
                            val x1 = (index - 1) * stepX
                            val x2 = index * stepX
                            val y1 = size.height - (points[index - 1] / max) * size.height
                            val y2 = size.height - (value / max) * size.height
                            drawLine(Color(0xFF1E3A8A), Offset(x1, y1), Offset(x2, y2), 6f)
                        }
                    }
                }
            }
        }

        item {
            SectionHeader(title = stringResource(id = R.string.training_sessions))
        }

        items(SampleData.trainingSessions) { session ->
            Card {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = session.label, style = MaterialTheme.typography.titleMedium)
                    InfoRow(label = stringResource(id = R.string.load_label), value = session.load.toString())
                    InfoRow(label = stringResource(id = R.string.duration_label), value = "${session.durationMinutes} min")
                    InfoRow(label = stringResource(id = R.string.distance_label), value = "${session.distanceKm} km")
                }
            }
        }

        item {
            SectionHeader(title = stringResource(id = R.string.ai_insights))
        }
        item {
            InsightCarousel(insights = SampleData.aiInsights)
        }

        item {
            SectionHeader(title = stringResource(id = R.string.predictive_analytics))
        }
        items(
            PredictiveEngine.buildScores(
                PredictiveInput(
                    loadIndex = 78,
                    sleepHours = activeAthlete?.lifestyle?.avgSleepHours ?: 7.0,
                    hydrationLiters = activeAthlete?.lifestyle?.hydrationLiters ?: 2.4,
                    readinessScore = 84,
                    stressLevel = activeAthlete?.lifestyle?.stressLevel ?: com.rockfit.ksa.model.StressLevel.Moderate,
                    recentInjuryNotes = activeAthlete?.lifestyle?.recentInjuryNotes ?: "No injury notes"
                )
            )
        ) { score ->
            PredictiveRiskCard(score = score)
        }

        item {
            SectionHeader(title = stringResource(id = R.string.predictive_signals))
        }
        items(SampleData.riskSignals) { signal ->
            RiskSignalCard(signal = signal)
        }

        item {
            SectionHeader(title = stringResource(id = R.string.ai_readiness_twin))
        }
        item {
            WhatIfSimulator()
        }

        item {
            SectionHeader(title = stringResource(id = R.string.predictive_digital_twin))
        }
        item {
            DigitalTwinExperience(
                zones = SampleData.twinZones,
                lifestyle = activeAthlete?.lifestyle
            )
        }
    }
}
