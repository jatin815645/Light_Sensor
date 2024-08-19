package com.smartherd.lightsensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.IOException

class MainActivity : AppCompatActivity(), SensorEventListener {

    var sensor : Sensor? = null
    var sensorManager : SensorManager? = null
    lateinit var image : ImageView
    lateinit var background : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        image = findViewById(R.id.diaplayImage)
        background = findViewById(R.id.backgroungMain)
        image.visibility = View.VISIBLE

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)

    }

    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        try {

            if (event != null){
                Log.d("Changed", "onSensorChanged: ${event.values[0]}")
            }
            if (event!!.values[0] < 30){
                // if light is dim
                image.visibility = View.INVISIBLE
                background.setBackgroundColor(resources.getColor(R.color.black))
            }
            else{
                // if light is good
                image.visibility = View.VISIBLE
                background.setBackgroundColor(resources.getColor(R.color.yellow))
            }

        }catch (e : IOException){
            Log.d("Sensor", "onSensorChanged: ${e.message}")
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }
}