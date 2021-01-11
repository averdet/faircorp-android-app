package com.faircorp.model

import android.os.Parcel
import android.os.Parcelable

data class BuildingDto (val id: Long, val name: String):
    Parcelable {
    constructor(parcel: Parcel) : this(
        id = parcel.readLong(),
        name = parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BuildingDto> {
        override fun createFromParcel(parcel: Parcel): BuildingDto {
            return BuildingDto(parcel)
        }

        override fun newArray(size: Int): Array<BuildingDto?> {
            return arrayOfNulls(size)
        }
    }


}