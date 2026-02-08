package com.rockfit.ksa.ui.screens.health

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rockfit.ksa.model.SampleData
import com.rockfit.ksa.R
import com.rockfit.ksa.ui.components.InfoRow
import com.rockfit.ksa.ui.components.MetricCard
import com.rockfit.ksa.ui.components.SectionHeader

@Composable
fun HealthScreen() {
    val report = SampleData.medicalReport

    LazyColumn(
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SectionHeader(title = stringResource(id = R.string.vital_signs))
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SampleData.vitals.chunked(2).forEach { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        row.forEach { metric ->
                            Box(modifier = Modifier.weight(1f)) {
                                MetricCard(metric = metric)
                            }
                        }
                        if (row.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }

        item {
            SectionHeader(title = stringResource(id = R.string.recent_readings))
        }

        items(SampleData.healthReadings) { reading ->
            Card {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = reading.label, style = MaterialTheme.typography.titleMedium)
                    InfoRow(label = stringResource(id = R.string.value), value = reading.value)
                    InfoRow(label = stringResource(id = R.string.recorded), value = reading.timestamp)
                }
            }
        }

        item {
            SectionHeader(title = stringResource(id = R.string.wearables_feed))
        }

        item {
            Card {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = stringResource(id = R.string.health_connect_active), style = MaterialTheme.typography.titleMedium)
                    Text(text = stringResource(id = R.string.wearables_pending))
                    InfoRow(label = stringResource(id = R.string.last_sync), value = stringResource(id = R.string.last_sync_time))
                }
            }
        }

        item {
            SectionHeader(title = stringResource(id = R.string.medical_status))
        }

        item {
            Card {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = report.summary, style = MaterialTheme.typography.bodyLarge)
                    InfoRow(label = stringResource(id = R.string.doctor), value = report.doctor)
                    InfoRow(label = stringResource(id = R.string.report_date), value = report.date)
                    InfoRow(label = stringResource(id = R.string.return_to_play), value = report.returnToPlay.name)
                    Text(text = report.prescription, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        item {
            SectionHeader(title = stringResource(id = R.string.injury_log))
        }

        items(SampleData.injuries) { injury ->
            Card {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = injury.title, style = MaterialTheme.typography.titleMedium)
                    InfoRow(label = stringResource(id = R.string.date), value = injury.date)
                    InfoRow(label = stringResource(id = R.string.status), value = injury.status)
                    InfoRow(label = stringResource(id = R.string.expected_return), value = injury.expectedReturn)
                }
            }
        }
    }
}
