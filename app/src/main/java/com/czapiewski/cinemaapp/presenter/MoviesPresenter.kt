package com.czapiewski.cinemaapp.presenter

import android.content.Context
import android.content.Intent
import com.czapiewski.cinemaapp.view.ItemOnClickListener
import com.czapiewski.cinemaapp.view.MovieDetailActivity

class MoviesPresenter(private val  context: Context) : ItemOnClickListener {

    override fun onSongClick(id: String) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra("MOVIE_ID", id)
        context.startActivity(intent)
    }
}
