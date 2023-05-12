package dev.ricknout.composesensors.demo.ui.ambienttemperature

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import dev.ricknout.composesensors.ambienttemperature.isAmbientTemperatureSensorAvailable
import dev.ricknout.composesensors.ambienttemperature.rememberAmbientTemperatureSensorValueAsState
import dev.ricknout.composesensors.demo.model.Demo
import dev.ricknout.composesensors.demo.ui.Demo
import dev.ricknout.composesensors.demo.ui.NotAvailableDemo

@Composable
fun AmbientTemperatureDemo() {
    if (isAmbientTemperatureSensorAvailable()) {
        val sensorValue by rememberAmbientTemperatureSensorValueAsState()
        val value = sensorValue.value
        Demo(
            demo = Demo.AMBIENT_TEMPERATURE,
            value = "$value °C",
        ) {
            // TODO: Add demo
        }
    } else {
        NotAvailableDemo(demo = Demo.AMBIENT_TEMPERATURE)
    }
}
