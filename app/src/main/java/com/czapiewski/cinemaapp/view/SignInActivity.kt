package com.czapiewski.cinemaapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.czapiewski.cinemaapp.R
import com.czapiewski.cinemaapp.presenter.SignInPresenter
import kotlinx.android.synthetic.main.activity_main.*

class SignInActivity : AppCompatActivity(), ISignIn {

    private val signInPresenter = SignInPresenter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSignIn.setOnClickListener {
            signIn()
        }

        btnSignUp.setOnClickListener {
            startSignUpActivity()
        }
    }
    override fun showProgress(show: Boolean) {
        if (show)
            pBSignIn.visibility = View.VISIBLE
        else
            pBSignIn.visibility = View.INVISIBLE
    }

    override fun onEmailError(message: String) {
        etUserName.error = message
    }

    override fun onPINError(message: String) {
       etPIN.error = message
    }

    private fun startSignUpActivity() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun signIn() {
       signInPresenter.signIn(etUserName.text.toString(), etPIN.text.toString())
    }
}
