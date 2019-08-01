package com.czapiewski.cinemaapp.view.interfaces

interface ISignIn {

    fun showProgress(show: Boolean)
    fun onEmailError(message:String)
    fun onPINError(message: String)

}