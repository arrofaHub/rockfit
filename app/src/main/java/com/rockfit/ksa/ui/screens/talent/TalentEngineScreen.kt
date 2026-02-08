package com.rockfit.ksa.ui.screens.talent

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
import com.rockfit.ksa.R
import com.rockfit.ksa.model.SampleData
import com.rockfit.ksa.ui.components.SectionHeader

@Composable
fun TalentEngineScreen() {
    LazyColumn(
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SectionHeader(title = stringResource(id = R.string.talent_engine_title))
        }
        item {
            Card {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = stringResource(id = R.string.talent_engine_hint), style = MaterialTheme.typography.bodyMedium)
                    Text(text = stringResource(id = R.string.talent_engine_scope), style = MaterialTheme.typography.labelLarge)
                }
            }
        }
        item {
            SectionHeader(title = stringResource(id = R.string.talent_indicators))
        }
        items(SampleData.talentIndicators) { indicator ->
            Card {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = stringResource(id = indicator.labelRes), style = MaterialTheme.typography.titleMedium)
                    Text(text = "${indicator.percentile}%", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                    Text(text = stringResource(id = indicator.noteRes), style = MaterialTheme.typography.bodySmall)
                }
            }
        }
        item {
            SectionHeader(title = stringResource(id = R.string.talent_pipeline))
        }
        item {
            Card {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = stringResource(id = R.string.talent_pipeline_hint),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
