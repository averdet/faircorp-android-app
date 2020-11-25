package com.faircorp.model

import android.os.Parcel
import android.os.Parcelable

enum class WindowStatus { OPEN, CLOSED}


data class WindowDto (val id: Long, val name: String, val windowStatus: WindowStatus, val roomName: String, val roomId: Long): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        WindowStatus.valueOf(parcel.readString()!!),
        parcel.readString()!!,
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(roomName)
        parcel.writeLong(roomId)
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

