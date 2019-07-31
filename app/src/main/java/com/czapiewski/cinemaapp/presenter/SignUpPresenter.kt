package com.czapiewski.cinemaapp.presenter

import android.content.Context
import android.widget.Toast
import com.czapiewski.cinemaapp.model.User
import com.czapiewski.cinemaapp.view.ISignUp
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
                    view.onEmailError("User with given user name already exists")
                } else {
                    val uuid = UUID.randomUUID().toString()
                    val user = User(uuid, userName, email, pin)
                    dBReference.child("users").child(userName).setValue(user)
                    Toast.makeText(context, "You're signed up!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                view.showProgress(false)
                Toast.makeText(context, "Database connection error", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun validate(email: String, pin: String, pin2: String, userName: String): Boolean {
        var passed = true
        if (Strings.isEmptyOrWhitespace(email)) {
            view.onEmailError("Email is empty")
            passed = false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.onEmailError("Wrong email format")
            passed = false
        }

        if (Strings.isEmptyOrWhitespace(pin) || Strings.isEmptyOrWhitespace(pin2)) {
            view.onPinError("PIN is empty")
            passed = false
        }

        if (pin != pin2) {
            view.onPinError("PINs does not match")
            passed = false
        }

        if (Strings.isEmptyOrWhitespace(userName)) {
            view.onUserNameError("Username is empty")
            passed = false
        }

        return passed
    }

}
