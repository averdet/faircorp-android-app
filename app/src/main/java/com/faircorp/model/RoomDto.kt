package com.faircorp.model

import android.os.Parcel
import android.os.Parcelable

data class RoomDto(val id: Long,
                   val name: String,
                   val currentTemperature: Double?,
                   val targetTemperature: Double?,
                   val floor: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeValue(currentTemperature)
        parcel.writeValue(targetTemperature)
        parcel.writeInt(floor)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RoomDto> {
        override fun createFromParcel(parcel: Parcel): RoomDto {
            return RoomDto(parcel)
        }

        override fun newArray(size: Int): Array<RoomDto?> {
            return arrayOfNulls(size)
        }
    }
}