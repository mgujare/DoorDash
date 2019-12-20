package com.doordash.demo.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Restaurant() : Parcelable {
    var id:String? = null
    var name:String? = null
    var description:String? = null
    @SerializedName("asap_time")
    var asapTime:Int = 0
    @SerializedName("cover_img_url")
    var coverImageUrl:String? = null
    @SerializedName("phone_number")
    var phoneNumber:String? = null

    constructor(nameParam:String?, desc:String?, asap:Int):this() {
        this.name = nameParam
        this.description = desc
        this.asapTime = asap
    }

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        name = parcel.readString()
        description = parcel.readString()
        asapTime = parcel.readInt()
        coverImageUrl = parcel.readString()
        phoneNumber = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeInt(asapTime)
        parcel.writeString(coverImageUrl)
        parcel.writeString(phoneNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Restaurant> {
        override fun createFromParcel(parcel: Parcel): Restaurant {
            return Restaurant(parcel)
        }

        override fun newArray(size: Int): Array<Restaurant?> {

            return arrayOfNulls(size)
        }
    }
}