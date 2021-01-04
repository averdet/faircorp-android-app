package com.faircorp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import org.w3c.dom.Text

class HeaterActivity : AppCompatActivity() {

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

        if ( heater != null ) loadHeater(savedInstanceState)
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
                                findViewById<SeekBar>(R.id.heater_seekbar).progress = if ( heater!!.power == null) {0} else { heater!!.power!!.toInt() }

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
    }
}