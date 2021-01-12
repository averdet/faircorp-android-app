package com.faircorp.model

import android.os.Parcel
import android.os.Parcelable

enum class WindowStatus { OPEN, CLOSED }


data class WindowDto(val id: Long, val name: String, val windowStatus: WindowStatus, val room: RoomDto) : Parcelable {
    constructor(parcel: Parcel) : this(
            id = parcel.readLong(),
            name = parcel.readString()!!,
            windowStatus = WindowStatus.valueOf(parcel.readString()!!),
            room = parcel.readParcelable<RoomDto>(RoomDto::class.java.classLoader)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(windowStatus.toString())
        parcel.writeParcelable(room, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WindowDto> {
        override fun createFromParcel(parcel: Parcel): WindowDto {
            return WindowDto(parcel)
        }

        override fun newArray(size: Int): Array<WindowDto?> {
            return arrayOfNulls(size)
        }
    }


}

