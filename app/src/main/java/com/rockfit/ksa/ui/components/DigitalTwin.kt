package com.rockfit.ksa.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rockfit.ksa.R
import com.rockfit.ksa.model.LifestyleProfile
import com.rockfit.ksa.model.MuscleGroup
import com.rockfit.ksa.model.TwinForecastDay
import com.rockfit.ksa.model.TwinZone
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

@Composable
fun DigitalTwinExperience(
    zones: List<TwinZone>,
    lifestyle: LifestyleProfile?,
    modifier: Modifier = Modifier
) {
    val allGroups = MuscleGroup.values().toList()
    var activeGroups by remember { mutableStateOf(allGroups.toSet()) }
    var selectedZone by remember { mutableStateOf<TwinZone?>(zones.firstOrNull()) }
    val forecast = remember(zones, lifestyle) { generateForecast(lifestyle) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TwinHeader()
        TwinFilters(
            groups = allGroups,
            active = activeGroups,
            onToggle = { group ->
                activeGroups = if (activeGroups.contains(group)) {
                    activeGroups - group
                } else {
                    activeGroups + group
                }
                if (selectedZone?.group !in activeGroups) {
                    selectedZone = zones.firstOrNull { it.group in activeGroups }
                }
            }
        )
        TwinSilhouette(
            zones = zones.filter { it.group in activeGroups },
            selected = selectedZone,
            onSelect = { selectedZone = it }
        )
        SuggestionPanel(zone = selectedZone, lifestyle = lifestyle)
        ForecastRow(days = forecast)
    }
}

@Composable
private fun TwinHeader() {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        listOf(Color(0xFF0E1B3F), Color(0xFF243E8F))
                    )
                )
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.ai_digital_twin),
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
            Text(
                text = stringResource(id = R.string.ai_digital_twin_hint),
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xDFFFFFFF)
            )
        }
    }
}

@Composable
private fun TwinFilters(
    groups: List<MuscleGroup>,
    active: Set<MuscleGroup>,
    onToggle: (MuscleGroup) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(groups) { group ->
            val selected = active.contains(group)
                            FilterChip(
                                selected = selected,
                                onClick = { onToggle(group) },
                                label = { Text(text = stringResource(id = groupLabelRes(group))) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(0xFF1E3A8A),
                                    selectedLabelColor = Color.White
                                )
                            )
        }
    }
}

