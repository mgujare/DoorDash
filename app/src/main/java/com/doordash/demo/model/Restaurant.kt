package com.doordash.demo.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "restaurants")
class Restaurant() : Parcelable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id:String = ""

    @ColumnInfo(name = "name")
    var name:String? = null

    @ColumnInfo(name = "description")
    var description:String? = null

    @ColumnInfo(name = "asap_time")
    @SerializedName("asap_time")
    var asapTime:Int = 0

    @ColumnInfo(name = "cover_img_url")
    @SerializedName("cover_img_url")
    var coverImageUrl:String? = null

    @ColumnInfo(name = "phone_number")
    @SerializedName("phone_number")
    var phoneNumber:String? = null

    constructor(nameParam:String?, desc:String?, asap:Int):this() {
        this.name = nameParam
        this.description = desc
        this.asapTime = asap
    }

    constructor(parcel: Parcel) : this() {
        var idValue = parcel.readString()
        id = if (idValue.isNullOrEmpty()) "" else idValue
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