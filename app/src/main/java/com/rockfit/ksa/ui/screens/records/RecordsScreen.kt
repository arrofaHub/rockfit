package com.rockfit.ksa.ui.screens.records

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.rockfit.ksa.ui.components.SectionHeader

@Composable
fun RecordsScreen() {
    val profile = SampleData.athleteProfile

    LazyColumn(
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SectionHeader(title = stringResource(id = R.string.athlete_profile))
        }
        item {
            Card {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = profile.fullName, style = MaterialTheme.typography.titleLarge)
                    InfoRow(label = stringResource(id = R.string.birth_year), value = profile.birthYear.toString())
                    InfoRow(label = stringResource(id = R.string.height), value = "${profile.heightCm} cm")
                    InfoRow(label = stringResource(id = R.string.weight), value = "${profile.weightKg} kg")
                    InfoRow(label = stringResource(id = R.string.bmi_label), value = profile.bmi.toString())
                    InfoRow(label = stringResource(id = R.string.body_fat), value = "${profile.bodyFatPercent}%")
                    InfoRow(label = stringResource(id = R.string.muscle_mass), value = "${profile.muscleMassKg} kg")
                }
            }
        }

        item {
            SectionHeader(title = stringResource(id = R.string.injury_timeline))
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

        item {
            SectionHeader(title = stringResource(id = R.string.competition_history))
        }
        item {
            Card {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = stringResource(id = R.string.competition_history_line),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        item {
            SectionHeader(title = stringResource(id = R.string.certifications_achievements))
        }
        item {
            Card {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = stringResource(id = R.string.certifications_line),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