@Composable
private fun TwinSilhouette(
    zones: List<TwinZone>,
    selected: TwinZone?,
    onSelect: (TwinZone) -> Unit
) {
    var rotation by remember { mutableFloatStateOf(0f) }
    val pulse by rememberInfiniteTransition().animateFloat(
        initialValue = 0.15f,
        targetValue = 0.45f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = LinearEasing)
        )
    )

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .background(Color(0xFFF7F8FD), RoundedCornerShape(24.dp))
                .pointerInput(Unit) {
                    detectDragGestures { _, dragAmount ->
                        rotation = (rotation + dragAmount.x / 4f).coerceIn(-70f, 70f)
                    }
                }
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(220.dp, 280.dp)) {
                val centerX = size.width / 2f
                val headRadius = size.width * 0.12f
                val shoulderY = size.height * 0.22f
                val torsoHeight = size.height * 0.46f
                val hipY = shoulderY + torsoHeight

                val rotationScale = cos(Math.toRadians(rotation.toDouble())).toFloat()
                val xScale = max(0.65f, rotationScale)

                fun rotX(x: Float): Float = centerX + (x - centerX) * xScale

                drawCircle(
                    color = Color(0xFFCBD5F6),
                    radius = headRadius,
                    center = Offset(rotX(centerX), headRadius + 8f)
                )
                drawRoundRect(
                    color = Color(0xFFB8C6F3),
                    topLeft = Offset(rotX(centerX - size.width * 0.16f), shoulderY),
                    size = androidx.compose.ui.geometry.Size(size.width * 0.32f * xScale, torsoHeight),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(28f, 28f)
                )
                drawLine(
                    color = Color(0xFFB8C6F3),
                    start = Offset(rotX(centerX - size.width * 0.22f), shoulderY + 14f),
                    end = Offset(rotX(centerX - size.width * 0.38f), hipY - 8f),
                    strokeWidth = 12f
                )
                drawLine(
                    color = Color(0xFFB8C6F3),
                    start = Offset(rotX(centerX + size.width * 0.22f), shoulderY + 14f),
                    end = Offset(rotX(centerX + size.width * 0.38f), hipY - 8f),
                    strokeWidth = 12f
                )
                drawLine(
                    color = Color(0xFFB8C6F3),
                    start = Offset(rotX(centerX - size.width * 0.10f), hipY),
                    end = Offset(rotX(centerX - size.width * 0.20f), size.height * 0.92f),
                    strokeWidth = 14f
                )
                drawLine(
                    color = Color(0xFFB8C6F3),
                    start = Offset(rotX(centerX + size.width * 0.10f), hipY),
                    end = Offset(rotX(centerX + size.width * 0.20f), size.height * 0.92f),
                    strokeWidth = 14f
                )

                zones.forEach { zone ->
                    val zoneCenter = Offset(
                        rotX(centerX + zone.offsetXRatio * size.width),
                        zone.offsetYRatio * size.height
                    )
                    val zoneRadius = size.width * zone.radiusRatio
                    val heatAlpha = if (zone == selected) 0.55f else pulse
                    drawCircle(
                        color = zone.color.copy(alpha = heatAlpha),
                        radius = zoneRadius,
                        center = zoneCenter
                    )
                    drawCircle(
                        color = Color.White.copy(alpha = if (zone == selected) 0.7f else 0.3f),
                        radius = zoneRadius + 10f,
                        center = zoneCenter,
                        style = Stroke(width = 2f)
                    )
                }
            }

            ZoneTapLayer(zones = zones, onSelect = onSelect)
        }
    }
}

@Composable
private fun ZoneTapLayer(zones: List<TwinZone>, onSelect: (TwinZone) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
            .pointerInput(zones) {
                detectTapGestures { offset ->
                    val nearest = findNearestZone(
                        offset,
                        Size(size.width.toFloat(), size.height.toFloat()),
                        zones
                    )
                    if (nearest != null) onSelect(nearest)
                }
            }
    )
}

private fun findNearestZone(offset: Offset, size: androidx.compose.ui.geometry.Size, zones: List<TwinZone>): TwinZone? {
    if (zones.isEmpty()) return null
    val centerX = size.width / 2f
    var bestZone: TwinZone? = null
    var bestDistance = Float.MAX_VALUE
    zones.forEach { zone ->
        val x = centerX + zone.offsetXRatio * size.width
        val y = zone.offsetYRatio * size.height
        val dx = offset.x - x
        val dy = offset.y - y
        val dist = sqrt(dx * dx + dy * dy)
        if (dist < bestDistance) {
            bestDistance = dist
            bestZone = zone
        }
    }
    val maxRadius = size.width * 0.18f
    return if (bestDistance <= maxRadius) bestZone else null
}

