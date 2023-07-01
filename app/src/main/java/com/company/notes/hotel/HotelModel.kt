package com.company.notes.hotel

import android.os.Parcel
import android.os.Parcelable

class HotelModel() : Parcelable {
    var hotelName: String? = null
    var hotelSuitedFor: String? = null
    var hotelImageLink: String? = null
    var hotelPrice: String? = null
    var hotelDescription: String? = null

    constructor(parcel: Parcel) : this() {
        hotelName = parcel.readString()
        hotelSuitedFor = parcel.readString()
        hotelImageLink = parcel.readString()
        hotelPrice = parcel.readString()
        hotelDescription = parcel.readString()
    }

    constructor(hotelName: String,  hotelSuitedFor: String, hotelImageLink: String, hotelPrice: String, hotelDescription: String) : this() {
        this.hotelName = hotelName
        this.hotelSuitedFor = hotelSuitedFor
        this.hotelImageLink = hotelImageLink
        this.hotelPrice = hotelPrice
        this.hotelDescription = hotelDescription
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(hotelName)
        parcel.writeString(hotelSuitedFor)
        parcel.writeString(hotelImageLink)
        parcel.writeString(hotelPrice)
        parcel.writeString(hotelDescription)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HotelModel> {
        override fun createFromParcel(parcel: Parcel): HotelModel {
            return HotelModel(parcel)
        }

        override fun newArray(size: Int): Array<HotelModel?> {
            return arrayOfNulls(size)
        }
    }


}