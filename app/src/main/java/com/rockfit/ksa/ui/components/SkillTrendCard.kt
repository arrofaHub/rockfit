package com.rockfit.ksa.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rockfit.ksa.model.SkillTrend

@Composable
fun SkillTrendCard(trend: SkillTrend) {
    Card(shape = RoundedCornerShape(18.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(text = trend.label, style = MaterialTheme.typography.titleMedium)
                Text(text = "${trend.currentScore}", style = MaterialTheme.typography.titleMedium, color = Color(0xFF1E3A8A))
            }
            TrendSparkline(points = trend.weeklyTrend)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                TrendStat(label = "Month", value = trend.lastMonth)
                TrendStat(label = "Year", value = trend.lastYear)
            }
        }
    }
}

@Composable
private fun TrendSparkline(points: List<Int>) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        if (points.size < 2) return@Canvas
        val max = points.maxOrNull() ?: 1
        val min = points.minOrNull() ?: 0
        val range = (max - min).coerceAtLeast(1)
        val stepX = size.width / (points.size - 1)
        points.forEachIndexed { index, value ->
            if (index == 0) return@forEachIndexed
            val x1 = (index - 1) * stepX
            val x2 = index * stepX
            val y1 = size.height - ((points[index - 1] - min).toFloat() / range) * size.height
            val y2 = size.height - ((value - min).toFloat() / range) * size.height
            drawLine(Color(0xFF4F8EF7), Offset(x1, y1), Offset(x2, y2), 6f)
        }
    }
}

@Composable
private fun TrendStat(label: String, value: Int) {
    Row {
        Text(text = "$label: ", style = MaterialTheme.typography.labelSmall)
        Text(text = "$value", style = MaterialTheme.typography.labelMedium)
        Spacer(modifier = Modifier.width(6.dp))
    }
}
