package dev.ricknout.composesensors.light

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
 * A composable function to check if [Sensor.TYPE_LIGHT] is available.
 *
 * @return Whether or not the sensor is available, as a boolean.
 */
@Composable
@ReadOnlyComposable
fun isLightSensorAvailable(): Boolean = isSensorAvailable(type = Sensor.TYPE_LIGHT)

/**
 * A composable function to get the default [Sensor.TYPE_LIGHT], if available.
 *
 * @return The default sensor.
 *
 * @throws RuntimeException if the sensor is unavailable. Use the [isLightSensorAvailable] function.
 */
@Composable
@ReadOnlyComposable
fun getLightSensor(): Sensor = getSensor(type = Sensor.TYPE_LIGHT)

/**
 * A composable function that uses [remember] to return the value of the default [Sensor.TYPE_LIGHT] as [State].
 *
 * @param initialValue Initial value in lx, default is 0f.
 * @param samplingPeriodUs Sampling period in μs, default is [SensorManager.SENSOR_DELAY_NORMAL].
 *
 * @return The light sensor value in lx, as [State].
 *
 * @throws RuntimeException if the sensor is unavailable. Use the [isLightSensorAvailable] function.
 */
@Composable
fun rememberLightSensorValueAsState(
    initialValue: SensorValue<Float> = SensorValue.EMPTY_1D,
    samplingPeriodUs: Int = SensorManager.SENSOR_DELAY_NORMAL,
): State<SensorValue<Float>> = rememberSensorValueAsState(
    type = Sensor.TYPE_LIGHT,
    samplingPeriodUs = samplingPeriodUs,
    transformSensorEvent = { event -> event?.to1DSensorValue() ?: initialValue },
)
