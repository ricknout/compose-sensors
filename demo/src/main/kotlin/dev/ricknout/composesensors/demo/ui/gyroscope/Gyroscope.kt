package dev.ricknout.composesensors.demo.ui.gyroscope

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import dev.ricknout.composesensors.demo.model.Demo
import dev.ricknout.composesensors.demo.ui.Demo
import dev.ricknout.composesensors.demo.ui.NotAvailableDemo
import dev.ricknout.composesensors.gyroscope.isGyroscopeSensorAvailable
import dev.ricknout.composesensors.gyroscope.rememberGyroscopeSensorValueAsState

@Composable
fun GyroscopeDemo() {
    if (isGyroscopeSensorAvailable()) {
        val sensorValue by rememberGyroscopeSensorValueAsState()
        val (x, y, z) = sensorValue.value
        Demo(
            demo = Demo.GYROSCOPE,
            value = "X: $x rad/s\nY: $y rad/s\nZ: $z rad/s",
        ) {
            // TODO: Add demo
        }
    } else {
        NotAvailableDemo(demo = Demo.GYROSCOPE)
    }
}
