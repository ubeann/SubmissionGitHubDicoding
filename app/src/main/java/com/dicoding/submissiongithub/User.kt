package com.dicoding.submissiongithub

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var avatar:Int,
    var name:String,
    var username:String,
    var company:String,
    var location: String,
    var repository:String?,
    var follower:String?,
    var following:String?
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as Int,
        parcel.readString() as String,
        parcel.readString() as String,
        parcel.readString() as String,
        parcel.readString() as String,
        parcel.readString() as String,
        parcel.readString() as String,
        parcel.readString() as String)

    override fun describeContents(): Int {
        return 0
    }
    companion object : Parceler<User> {

        override fun User.write(parcel: Parcel, flags: Int) {
            parcel.writeValue(avatar)
            parcel.writeString(name)
            parcel.writeString(username)
            parcel.writeString(company)
            parcel.writeString(location)
            parcel.writeString(repository)
            parcel.writeString(follower)
            parcel.writeString(following)
        }

        override fun create(parcel: Parcel): User {
            return User(parcel)
        }
    }
}
