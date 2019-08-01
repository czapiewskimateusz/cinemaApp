package com.czapiewski.cinemaapp.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import com.czapiewski.cinemaapp.R
import com.czapiewski.cinemaapp.presenter.SignInPresenter
import com.czapiewski.cinemaapp.view.interfaces.IBioCallbackInterface
import com.czapiewski.cinemaapp.view.interfaces.ISignIn
import com.google.android.gms.common.util.Strings
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executors

class SignInActivity : AppCompatActivity(), ISignIn,
    IBioCallbackInterface {

    private val signInPresenter = SignInPresenter(this, this)

    private val biometricPrompt = BiometricPrompt(this, Executors.newSingleThreadExecutor(), BiometricCallback(this))

    private val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Sign in with your fingerprint")
        .setNegativeButtonText("No thanks")
        .build()

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

    override fun onAuthSuccessful() {
        val intent = Intent(this, MoviesActivity::class.java)
        this.startActivity(intent)
    }

    override fun onCancel() {
        biometricPrompt.cancelAuthentication()
    }

    override fun onFailed() {
        biometricPrompt.cancelAuthentication()
    }

    private fun startSignUpActivity() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun signIn() {
        if (Strings.isEmptyOrWhitespace(etUserName.text.toString()) && Strings.isEmptyOrWhitespace(etPIN.text.toString())) {
            if (!isFirstLogin()) {
                biometricPrompt.authenticate(promptInfo)
            } else {
                Toast.makeText(this, "It's your first login, you can't use your fingerprint yet", Toast.LENGTH_LONG).show()
            }
        } else {
            signInPresenter.signIn(etUserName.text.toString(), etPIN.text.toString())
        }
    }

    private fun isFirstLogin(): Boolean {
        val prefs = this.getSharedPreferences("com.czapiewski.cinemaapp", Context.MODE_PRIVATE)
        return Strings.isEmptyOrWhitespace(prefs.getString("USER_NAME", "")!!)
    }
}

class BiometricCallback(private val callbackInterface: IBioCallbackInterface) :
    BiometricPrompt.AuthenticationCallback() {

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
        super.onAuthenticationError(errorCode, errString)
        if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
            callbackInterface.onCancel()
        }
    }

    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
        super.onAuthenticationSucceeded(result)
        callbackInterface.onAuthSuccessful()
    }

    override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()
        callbackInterface.onFailed()
    }
}
