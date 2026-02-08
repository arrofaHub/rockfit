package com.rockfit.ksa.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rockfit.ksa.model.MetricStatus
import com.rockfit.ksa.model.RiskScore

@Composable
fun PredictiveRiskCard(score: RiskScore) {
    Card(shape = RoundedCornerShape(18.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .padding(end = 8.dp)
                        .background(statusColor(score.status), CircleShape)
                )
                Text(text = score.label, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "${score.score}%", style = MaterialTheme.typography.titleMedium, color = statusColor(score.status))
            }
            Text(text = score.explanation, style = MaterialTheme.typography.bodySmall)
        }
    }
}

private fun statusColor(status: MetricStatus): Color {
    return when (status) {
        MetricStatus.Stable -> Color(0xFF1AAE6F)
        MetricStatus.Warning -> Color(0xFFF0A13E)
        MetricStatus.Critical -> Color(0xFFD85B5B)
    }
}
