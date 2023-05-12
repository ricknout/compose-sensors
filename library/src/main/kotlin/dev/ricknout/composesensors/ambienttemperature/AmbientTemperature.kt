package dev.ricknout.composesensors.ambienttemperature

import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import dev.ricknout.composesensors.SensorValue
import dev.ricknout.composesensors.getSensor
import dev.ricknout.composesensors.isSensorAvailable
import dev.ricknout.composesensors.rememberSensorValueAsState
import dev.ricknout.composesensors.to1DSensorValue

/**
 * A composable function to check if [Sensor.TYPE_AMBIENT_TEMPERATURE] is available.
 *
 * @return Whether or not the sensor is available, as a boolean.
 */
@Composable
@ReadOnlyComposable
fun isAmbientTemperatureSensorAvailable(): Boolean = isSensorAvailable(type = Sensor.TYPE_AMBIENT_TEMPERATURE)

/**
 * A composable function to get the default [Sensor.TYPE_AMBIENT_TEMPERATURE], if available.
 *
 * @return The default sensor.
 *
 * @throws RuntimeException if the sensor is unavailable. Use the [isAmbientTemperatureSensorAvailable] function.
 */
@Composable
@ReadOnlyComposable
fun getAmbientTemperatureSensor(): Sensor = getSensor(type = Sensor.TYPE_AMBIENT_TEMPERATURE)

/**
 * A composable function that uses [remember] to return the value of the default [Sensor.TYPE_AMBIENT_TEMPERATURE] as
 * [State].
 *
 * @param initialValue Initial value in degrees celsius, default is 0f.
 * @param samplingPeriodUs Sampling period in μs, default is [SensorManager.SENSOR_DELAY_NORMAL].
 *
 * @return The ambient temperature sensor value in degrees celsius, as [State].
 *
 * @throws RuntimeException if the sensor is unavailable. Use the [isAmbientTemperatureSensorAvailable] function.
 */
@Composable
fun rememberAmbientTemperatureSensorValueAsState(
    initialValue: SensorValue<Float> = SensorValue.EMPTY_1D,
    samplingPeriodUs: Int = SensorManager.SENSOR_DELAY_NORMAL,
): State<SensorValue<Float>> = rememberSensorValueAsState(
    type = Sensor.TYPE_AMBIENT_TEMPERATURE,
    samplingPeriodUs = samplingPeriodUs,
    transformSensorEvent = { event -> event?.to1DSensorValue() ?: initialValue },
)
