package dev.ricknout.composesensors.demo.ui.relativehumidity

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import dev.ricknout.composesensors.demo.model.Demo
import dev.ricknout.composesensors.demo.ui.Demo
import dev.ricknout.composesensors.demo.ui.NotAvailableDemo
import dev.ricknout.composesensors.relativehumidity.isRelativeHumiditySensorAvailable
import dev.ricknout.composesensors.relativehumidity.rememberRelativeHumiditySensorValueAsState

@Composable
fun RelativeHumidityDemo() {
    if (isRelativeHumiditySensorAvailable()) {
        val sensorValue by rememberRelativeHumiditySensorValueAsState()
        val value = sensorValue.value
        Demo(
            demo = Demo.RELATIVE_HUMIDITY,
            value = "$value %",
        ) {
            // TODO: Add demo
        }
    } else {
        NotAvailableDemo(demo = Demo.RELATIVE_HUMIDITY)
    }
}
