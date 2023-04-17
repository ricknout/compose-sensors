package dev.ricknout.composesensors.demo.ui.proximity

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import dev.ricknout.composesensors.SensorValue
import dev.ricknout.composesensors.demo.model.Demo
import dev.ricknout.composesensors.demo.ui.Demo
import dev.ricknout.composesensors.demo.ui.NotAvailableDemo
import dev.ricknout.composesensors.proximity.getProximitySensor
import dev.ricknout.composesensors.proximity.isProximitySensorAvailable
import dev.ricknout.composesensors.proximity.rememberProximitySensorValueAsState

@Composable
fun ProximityDemo() {
    if (isProximitySensorAvailable()) {
        val sensor = getProximitySensor()
        val sensorValue by rememberProximitySensorValueAsState(initialValue = SensorValue(value = sensor.maximumRange))
        val value = sensorValue.value
        Demo(
            demo = Demo.PROXIMITY,
            value = "$value cm",
        ) {
            val near = value == 0f
            val radiusDp by animateDpAsState(targetValue = if (near) 100.dp else 10.dp)
            val radius = with(LocalDensity.current) { radiusDp.toPx() }
            val contentColor = LocalContentColor.current
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = contentColor,
                    radius = radius,
                    center = center,
                )
            }
        }
    } else {
        NotAvailableDemo(demo = Demo.PROXIMITY)
    }
}
