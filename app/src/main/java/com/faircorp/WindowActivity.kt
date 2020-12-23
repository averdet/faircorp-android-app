package com.faircorp

import android.os.Bundle
import android.view.View
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.faircorp.model.ApiServices
import com.faircorp.model.WindowDto
import com.faircorp.model.WindowStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class WindowActivity : BasicActivity() {

    var window: WindowDto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadWindow(savedInstanceState)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        window = savedInstanceState.getParcelable("window")

        if (window == null) loadWindow(savedInstanceState)
    }

    private fun loadWindow(savedInstanceState: Bundle?) {

        val id = intent.getLongExtra(WINDOW_NAME_PARAM, -10)

        lifecycleScope.launch(context = Dispatchers.IO) { // (1)
            runCatching { ApiServices().windowsApiService.findById(id).execute() } // (2)
                .onSuccess {
                    withContext(context = Dispatchers.Main) { // (3)
                        window = it.body()

                        if (window != null) {
                            savedInstanceState?.putParcelable("window", window)

                            findViewById<TextView>(R.id.txt_window_name).text = window!!.name
                            findViewById<TextView>(R.id.txt_room_name).text = window!!.room.name
                            findViewById<TextView>(R.id.txt_window_current_temperature).text =
                                window!!.room.currentTemperature?.toString()
                            findViewById<TextView>(R.id.txt_window_target_temperature).text =
                                window!!.room.targetTemperature?.toString()
                            findViewById<TextView>(R.id.txt_window_status).text =
                                window!!.windowStatus.toString()
                            findViewById<Switch>(R.id.switch_window).text =
                                window!!.windowStatus.toString()
                            findViewById<Switch>(R.id.switch_window).isChecked =
                                window!!.windowStatus == WindowStatus.OPEN

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

        val switch = findViewById<Switch>(R.id.switch_window)
        val newWindowStatus = if (window!!.windowStatus == WindowStatus.CLOSED) {
            WindowStatus.OPEN
        } else {
            WindowStatus.CLOSED
        }

        val changedWindow = WindowDto(window!!.id, window!!.name, newWindowStatus, window!!.room)

        lifecycleScope.launch(context = Dispatchers.IO) {
            runCatching { ApiServices().windowsApiService.switchStatus(window!!.id).execute() }
                .onSuccess {
                    withContext(context = Dispatchers.Main) {

                        findViewById<TextView>(R.id.txt_window_status).text = newWindowStatus.toString()
                        switch.text = newWindowStatus.toString()
                        switch.isChecked = newWindowStatus == WindowStatus.OPEN

                        window = changedWindow

                        Toast.makeText(
                            applicationContext,
                            "Window Status Changed",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                .onFailure {
                    withContext(context = Dispatchers.Main) {

                    }
                    Toast.makeText(
                        applicationContext,
                        "Failed switching $it",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }

    }
}