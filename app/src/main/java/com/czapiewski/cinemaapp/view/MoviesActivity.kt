package com.czapiewski.cinemaapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.czapiewski.cinemaapp.R
import com.czapiewski.cinemaapp.model.Movies
import com.czapiewski.cinemaapp.presenter.MoviesAdapter
import com.czapiewski.cinemaapp.presenter.MoviesPresenter
import com.czapiewski.cinemaapp.view.interfaces.IMovies
import kotlinx.android.synthetic.main.activity_movies.*

class MoviesActivity : AppCompatActivity(), IMovies {

    private val movies = Movies(ArrayList())
    private val moviesPresenter = MoviesPresenter(movies, this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        rvMoviesList.layoutManager = LinearLayoutManager(this)
        rvMoviesList.adapter = MoviesAdapter(movies.movies, moviesPresenter)
        moviesPresenter.loadData()
    }

    override fun notifyDataSetChanged() {
        rvMoviesList.adapter?.notifyDataSetChanged()
    }


    override fun showProgress(show: Boolean) {
        if (show)
            pbMovies.visibility = View.VISIBLE
        else
            pbMovies.visibility = View.INVISIBLE
    }
}
