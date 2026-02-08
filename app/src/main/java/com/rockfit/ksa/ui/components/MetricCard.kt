package com.rockfit.ksa.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rockfit.ksa.model.HealthMetric
import com.rockfit.ksa.model.MetricStatus

@Composable
fun MetricCard(metric: HealthMetric, modifier: Modifier = Modifier) {
    val accent = when (metric.status) {
        MetricStatus.Stable -> Color(0xFF22C55E)
        MetricStatus.Warning -> Color(0xFFF59E0B)
        MetricStatus.Critical -> Color(0xFFEF4444)
    }

    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = metric.title,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.outline
            )
            Text(text = "${metric.value} ${metric.unit}", style = MaterialTheme.typography.titleLarge)
            Text(text = metric.status.name, style = MaterialTheme.typography.labelLarge, color = accent)
        }
    }
}
