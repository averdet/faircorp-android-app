package com.faircorp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView


class WindowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val param = intent.getStringExtra(WINDOW_NAME_PARAM)
        val windowName = findViewById<TextView>(R.id.txt_window_name2)
        windowName.text = param
    }
}