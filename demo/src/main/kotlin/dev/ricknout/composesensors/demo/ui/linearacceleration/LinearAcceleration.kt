package dev.ricknout.composesensors.demo.ui.linearacceleration

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import dev.ricknout.composesensors.demo.model.Demo
import dev.ricknout.composesensors.demo.ui.Demo
import dev.ricknout.composesensors.demo.ui.NotAvailableDemo
import dev.ricknout.composesensors.linearacceleration.isLinearAccelerationSensorAvailable
import dev.ricknout.composesensors.linearacceleration.rememberLinearAccelerationSensorValueAsState

@Composable
fun LinearAccelerationDemo() {
    if (isLinearAccelerationSensorAvailable()) {
        val sensorValue by rememberLinearAccelerationSensorValueAsState()
        val (x, y, z) = sensorValue.value
        Demo(
            demo = Demo.LINEAR_ACCELERATION,
            value = "X: $x m/s^2\nY: $y m/s^2\nZ: $z m/s^2",
        ) {
            // TODO: Add demo
        }
    } else {
        NotAvailableDemo(demo = Demo.LINEAR_ACCELERATION)
    }
}
