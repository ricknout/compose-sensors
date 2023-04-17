package dev.ricknout.composesensors.demo.ui.light

import android.hardware.SensorManager
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import dev.ricknout.composesensors.demo.model.Demo
import dev.ricknout.composesensors.demo.ui.Demo
import dev.ricknout.composesensors.demo.ui.NotAvailableDemo
import dev.ricknout.composesensors.light.isLightSensorAvailable
import dev.ricknout.composesensors.light.rememberLightSensorValueAsState

@Composable
fun LightDemo() {
    if (isLightSensorAvailable()) {
        val sensorValue by rememberLightSensorValueAsState(samplingPeriodUs = SensorManager.SENSOR_DELAY_FASTEST)
        val value = sensorValue.value
        val ratio = animateFloatAsState(targetValue = (value / SensorManager.LIGHT_OVERCAST).coerceAtMost(1f))
        Demo(
            demo = Demo.LIGHT,
            value = "$value lx",
        ) {
            val contentColor = LocalContentColor.current
            val radius = with(LocalDensity.current) { 10.dp.toPx() }
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = contentColor,
                    radius = radius,
                    center = center,
                )
                val gradientRadius = radius + (size.minDimension - radius) * ratio.value
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(contentColor, Color.Transparent),
                        radius = gradientRadius,
                        center = center,
                    ),
                    radius = gradientRadius,
                    center = center,
                )
            }
        }
    } else {
        NotAvailableDemo(demo = Demo.LIGHT)
    }
}
