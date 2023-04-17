package dev.ricknout.composesensors.demo.ui.magneticfield

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import dev.ricknout.composesensors.demo.model.Demo
import dev.ricknout.composesensors.demo.ui.Demo
import dev.ricknout.composesensors.demo.ui.NotAvailableDemo
import dev.ricknout.composesensors.magneticfield.isMagneticFieldSensorAvailable
import dev.ricknout.composesensors.magneticfield.rememberMagneticFieldSensorValueAsState

@Composable
fun MagneticFieldDemo() {
    if (isMagneticFieldSensorAvailable()) {
        val sensorValue by rememberMagneticFieldSensorValueAsState()
        val (x, y, z) = sensorValue.value
        Demo(
            demo = Demo.MAGNETIC_FIELD,
            value = "X: $x μT\nY: $y μT\nZ: $z μT",
        ) {
            // TODO: Add demo
        }
    } else {
        NotAvailableDemo(demo = Demo.MAGNETIC_FIELD)
    }
}
