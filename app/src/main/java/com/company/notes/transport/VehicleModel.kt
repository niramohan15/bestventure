package com.company.notes.transport

import android.os.Parcel
import android.os.Parcelable

class VehicleModel() : Parcelable {
    var vehicleName: String? = null
    var vehicleSuitedFor: String? = null
    var vehicleImageLink: String? = null
    var vehiclePrice: String? = null
    var vehicleDescription: String? = null

    constructor(parcel: Parcel) : this() {
        vehicleName = parcel.readString()
        vehicleSuitedFor = parcel.readString()
        vehicleImageLink = parcel.readString()
        vehiclePrice = parcel.readString()
        vehicleDescription = parcel.readString()
    }

    constructor(vehicleName: String,  vehicleSuitedFor: String, vehicleImageLink: String, vehiclePrice: String, vehicleDescription: String) : this() {
        this.vehicleName = vehicleName
        this.vehicleSuitedFor = vehicleSuitedFor
        this.vehicleImageLink = vehicleImageLink
        this.vehiclePrice = vehiclePrice
        this.vehicleDescription = vehicleDescription
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(vehicleName)
        parcel.writeString(vehicleSuitedFor)
        parcel.writeString(vehicleImageLink)
        parcel.writeString(vehiclePrice)
        parcel.writeString(vehicleDescription)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VehicleModel> {
        override fun createFromParcel(parcel: Parcel): VehicleModel {
            return VehicleModel(parcel)
        }

        override fun newArray(size: Int): Array<VehicleModel?> {
            return arrayOfNulls(size)
        }
    }


}