package com.rockfit.ksa.ui.screens.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import com.rockfit.ksa.R
import com.rockfit.ksa.model.AthleteProfileCard
import com.rockfit.ksa.ui.UserSession

@Composable
fun SettingsScreen(
    session: UserSession?,
    athletes: List<AthleteProfileCard>,
    activeAthlete: AthleteProfileCard?,
    onSelectAthlete: (AthleteProfileCard) -> Unit,
    onOpenVr: () -> Unit,
    onOpenTalent: () -> Unit,
    onSave: () -> Unit,
    onSignOut: () -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = stringResource(id = R.string.profile), style = MaterialTheme.typography.titleMedium)
                    Text(text = "${session?.email ?: "-"}")
                    Text(text = "${stringResource(id = R.string.role_label)}: ${session?.role?.name ?: "-"}")
                }
            }
        }

        item {
            Card {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(text = stringResource(id = R.string.language), style = MaterialTheme.typography.titleMedium)
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Button(onClick = { AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en")) }) {
                            Text(text = stringResource(id = R.string.english))
                        }
                        Button(onClick = { AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("ar")) }) {
                            Text(text = stringResource(id = R.string.arabic))
                        }
                    }
                }
            }
        }

        item {
            Card {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(text = stringResource(id = R.string.active_athlete), style = MaterialTheme.typography.titleMedium)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        athletes.forEach { athlete ->
                            val selected = athlete.id == activeAthlete?.id
                            FilterChip(
                                selected = selected,
                                onClick = { onSelectAthlete(athlete) },
                                label = { Text(text = athlete.name) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(0xFF1E3A8A),
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                    Text(
                        text = activeAthlete?.sport ?: "-",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        item {
            Card {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = stringResource(id = R.string.vr_training_title), style = MaterialTheme.typography.titleMedium)
                    Text(text = stringResource(id = R.string.vr_training_hint), style = MaterialTheme.typography.bodyMedium)
                    Button(onClick = onOpenVr) {
                        Text(text = stringResource(id = R.string.vr_training))
                    }
                }
            }
        }

        item {
            Card {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = stringResource(id = R.string.talent_engine_title), style = MaterialTheme.typography.titleMedium)
                    Text(text = stringResource(id = R.string.talent_engine_hint), style = MaterialTheme.typography.bodyMedium)
                    Button(onClick = onOpenTalent) {
                        Text(text = stringResource(id = R.string.talent_engine))
                    }
                }
            }
        }

        item {
            Card {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = stringResource(id = R.string.wearables), style = MaterialTheme.typography.titleMedium)
                    Text(text = stringResource(id = R.string.health_connect))
                    Text(text = stringResource(id = R.string.garmin_stub))
                    Text(text = stringResource(id = R.string.polar_stub))
                    Text(text = stringResource(id = R.string.apple_stub))
                }
            }
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = onSave) {
                    Text(text = stringResource(id = R.string.save_changes))
                }
                Spacer(modifier = Modifier.width(2.dp))
                Button(onClick = onSignOut) {
                    Text(text = stringResource(id = R.string.logout))
                }
            }
        }
    }
}
