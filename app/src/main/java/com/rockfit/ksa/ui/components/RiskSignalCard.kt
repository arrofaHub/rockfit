package com.rockfit.ksa.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rockfit.ksa.model.MetricStatus
import com.rockfit.ksa.model.RiskSignal

@Composable
fun RiskSignalCard(signal: RiskSignal) {
    Card(shape = RoundedCornerShape(18.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(
                    modifier = Modifier
                        .size(10.dp)
                        .background(severityColor(signal.severity), shape = RoundedCornerShape(10.dp))
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = stringResource(id = signal.titleRes), style = MaterialTheme.typography.titleMedium)
            }
            Text(text = stringResource(id = signal.descriptionRes), style = MaterialTheme.typography.bodySmall)
            Text(text = stringResource(id = signal.impactRes), style = MaterialTheme.typography.labelMedium, color = Color(0xFF1E3A8A))
        }
    }
}

private fun severityColor(severity: MetricStatus): Color {
    return when (severity) {
        MetricStatus.Stable -> Color(0xFF1AAE6F)
        MetricStatus.Warning -> Color(0xFFF0A13E)
        MetricStatus.Critical -> Color(0xFFD85B5B)
    }
}
