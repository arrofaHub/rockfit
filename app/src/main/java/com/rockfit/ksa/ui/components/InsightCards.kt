package com.rockfit.ksa.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.rockfit.ksa.model.AiInsight
import com.rockfit.ksa.model.InsightCategory

@Composable
fun InsightCarousel(
    insights: List<AiInsight>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(insights) { insight ->
            InsightCard(insight = insight)
        }
    }
}

@Composable
fun InsightCard(insight: AiInsight) {
    val palette = insightPalette(insight.category)
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .width(280.dp)
                .background(Brush.linearGradient(palette.colors))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                BoxIcon(icon = palette.icon)
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = stringResource(id = insight.titleRes),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                    Text(
                        text = "${insight.confidence}% confidence",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xE6FFFFFF)
                    )
                }
            }
            Text(
                text = stringResource(id = insight.summaryRes),
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xE6FFFFFF)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0x22FFFFFF), shape = RoundedCornerShape(14.dp))
                    .padding(10.dp)
            ) {
                Text(
                    text = stringResource(id = insight.actionRes),
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun BoxIcon(icon: ImageVector) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(Color(0x33FFFFFF), shape = RoundedCornerShape(14.dp)),
        contentAlignment = Alignment.Center
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = Color.White)
    }
}

private data class InsightPalette(
    val colors: List<Color>,
    val icon: ImageVector
)

private fun insightPalette(category: InsightCategory): InsightPalette {
    return when (category) {
        InsightCategory.Training -> InsightPalette(
            colors = listOf(Color(0xFF1E3A8A), Color(0xFF5B8CFF)),
            icon = Icons.Filled.Bolt
        )
        InsightCategory.Recovery -> InsightPalette(
            colors = listOf(Color(0xFF1B9A8A), Color(0xFF63D9C8)),
            icon = Icons.Filled.Favorite
        )
        InsightCategory.Nutrition -> InsightPalette(
            colors = listOf(Color(0xFF2A6B5F), Color(0xFF4CD7B6)),
            icon = Icons.Filled.LocalDrink
        )
        InsightCategory.InjuryRisk -> InsightPalette(
            colors = listOf(Color(0xFF8A2A2A), Color(0xFFF08B7B)),
            icon = Icons.Filled.Bolt
        )
        InsightCategory.Sleep -> InsightPalette(
            colors = listOf(Color(0xFF3A2D8A), Color(0xFF9A86FF)),
            icon = Icons.Filled.Bedtime
        )
    }
}
