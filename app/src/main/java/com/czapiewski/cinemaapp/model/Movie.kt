package com.czapiewski.cinemaapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    var id: Long = 0L,
    var title: String = "",
    var director: String = "",
    var cast: String = "",
    var description: String = "",
    var poster: String = "",
    var score: Double = 0.0,
    var date: String = "",
    var tickets: Long = 0L
) : Parcelable