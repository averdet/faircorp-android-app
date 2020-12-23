package com.faircorp.model

import android.os.Parcel
import android.os.Parcelable

enum class HeaterStatus { ON, OFF}

data class HeaterDto (val id: Long, val name: String, val power: Long, val heaterStatus: HeaterStatus, val room: RoomDto) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readLong(),
        HeaterStatus.valueOf(parcel.readString()!!),
        RoomDto.createFromParcel(parcel)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeLong(power)
        parcel.writeParcelable(room, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HeaterDto> {
        override fun createFromParcel(parcel: Parcel): HeaterDto {
            return HeaterDto(parcel)
        }

        override fun newArray(size: Int): Array<HeaterDto?> {
            return arrayOfNulls(size)
        }
    }
}