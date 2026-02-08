package com.rockfit.ksa.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rockfit.ksa.R

@Composable
fun WhatIfSimulator() {
    var load by remember { mutableFloatStateOf(70f) }
    var sleep by remember { mutableFloatStateOf(7.2f) }
    var hydration by remember { mutableFloatStateOf(2.4f) }

    val readiness = calculateReadiness(load, sleep, hydration)
    val riskLabel = when {
        readiness >= 85 -> "Low risk"
        readiness >= 70 -> "Moderate risk"
        else -> "High risk"
    }
    val recoveryHours = calculateRecoveryHours(load, sleep, hydration)

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(22.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        listOf(Color(0xFF101B3E), Color(0xFF203A8B))
                    )
                )
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = stringResource(id = R.string.ai_readiness_twin),
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
            Text(
                text = stringResource(id = R.string.ai_readiness_hint),
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xDFFFFFFF)
            )

            MetricSlider(
                label = stringResource(id = R.string.training_load),
                value = load,
                range = 40f..100f,
                valueLabel = "${load.toInt()}"
            ) { load = it }

            MetricSlider(
                label = stringResource(id = R.string.sleep_hours),
                value = sleep,
                range = 5f..9f,
                valueLabel = String.format("%.1f", sleep)
            ) { sleep = it }

            MetricSlider(
                label = stringResource(id = R.string.hydration_liters),
                value = hydration,
                range = 1.5f..3.5f,
                valueLabel = String.format("%.1f", hydration)
            ) { hydration = it }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0x22FFFFFF), shape = RoundedCornerShape(16.dp))
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = stringResource(id = R.string.predicted_readiness),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xCFFFFFFF)
                    )
                    Text(
                        text = "$readiness%",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = stringResource(id = R.string.risk_outlook),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xCFFFFFFF)
                    )
                    Text(
                        text = riskLabel,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(id = R.string.recovery_hours, recoveryHours),
                        style = MaterialTheme.typography.labelMedium,
                        color = Color(0xE6FFFFFF)
                    )
                }
            }
        }
    }
}

@Composable
private fun MetricSlider(
    label: String,
    value: Float,
    range: ClosedFloatingPointRange<Float>,
    valueLabel: String,
    onValueChange: (Float) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, color = Color.White, style = MaterialTheme.typography.labelMedium)
            Text(text = valueLabel, color = Color(0xE6FFFFFF), style = MaterialTheme.typography.labelMedium)
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = range
        )
    }
}

private fun calculateReadiness(load: Float, sleep: Float, hydration: Float): Int {
    val loadPenalty = (load - 60f).coerceAtLeast(0f) * 0.6f
    val sleepBoost = (sleep - 6.5f).coerceAtLeast(0f) * 8f
    val hydrationBoost = (hydration - 2.0f).coerceAtLeast(0f) * 10f
    val raw = 78f - loadPenalty + sleepBoost + hydrationBoost
    return raw.coerceIn(50f, 98f).toInt()
}

private fun calculateRecoveryHours(load: Float, sleep: Float, hydration: Float): Int {
    val loadImpact = (load - 60f).coerceAtLeast(0f) * 0.4f
    val sleepOffset = (sleep - 7f).coerceAtLeast(0f) * -2.0f
    val hydrationOffset = (hydration - 2.2f).coerceAtLeast(0f) * -1.5f
    val hours = 18f + loadImpact + sleepOffset + hydrationOffset
    return hours.coerceIn(10f, 36f).toInt()
}
