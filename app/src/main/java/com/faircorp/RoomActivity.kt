package com.faircorp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.faircorp.model.ApiServices
import com.faircorp.model.RoomDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomActivity : AppCompatActivity() {
    var room: RoomDto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadRoom(savedInstanceState)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        room = savedInstanceState.getParcelable("room")

        if (room == null) loadRoom(savedInstanceState)
    }

    private fun loadRoom(savedInstanceState: Bundle?) {

        val id = intent.getLongExtra(ROOM_NAME_PARAM, -10)

        lifecycleScope.launch(context = Dispatchers.IO) { // (1)
            runCatching { ApiServices().roomsApiService.findById(id).execute() } // (2)
                .onSuccess {
                    withContext(context = Dispatchers.Main) { // (3)
                        room = it.body()

                        if (room != null) {
                            savedInstanceState?.putParcelable("room", room)

                            findViewById<TextView>(R.id.txt_act_room_name).text = room!!.name
                            findViewById<TextView>(R.id.txt_act_room_building_name).text = room!!.building.name
                            findViewById<TextView>(R.id.txt_act_room_floor).text = room!!.floor.toString()
                            findViewById<TextView>(R.id.txt_act_room_current_temperature).text = room!!.currentTemperature.toString()
                            findViewById<TextView>(R.id.txt_act_room_target_temperature).text = room!!.targetTemperature.toString()

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

    private fun onHeaterList(view: View) {
        val intent = Intent(this, HeatersActivity::class.java).putExtra(ROOM_NAME_PARAM, room!!.id)
        startActivity(intent)
    }

    private fun onWindowList(view: View) {
        val intent = Intent(this, WindowsActivity::class.java).putExtra(ROOM_NAME_PARAM, room!!.id)
        startActivity(intent)
    }
}