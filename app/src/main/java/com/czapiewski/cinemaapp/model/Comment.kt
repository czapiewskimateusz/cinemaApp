package com.czapiewski.cinemaapp.model

data class Comment(
    val id: String = "",
    val movieId: Long = 0L,
    val score: Double = 0.0,
    val user: String = "",
    val comment: String = ""
)