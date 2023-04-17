package dev.ricknout.composesensors.demo.ui.pressure

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import dev.ricknout.composesensors.demo.model.Demo
import dev.ricknout.composesensors.demo.ui.Demo
import dev.ricknout.composesensors.demo.ui.NotAvailableDemo
import dev.ricknout.composesensors.pressure.isPressureSensorAvailable
import dev.ricknout.composesensors.pressure.rememberPressureSensorValueAsState

@Composable
fun PressureDemo() {
    if (isPressureSensorAvailable()) {
        val sensorValue by rememberPressureSensorValueAsState()
        val value = sensorValue.value
        Demo(
            demo = Demo.PRESSURE,
            value = "$value hPa",
        ) {
            // TODO: Add demo
        }
    } else {
        NotAvailableDemo(demo = Demo.PRESSURE)
    }
}