@Composable
private fun SuggestionPanel(zone: TwinZone?, lifestyle: LifestyleProfile?) {
    if (zone == null) return
    val suggestions = buildSuggestions(zone, lifestyle)
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = stringResource(id = R.string.target_label, zone.label),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = suggestions.first(),
                style = MaterialTheme.typography.bodyMedium
            )
            suggestions.drop(1).forEach { item ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(Color(0xFF1E3A8A), shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = item, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Composable
private fun buildSuggestions(zone: TwinZone, lifestyle: LifestyleProfile?): List<String> {
    val zoneLabel = stringResource(id = groupLabelRes(zone.group))
    if (lifestyle == null) {
        return listOf(
            stringResource(id = R.string.suggest_prehab, zoneLabel),
            stringResource(id = R.string.suggest_reduce_hi),
            stringResource(id = R.string.suggest_sleep_hydration)
        )
    }
    val sleepTag = if (lifestyle.avgSleepHours < 7.0) {
        stringResource(id = R.string.suggest_sleep_push)
    } else {
        stringResource(id = R.string.suggest_sleep_maintain)
    }
    val hydrationTag = if (lifestyle.hydrationLiters < 2.5) {
        stringResource(id = R.string.suggest_hydration_up)
    } else {
        stringResource(id = R.string.suggest_hydration_keep)
    }
    val stressTag = when (lifestyle.stressLevel) {
        com.rockfit.ksa.model.StressLevel.High -> stringResource(id = R.string.suggest_stress_high)
        com.rockfit.ksa.model.StressLevel.Moderate -> stringResource(id = R.string.suggest_stress_mid)
        com.rockfit.ksa.model.StressLevel.Low -> stringResource(id = R.string.suggest_stress_low)
    }
    return when (zone.group) {
        MuscleGroup.Hamstrings -> listOf(
            stringResource(id = R.string.suggest_hamstrings_one),
            sleepTag,
            hydrationTag
        )
        MuscleGroup.LowerBack -> listOf(
            stringResource(id = R.string.suggest_lowerback_one),
            stressTag,
            stringResource(id = R.string.suggest_lowerback_two)
        )
        MuscleGroup.Shoulders -> listOf(
            stringResource(id = R.string.suggest_shoulders_one),
            stringResource(id = R.string.suggest_shoulders_two),
            hydrationTag
        )
        MuscleGroup.Quads -> listOf(
            stringResource(id = R.string.suggest_quads_one),
            stringResource(id = R.string.suggest_quads_two),
            sleepTag
        )
        MuscleGroup.Calves -> listOf(
            stringResource(id = R.string.suggest_calves_one),
            stringResource(id = R.string.suggest_calves_two),
            hydrationTag
        )
    }
}

@Composable
private fun ForecastRow(days: List<TwinForecastDay>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(days) { day ->
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF6F7FB))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(text = day.label, style = MaterialTheme.typography.labelLarge)
                    Text(
                        text = "${day.readiness}%",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E3A8A)
                    )
                    Text(
                        text = day.focus,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(day.riskColor, shape = RoundedCornerShape(6.dp))
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = day.riskLabel,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}

private fun generateForecast(lifestyle: LifestyleProfile?): List<TwinForecastDay> {
    val base = if (lifestyle == null) 80 else when (lifestyle.stressLevel) {
        com.rockfit.ksa.model.StressLevel.High -> 74
        com.rockfit.ksa.model.StressLevel.Moderate -> 78
        com.rockfit.ksa.model.StressLevel.Low -> 83
    }
    val sleepAdj = if (lifestyle != null && lifestyle.avgSleepHours < 7.0) -4 else 2
    val hydAdj = if (lifestyle != null && lifestyle.hydrationLiters < 2.4) -3 else 1
    return (1..30).map { day ->
        val drift = ((day % 6) - 3) * 2
        val readiness = max(60, min(95, base + sleepAdj + hydAdj + drift))
        val riskLabel = when {
            readiness >= 85 -> "Low risk"
            readiness >= 75 -> "Moderate"
            else -> "Elevated"
        }
        val riskColor = when {
            readiness >= 85 -> Color(0xFF1AAE6F)
            readiness >= 75 -> Color(0xFFF0A13E)
            else -> Color(0xFFD85B5B)
        }
        TwinForecastDay(
            label = "Day $day",
            readiness = readiness,
            riskLabel = riskLabel,
            riskColor = riskColor,
            focus = if (day % 5 == 0) "Recovery focus" else "Performance load"
        )
    }
}

private fun groupLabelRes(group: MuscleGroup): Int {
    return when (group) {
        MuscleGroup.Hamstrings -> R.string.muscle_hamstrings
        MuscleGroup.LowerBack -> R.string.muscle_lower_back
        MuscleGroup.Shoulders -> R.string.muscle_shoulders
        MuscleGroup.Quads -> R.string.muscle_quads
        MuscleGroup.Calves -> R.string.muscle_calves
    }
}
