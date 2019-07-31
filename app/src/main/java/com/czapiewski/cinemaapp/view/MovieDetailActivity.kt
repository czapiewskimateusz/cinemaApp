package com.czapiewski.cinemaapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.czapiewski.cinemaapp.R

class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val id = intent.getStringExtra("MOVIE_ID")
        Toast.makeText(this, "Odebrano $id", Toast.LENGTH_LONG).show()
    }
}
