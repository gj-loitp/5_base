package com.views.compass

import android.hardware.Sensor
import android.hardware.SensorEvent

interface CompassListener {
    fun onSensorChanged(event: SensorEvent)
    fun onAccuracyChanged(sensor: Sensor, accuracy: Int)
}
