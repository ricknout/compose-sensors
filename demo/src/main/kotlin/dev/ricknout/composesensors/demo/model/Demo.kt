package dev.ricknout.composesensors.demo.model

import android.view.View
import dev.ricknout.composesensors.demo.R

enum class Demo(
    val title: String,
    val iconResourceId: Int,
) {
    NONE("", View.NO_ID),
    ACCELEROMETER("Accelerometer", R.drawable.ic_accelerometer_24dp),
    GRAVITY("Gravity", R.drawable.ic_gravity_24dp),
    GYROSCOPE("Gyroscope", R.drawable.ic_gyroscope_24dp),
    LIGHT("Light", R.drawable.ic_light_24dp),
    MAGNETIC_FIELD("Magnetic Field", R.drawable.ic_magnetic_field_24dp),
    PRESSURE("Pressure", R.drawable.ic_pressure_24dp),
    PROXIMITY("Proximity", R.drawable.ic_proximity_24dp),
}
