package com.czapiewski.cinemaapp.presenter

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.czapiewski.cinemaapp.R
import com.czapiewski.cinemaapp.model.User
import com.czapiewski.cinemaapp.view.MoviesActivity
import com.czapiewski.cinemaapp.view.interfaces.ISignUp
import com.google.android.gms.common.util.Strings
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener


class SignUpPresenter(private val view: ISignUp, private val context: Context) {

    private val dBReference = FirebaseDatabase.getInstance().reference

    fun signUp(email: String, userName: String, pin: String, pin2: String) {
        if (validate(email, pin, pin2, userName)) {
            view.showProgress(true)
            signUpIfNotExists(email.trim(), userName.trim(), pin)
        }
    }

    private fun signUpIfNotExists(email: String, userName: String, pin: String) {

        val query = dBReference.child("users").orderByChild("userName").equalTo(userName)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                view.showProgress(false)
                if (dataSnapshot.childrenCount > 0) {
                    view.onEmailError(context.getString(R.string.user_already_exists))
                } else {
                    val uuid = UUID.randomUUID().toString()
                    val user = User(uuid, userName, email, pin)
                    dBReference.child("users").child(userName).setValue(user)
                    saveUser()
                    startMoviesActivity()
                }
            }

            private fun startMoviesActivity() {
                val intent = Intent(context, MoviesActivity::class.java)
                context.startActivity(intent)
            }

            private fun saveUser() {
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

    private fun validate(email: String, pin: String, pin2: String, userName: String): Boolean {
        var passed = true

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.onEmailError(context.getString(R.string.wrong_email_format))
            passed = false
        }

        if (Strings.isEmptyOrWhitespace(email)) {
            view.onEmailError(context.getString(R.string.email_empty))
            passed = false
        }

        if (Strings.isEmptyOrWhitespace(pin) || Strings.isEmptyOrWhitespace(pin2)) {
            view.onPinError(context.getString(R.string.pin_empty))
            passed = false
        }

        if (pin != pin2) {
            view.onPinError(context.getString(R.string.pin_not_match))
            passed = false
        }

        if (Strings.isEmptyOrWhitespace(userName)) {
            view.onUserNameError(context.getString(R.string.username_empty))
            passed = false
        }

        return passed
    }

}
