package com.czapiewski.cinemaapp.view

interface IMovieDetails {

    fun notifyInsertedToPosition(index :Int)
    fun updateScore(score:String)
    fun showProgress(show:Boolean)
    fun notifyDataSetChanged()
    fun updateTickets(ticketsLeft: Long)
}