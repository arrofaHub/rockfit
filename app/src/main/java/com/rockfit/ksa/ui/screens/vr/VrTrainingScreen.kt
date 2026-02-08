package com.rockfit.ksa.ui.screens.vr

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rockfit.ksa.R
import com.rockfit.ksa.ui.components.SectionHeader

@Composable
fun VrTrainingScreen() {
    LazyColumn(
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SectionHeader(title = stringResource(id = R.string.vr_training_title))
        }
        item {
            Card {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = stringResource(id = R.string.vr_training_hint), style = MaterialTheme.typography.bodyMedium)
                    Text(text = stringResource(id = R.string.vr_training_status), style = MaterialTheme.typography.labelLarge)
                }
            }
        }
        item {
            SectionHeader(title = stringResource(id = R.string.ar_modules_title))
        }
        item {
            Card {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = stringResource(id = R.string.ar_module_one), style = MaterialTheme.typography.bodyMedium)
                    Text(text = stringResource(id = R.string.ar_module_two), style = MaterialTheme.typography.bodyMedium)
                    Text(text = stringResource(id = R.string.ar_module_three), style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
        item {
            SectionHeader(title = stringResource(id = R.string.vr_device_title))
        }
        item {
            Card {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = stringResource(id = R.string.vr_device_hint), style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
