package com.czapiewski.cinemaapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.czapiewski.cinemaapp.R
import com.czapiewski.cinemaapp.presenter.SignUpPresenter
import com.czapiewski.cinemaapp.view.interfaces.ISignUp
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity(), ISignUp {

    private val signUpPresenter = SignUpPresenter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btnSignUpConfirm.setOnClickListener {
            signUp()
        }
    }

    override fun showProgress(show: Boolean) {
        if (show)
            pbSignUp.visibility = View.VISIBLE
        else
            pbSignUp.visibility = View.INVISIBLE
    }

    override fun onEmailError(message: String) {
        etUserName.error = message
    }

    override fun onPinError(message: String) {
        etPIN2.error = message
    }

    override fun onUserNameError(message: String) {
        etUserName.error = message
    }

    private fun signUp() {
        signUpPresenter.signUp(
            etEmail.text.toString(),
            etUserName.text.toString(),
            etPIN.text.toString(),
            etPIN2.text.toString()
        )
    }
}
