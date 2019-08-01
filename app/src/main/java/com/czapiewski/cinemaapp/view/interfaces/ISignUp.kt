package com.czapiewski.cinemaapp.view.interfaces

interface ISignUp {
    fun showProgress(show:Boolean)
    fun onEmailError(message: String)
    fun onPinError(message: String)
    fun onUserNameError(message: String)
}
