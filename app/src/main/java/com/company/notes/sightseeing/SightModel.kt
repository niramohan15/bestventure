package com.company.notes.sightseeing

import android.os.Parcel
import android.os.Parcelable

class SightModel() : Parcelable {
    var sightName: String? = null
    var sightSuitedFor: String? = null
    var sightImageLink: String? = null
    var sightPrice: String? = null
    var sightDescription: String? = null

    constructor(parcel: Parcel) : this() {
        sightName = parcel.readString()
        sightSuitedFor = parcel.readString()
        sightImageLink = parcel.readString()
        sightPrice = parcel.readString()
        sightDescription = parcel.readString()
    }

    constructor(sightName: String,  sightSuitedFor: String, sightImageLink: String, sightPrice: String, sightDescription: String) : this() {
        this.sightName = sightName
        this.sightSuitedFor = sightSuitedFor
        this.sightImageLink = sightImageLink
        this.sightPrice = sightPrice
        this.sightDescription = sightDescription
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(sightName)
        parcel.writeString(sightSuitedFor)
        parcel.writeString(sightImageLink)
        parcel.writeString(sightPrice)
        parcel.writeString(sightDescription)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SightModel> {
        override fun createFromParcel(parcel: Parcel): SightModel {
            return SightModel(parcel)
        }

        override fun newArray(size: Int): Array<SightModel?> {
            return arrayOfNulls(size)
        }
    }


}