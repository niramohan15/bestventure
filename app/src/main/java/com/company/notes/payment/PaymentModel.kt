package com.company.notes.payment

import android.os.Parcel
import android.os.Parcelable

class PaymentModel() : Parcelable {
    var cardholderName: String? = null
    var cardNumber: String? = null
    var cardDate: String? = null
    var cardCvc: String? = null

    constructor(parcel: Parcel) : this() {
        cardholderName = parcel.readString()
        cardNumber = parcel.readString()
        cardDate = parcel.readString()
        cardCvc = parcel.readString()
    }

    constructor(cardholderName: String,  cardNumber: String,  cardDate: String, cardCvc: String) : this() {
        this.cardholderName = cardholderName
        this.cardNumber = cardNumber
        this.cardDate = cardDate
        this.cardCvc = cardCvc
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cardholderName)
        parcel.writeString(cardNumber)
        parcel.writeString(cardDate)
        parcel.writeString(cardCvc)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PaymentModel> {
        override fun createFromParcel(parcel: Parcel): PaymentModel {
            return PaymentModel(parcel)
        }

        override fun newArray(size: Int): Array<PaymentModel?> {
            return arrayOfNulls(size)
        }
    }


}