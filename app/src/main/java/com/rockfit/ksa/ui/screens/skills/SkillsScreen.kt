package com.rockfit.ksa.ui.screens.skills

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rockfit.ksa.model.SampleData
import com.rockfit.ksa.R
import com.rockfit.ksa.ui.components.SkillTrendCard
import com.rockfit.ksa.ui.components.MediaCardData
import com.rockfit.ksa.ui.components.MediaCarousel
import com.rockfit.ksa.ui.components.SectionHeader

@Composable
fun SkillsScreen() {
    LazyColumn(
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SectionHeader(title = stringResource(id = R.string.skill_profile))
        }

        items(SampleData.skills) { skill ->
            Card {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = skill.label, style = MaterialTheme.typography.titleMedium)
                    LinearProgressIndicator(progress = skill.score / 100f)
                    Text(text = "${skill.score}/100", style = MaterialTheme.typography.labelLarge)
                }
            }
        }

        item {
            SectionHeader(title = stringResource(id = R.string.progress_analytics))
        }

        items(SampleData.skillTrends) { trend ->
            SkillTrendCard(trend = trend)
        }

        item {
            SectionHeader(title = stringResource(id = R.string.technique_reels))
        }

        item {
            MediaCarousel(
                items = listOf(
                    MediaCardData(
                        title = stringResource(id = R.string.reel_first_touch_title),
                        subtitle = stringResource(id = R.string.reel_first_touch_subtitle),
                        icon = Icons.Filled.SportsSoccer,
                        colors = listOf(Color(0xFF2C5FEE), Color(0xFF7CA7FF))
                    ),
                    MediaCardData(
                        title = stringResource(id = R.string.reel_acceleration_title),
                        subtitle = stringResource(id = R.string.reel_acceleration_subtitle),
                        icon = Icons.Filled.Bolt,
                        colors = listOf(Color(0xFF5F2EEA), Color(0xFF9F8CFF))
                    ),
                    MediaCardData(
                        title = stringResource(id = R.string.reel_video_title),
                        subtitle = stringResource(id = R.string.reel_video_subtitle),
                        icon = Icons.Filled.Movie,
                        colors = listOf(Color(0xFF1B9A8A), Color(0xFF63D9C8))
                    )
                )
            )
        }

        item {
            SectionHeader(title = stringResource(id = R.string.video_analysis))
        }
        item {
            Card {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(id = R.string.video_analysis_hint),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
