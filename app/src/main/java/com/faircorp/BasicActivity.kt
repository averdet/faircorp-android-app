package com.faircorp

import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

open class BasicActivity : AppCompatActivity() {
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_rooms -> startActivity(
                    Intent(this, RoomsActivity::class.java).putExtra(BUILDING_NAME_PARAM, -200)
            )
            R.id.menu_windows -> startActivity(
                    Intent(this, WindowsActivity::class.java).putExtra(ROOM_NAME_PARAM, -200)
            )
            R.id.menu_heaters -> startActivity(
                    Intent(this, HeatersActivity::class.java).putExtra(ROOM_NAME_PARAM, -200)
            )
            R.id.menu_website -> startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse("http://verdet.xyz"))
            )
            R.id.menu_email -> startActivity(
                    Intent(Intent.ACTION_SENDTO, Uri.parse("mailto://alexandre.verdet@etu.emse.fr"))
            )

        }
        return super.onContextItemSelected(item)
    }
}