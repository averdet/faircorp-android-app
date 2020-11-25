package com.faircorp

import android.os.Bundle
import android.view.View
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.faircorp.model.ApiServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class WindowActivity : BasicActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.getLongExtra(WINDOW_NAME_PARAM, -10)

        lifecycleScope.launch(context = Dispatchers.IO) { // (1)
            runCatching { ApiServices().windowsApiService.findById(id).execute() } // (2)
                .onSuccess {
                    withContext(context = Dispatchers.Main) { // (3)
                        val window = it.body()

                        if (window != null) {

                            lifecycleScope.launch(context = Dispatchers.IO) {
                                runCatching { ApiServices().roomsApiService.findById(window.roomId).execute() }
                                    .onSuccess {
                                        withContext(context = Dispatchers.Main) {
                                            val room = it.body()
                                            if (room != null) {
                                                findViewById<TextView>(R.id.txt_window_current_temperature).text =
                                                    room.currentTemperature?.toString()
                                                findViewById<TextView>(R.id.txt_window_target_temperature).text =
                                                    room.targetTemperature?.toString()
                                            }
                                        }
                                    }
                                    .onFailure {
                                        withContext(context = Dispatchers.Main) {
                                            Toast.makeText(
                                                applicationContext,
                                                "Error on room loading $it",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                            }


                            findViewById<TextView>(R.id.txt_window_name).text = window.name
                            findViewById<TextView>(R.id.txt_room_name).text = window.roomName
                            findViewById<TextView>(R.id.txt_window_status).text = window.windowStatus.toString()
                        }
                    }
                }
                .onFailure {
                    withContext(context = Dispatchers.Main) { // (3)
                        Toast.makeText(
                            applicationContext,
                            "Error on window loading $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }


    }

    fun onSwitchChange(view: View) {

        val switchState = findViewById<Switch>(R.id.switch_window)
        Toast.makeText(
            applicationContext,
            "Switch Change",
            Toast.LENGTH_LONG
        ).show()
    }
}