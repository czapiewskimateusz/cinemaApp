package com.czapiewski.cinemaapp.presenter

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.czapiewski.cinemaapp.R

import com.czapiewski.cinemaapp.view.interfaces.ISignIn
import com.czapiewski.cinemaapp.view.MoviesActivity
import com.google.android.gms.common.util.Strings
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SignInPresenter(private val view: ISignIn, private val context: Context) {

    private val dBReference = FirebaseDatabase.getInstance().reference

    fun signIn(userName: String, pin: String) {
        if (validateCredentials(userName, pin)) {
            view.showProgress(true)
            val query = dBReference.child("users").child(userName)
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    validateLogin(dataSnapshot)
                }

                private fun validateLogin(dataSnapshot: DataSnapshot) {
                    view.showProgress(false)
                    if (dataSnapshot.childrenCount == 0L) {
                        Toast.makeText(context, context.getString(R.string.login_error), Toast.LENGTH_SHORT).show()
                    } else {
                        validatePIN(dataSnapshot)
                    }
                }

                private fun validatePIN(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.child("pin").value == pin) {
                        saveUserToSharedPrefs()
                        startMoviesActivity()
                    } else {
                        Toast.makeText(context, context.getString(R.string.login_error), Toast.LENGTH_SHORT).show()
                    }
                }

                private fun startMoviesActivity() {
                    val intent = Intent(context, MoviesActivity::class.java)
                    context.startActivity(intent)
                }

                private fun saveUserToSharedPrefs() {
                    val prefs = context.getSharedPreferences(
                        "com.czapiewski.cinemaapp", Context.MODE_PRIVATE
                    )
                    prefs.edit().putString("USER_NAME", userName).apply()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    view.showProgress(false)
                    Toast.makeText(context, "Database connection error", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun validateCredentials(email: String, pin: String): Boolean {
        var passed = true
        if (Strings.isEmptyOrWhitespace(email)) {
            view.onEmailError(context.getString(R.string.username_empty))
            passed = false
        }
        if (Strings.isEmptyOrWhitespace(pin)) {
            view.onPINError(context.getString(R.string.pin_empty))
            passed = false
        }
        return passed
    }

}
