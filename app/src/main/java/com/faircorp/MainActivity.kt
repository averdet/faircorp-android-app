package com.faircorp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

const val ROOM_NAME_PARAM = "com.faircorp.heatername.attribute"
const val WINDOW_NAME_PARAM = "com.faircorp.windowname.attribute"
const val HEATER_NAME_PARAM = "com.faircorp.heatername.attribute"

class MainActivity : BasicActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, RoomsActivity::class.java)
        startActivity(intent)
    }
}