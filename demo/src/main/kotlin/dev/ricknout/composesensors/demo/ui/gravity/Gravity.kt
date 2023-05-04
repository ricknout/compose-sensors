package dev.ricknout.composesensors.demo.ui.gravity

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import dev.ricknout.composesensors.demo.model.Demo
import dev.ricknout.composesensors.demo.ui.Demo
import dev.ricknout.composesensors.demo.ui.NotAvailableDemo
import dev.ricknout.composesensors.gravity.isGravitySensorAvailable
import dev.ricknout.composesensors.gravity.rememberGravitySensorValueAsState

@Composable
fun GravityDemo() {
    if (isGravitySensorAvailable()) {
        val sensorValue by rememberGravitySensorValueAsState()
        val (x, y, z) = sensorValue.value
        Demo(
            demo = Demo.GRAVITY,
            value = "X: $x m/s^2\nY: $y m/s^2\nZ: $z m/s^2",
        ) {
            // TODO: Add demo
        }
    } else {
        NotAvailableDemo(demo = Demo.GRAVITY)
    }
}
