package com.faircorp

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.faircorp.model.ApiServices
import com.faircorp.model.HeaterDto
import com.faircorp.model.HeaterStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HeaterActivity : BasicActivity() {

    var heater: HeaterDto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadHeater(savedInstanceState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        heater = savedInstanceState.getParcelable("heater")

        if (heater != null) loadHeater(savedInstanceState)
    }

    private fun loadHeater(savedInstanceState: Bundle?) {
        val id = intent.getLongExtra(HEATER_NAME_PARAM, -10)

        lifecycleScope.launch(context = Dispatchers.IO) { // (1)
            runCatching { ApiServices().heatersApiService.findById(id).execute() }
                    .onSuccess {
                        withContext(context = Dispatchers.Main) {
                            heater = it.body()

                            if (heater != null) {
                                savedInstanceState?.putParcelable("heater", heater)

                                findViewById<TextView>(R.id.txt_heater_name).text = heater!!.name
                                findViewById<TextView>(R.id.txt_heater_room).text = heater!!.room.name
                                findViewById<TextView>(R.id.txt_heater_current_room_temperature).text = heater!!.room.currentTemperature?.toString()
                                findViewById<TextView>(R.id.txt_heater_target_room_temperature).text = heater!!.room.targetTemperature?.toString()
                                findViewById<TextView>(R.id.txt_heater_status).text = heater!!.heaterStatus.toString()
                                findViewById<Switch>(R.id.heater_switch).text = heater!!.heaterStatus.toString()
                                findViewById<Switch>(R.id.heater_switch).isChecked = heater!!.heaterStatus == HeaterStatus.ON
                                findViewById<TextView>(R.id.txt_heater_power).text = heater!!.power?.toString()
                                findViewById<SeekBar>(R.id.heater_seekbar).progress = if (heater!!.power == null) {
                                    0
                                } else {
                                    heater!!.power!!.toInt()
                                }

                            }
                        }
                    }
                    .onFailure {
                        withContext(context = Dispatchers.Main) {
                            Toast.makeText(
                                    applicationContext,
                                    "Error on heater loading $it",
                                    Toast.LENGTH_LONG
                            ).show()
                        }
                    }
        }

        val seekBar = findViewById<SeekBar>(R.id.heater_seekbar)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
                findViewById<TextView>(R.id.txt_heater_power).text = seekBar.progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                onSeekBarChange(seekBar)
            }
        })
    }

    fun onSwitchChange(view: View) {
        val switch = findViewById<Switch>(R.id.heater_switch)
        val newHeaterStatus = if (heater!!.heaterStatus == HeaterStatus.ON) {
            HeaterStatus.OFF
        } else {
            HeaterStatus.ON
        }

        val changedHeater = HeaterDto(heater!!.id, heater!!.name, heater!!.power, newHeaterStatus, heater!!.room)

        lifecycleScope.launch(context = Dispatchers.IO) {
            runCatching { ApiServices().heatersApiService.switchStatus(heater!!.id).execute() }
                    .onSuccess {
                        withContext(context = Dispatchers.Main) {

                            findViewById<TextView>(R.id.txt_heater_status).text = newHeaterStatus.toString()
                            switch.text = newHeaterStatus.toString()
                            switch.isChecked = newHeaterStatus == HeaterStatus.ON

                            heater = changedHeater

                            Toast.makeText(
                                    applicationContext,
                                    "Heater Status Changed",
                                    Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    .onFailure {
                        withContext(context = Dispatchers.Main) {
                            Toast.makeText(
                                    applicationContext,
                                    "Failed switching $it",
                                    Toast.LENGTH_LONG
                            ).show()
                        }

                    }
        }
    }

    fun onSeekBarChange(seekBar: SeekBar) {

        val newPower = seekBar.progress
        val changedHeater = HeaterDto(heater!!.id, heater!!.name, newPower.toLong(), heater!!.heaterStatus, heater!!.room)

        lifecycleScope.launch(context = Dispatchers.IO) {
            runCatching { ApiServices().heatersApiService.setPower(heater!!.id, newPower.toLong()).execute() }
                    .onSuccess {
                        withContext(context = Dispatchers.Main) {
                            findViewById<TextView>(R.id.txt_heater_power).text = newPower.toString()

                            heater = changedHeater

                            Toast.makeText(
                                    applicationContext,
                                    "Heater Power Changed",
                                    Toast.LENGTH_LONG
                            ).show()
                        }

                    }
                    .onFailure {
                        withContext(context = Dispatchers.Main) {
                            Toast.makeText(
                                    applicationContext,
                                    "Failed setting power $it",
                                    Toast.LENGTH_LONG
                            ).show()
                        }
                    }
        }
    }
}