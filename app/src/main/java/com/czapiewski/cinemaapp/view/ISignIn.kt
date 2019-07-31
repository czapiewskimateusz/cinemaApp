package com.czapiewski.cinemaapp.view

interface ISignIn {

    fun showProgress(show: Boolean)
    fun onEmailError(message:String)
    fun onPINError(message: String)

}