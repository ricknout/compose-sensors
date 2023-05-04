package dev.ricknout.composesensors.gravity

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
import dev.ricknout.composesensors.to3DSensorValue

/**
 * A composable function to check if [Sensor.TYPE_GRAVITY] is available.
 *
 * @return Whether or not the sensor is available, as a boolean.
 */
@Composable
@ReadOnlyComposable
fun isGravitySensorAvailable(): Boolean = isSensorAvailable(type = Sensor.TYPE_GRAVITY)

/**
 * A composable function to get the default [Sensor.TYPE_GRAVITY], if available.
 *
 * @return The default sensor.
 *
 * @throws RuntimeException if the sensor is unavailable. Use the [isGravitySensorAvailable] function.
 */
@Composable
@ReadOnlyComposable
fun getGravitySensor(): Sensor = getSensor(type = Sensor.TYPE_GRAVITY)

/**
 * A composable function that uses [remember] to return the value of the default [Sensor.TYPE_GRAVITY] as [State].
 *
 * @param initialValue Initial X, Y and Z axis values in m/s^2, default is 0f, 0f, 0f.
 * @param samplingPeriodUs Sampling period in μs, default is [SensorManager.SENSOR_DELAY_NORMAL].
 *
 * @return The gravity sensor X, Y and Z axis values in m/s^2, as [State].
 *
 * @throws RuntimeException if the sensor is unavailable. Use the [isGravitySensorAvailable] function.
 */
@Composable
fun rememberGravitySensorValueAsState(
    initialValue: SensorValue<Triple<Float, Float, Float>> = SensorValue.EMPTY_3D,
    samplingPeriodUs: Int = SensorManager.SENSOR_DELAY_NORMAL,
): State<SensorValue<Triple<Float, Float, Float>>> = rememberSensorValueAsState(
    type = Sensor.TYPE_GRAVITY,
    samplingPeriodUs = samplingPeriodUs,
    transformSensorEvent = { event -> event?.to3DSensorValue() ?: initialValue },
)
