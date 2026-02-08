package com.rockfit.ksa.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Shapes
import androidx.compose.foundation.shape.RoundedCornerShape

private val RockfitColorScheme = lightColorScheme(
    primary = RockfitBlue,
    secondary = RockfitTeal,
    tertiary = RockfitOrange,
    background = RockfitBackground,
    surface = RockfitSurface,
    onSurface = RockfitOnSurface,
    onPrimary = Color.White
)

private val RockfitShapes = Shapes(
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(18.dp),
    large = RoundedCornerShape(26.dp)
)

@Composable
fun RockfitTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = RockfitColorScheme,
        typography = RockfitTypography,
        shapes = RockfitShapes,
        content = content
    )
}

@Composable
fun RockfitBackground(content: @Composable () -> Unit) {
    val infinite = rememberInfiniteTransition()
    val orb1X = infinite.animateFloat(
        initialValue = -60f,
        targetValue = 40f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 16000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val orb1Y = infinite.animateFloat(
        initialValue = 40f,
        targetValue = 160f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 18000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val orb2X = infinite.animateFloat(
        initialValue = 140f,
        targetValue = 260f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 20000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val orb2Y = infinite.animateFloat(
        initialValue = 420f,
        targetValue = 260f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 22000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val orb3X = infinite.animateFloat(
        initialValue = 220f,
        targetValue = 80f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 24000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val orb3Y = infinite.animateFloat(
        initialValue = 720f,
        targetValue = 620f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 26000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val gradient = Brush.linearGradient(
        colors = listOf(Color(0xFFE7F0FF), Color(0xFFF8FAFF), Color(0xFFEFFAF6))
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = orb1X.value.dp, y = orb1Y.value.dp)
                    .size(220.dp)
                    .blur(40.dp)
                    .background(Color(0x334F8EF7), shape = CircleShape)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = orb2X.value.dp, y = orb2Y.value.dp)
                    .size(280.dp)
                    .blur(52.dp)
                    .background(Color(0x3327C6A4), shape = CircleShape)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = orb3X.value.dp, y = orb3Y.value.dp)
                    .size(240.dp)
                    .blur(46.dp)
                    .background(Color(0x33F8B26A), shape = CircleShape)
            )
        }
        content()
    }
}
