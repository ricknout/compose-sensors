package dev.ricknout.composesensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.SystemClock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * A composable function to get the system [SensorManager], if available.
 *
 * @return The system [SensorManager], or null if unavailable.
 */
@Composable
@ReadOnlyComposable
fun getSensorManager(): SensorManager? {
    val context = LocalContext.current
    return ContextCompat.getSystemService(context, SensorManager::class.java)
}

/**
 * A composable function to check if the default sensor for a given [type] is available.
 *
 * @param type The sensor type as per constants in [Sensor].
 *
 * @return Whether or not the sensor for the given [type] is available, as a boolean.
 */
@Composable
@ReadOnlyComposable
fun isSensorAvailable(type: Int): Boolean = getSensorInternal(type = type) != null

/**
 * A composable function to get the default sensor for a given [type], if available.
 *
 * @param type The sensor type as per constants in [Sensor].
 *
 * @return The default sensor.
 *
 * @throws RuntimeException if the sensor is unavailable. Use the [isSensorAvailable] function.
 */
@Composable
@ReadOnlyComposable
fun getSensor(type: Int): Sensor = getSensorInternal(type = type)
    ?: throw RuntimeException("Sensor of type $type is not available, use one of the isSensorAvailable functions")

/**
 * Internal composable function to get the default sensor for a given [type], if available.
 *
 * @param type The sensor type as per constants in [Sensor].
 *
 * @return The default sensor, or null if unavailable.
 *
 * @throws RuntimeException if [SensorManager] is null.
 */
@Composable
@ReadOnlyComposable
internal fun getSensorInternal(type: Int): Sensor? {
    val sensorManager = getSensorManager() ?: throw RuntimeException("SensorManager is null")
    return sensorManager.getDefaultSensor(type)
}

/**
 * A generic, lifecycle-aware composable function that uses [remember] to return the value of a sensor as [State].
 *
 * @param type The sensor type as per constants in [Sensor].
 * @param samplingPeriodUs Sampling period in μs, default is [SensorManager.SENSOR_DELAY_NORMAL].
 * @param transformSensorEvent Function to transform a [SensorEvent] into a value of type [T].
 *
 * @return The sensor value of type [T], as [State].
 *
 * @throws RuntimeException if the sensor is unavailable. Use the [isSensorAvailable] function.
 */
@Composable
fun <T> rememberSensorValueAsState(
    type: Int,
    samplingPeriodUs: Int = SensorManager.SENSOR_DELAY_NORMAL,
    transformSensorEvent: (event: SensorEvent?) -> T,
): State<T> {
    val context = LocalContext.current
    val sensorEventCallbackFlow = remember {
        sensorEventCallbackFlow(
            context = context,
            type = type,
            samplingPeriodUs = samplingPeriodUs,
        )
    }
    val sensorEvent by sensorEventCallbackFlow.collectAsStateWithLifecycle(
        initialValue = ComposableSensorEvent(),
        minActiveState = Lifecycle.State.RESUMED,
    )
    return remember { derivedStateOf { transformSensorEvent(sensorEvent.event) } }
}

/**
 * Generic class used to model raw [SensorEvent] values.
 *
 * @param value The value from [SensorEvent.values] of type [T].
 * @param timestamp The timestamp from [SensorEvent.timestamp].
 * @param accuracy The accuracy from [SensorEvent.accuracy].
 */
class SensorValue<T>(
    val value: T,
    val timestamp: Long = SystemClock.elapsedRealtimeNanos(),
    val accuracy: Int = SensorManager.SENSOR_STATUS_NO_CONTACT,
) {
    companion object {
        val EMPTY_1D = SensorValue(value = 0f)
        val EMPTY_3D = SensorValue(value = Triple(0f, 0f, 0f))
    }
}

/**
 * Extension function to convert a [SensorEvent] to a 1D [SensorValue].
 */
fun SensorEvent.to1DSensorValue(): SensorValue<Float> = SensorValue(
    value = values[0],
    timestamp = timestamp,
    accuracy = accuracy,
)

/**
 * Extension function to convert a [SensorEvent] to a 3D [SensorValue].
 */
fun SensorEvent.to3DSensorValue(): SensorValue<Triple<Float, Float, Float>> = SensorValue(
    value = Triple(values[0], values[1], values[2]),
    timestamp = timestamp,
    accuracy = accuracy,
)

/**
 * Internal function used to wrap [SensorManager] and [Sensor] and provide [SensorEvent]s as a [Flow].
 *
 * @param context The current [Context].
 * @param type The sensor type as per constants in [Sensor].
 * @param samplingPeriodUs Sampling period in μs.
 *
 * @return [SensorEvent]s as a [Flow].
 */
internal fun sensorEventCallbackFlow(
    context: Context,
    type: Int,
    samplingPeriodUs: Int,
): Flow<ComposableSensorEvent> = callbackFlow {
    val sensorManager = ContextCompat.getSystemService(context, SensorManager::class.java)
        ?: throw RuntimeException("SensorManager is null")
    val sensor = sensorManager.getDefaultSensor(type)
        ?: throw RuntimeException("Sensor of type $type is not available, use one of the isSensorAvailable functions")
    val listener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val composableEvent = ComposableSensorEvent(event = event)
            trySend(composableEvent)
        }
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
            // TODO: Handle sensor accuracy changes?
        }
    }
    val successful = sensorManager.registerListener(listener, sensor, samplingPeriodUs)
    if (!successful) throw RuntimeException("Failed to register listener for sensor ${sensor.name}")
    awaitClose { sensorManager.unregisterListener(listener) }
}

/**
 * Internal class used to wrap [SensorEvent] and trigger recompositions via [SensorEvent.timestamp].
 *
 * @param event The [SensorEvent].
 * @param timestamp The [SensorEvent.timestamp].
 */
internal data class ComposableSensorEvent(
    val event: SensorEvent? = null,
    val timestamp: Long = event?.timestamp ?: SystemClock.elapsedRealtimeNanos()
)
