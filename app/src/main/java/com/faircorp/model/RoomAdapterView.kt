package com.faircorp.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.faircorp.OnRoomSelectedListener
import com.faircorp.R

class RoomAdapter(val listener: OnRoomSelectedListener): RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() { // (1)

    inner class RoomViewHolder(view: View) : RecyclerView.ViewHolder(view) { // (2)
        val name: TextView = view.findViewById(R.id.txt_room_item_name)
        val building: TextView = view.findViewById(R.id.txt_room_item_building)
        val temp: TextView = view.findViewById(R.id.txt_room_item_temp)
    }

    private val items = mutableListOf<RoomDto>() // (3)

    fun update(rooms: List<RoomDto>) {  // (4)
        items.clear()
        items.addAll(rooms)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size // (5)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder { // (6)
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_rooms_item, parent, false)
        return RoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val room = items[position]
        holder.apply {
            name.text = room.name
            building.text = room.building.name
            temp.text = "Current Temperature : " + room.currentTemperature.toString()
            itemView.setOnClickListener { listener.onRoomSelected(room.id) } // (1)
        }
    }

    override fun onViewRecycled(holder: RoomViewHolder) { // (2)
        super.onViewRecycled(holder)
        holder.apply {
            itemView.setOnClickListener(null)
        }

    }
}