package com.czapiewski.cinemaapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.czapiewski.cinemaapp.R
import com.czapiewski.cinemaapp.model.Movie
import com.czapiewski.cinemaapp.presenter.MoviesAdapter
import com.czapiewski.cinemaapp.presenter.MoviesPresenter
import kotlinx.android.synthetic.main.activity_movies.*

class MoviesActivity : AppCompatActivity() {

    private val moviesPresenter = MoviesPresenter( this)
    val movies: ArrayList<Movie> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        initMockData()
        rvMoviesList.layoutManager = LinearLayoutManager(this)
        rvMoviesList.adapter = MoviesAdapter(movies, moviesPresenter)

    }

    private fun initMockData() {
        movies.add(Movie("1234", "Incepcja", "Christopher Nolan", "", "", "https://ssl-gfx.filmweb.pl/po/08/91/500891/7354571.3.jpg", "4.5/5"))
        movies.add(Movie("1235", "Django", "Quentin Tarantino", "", "", "https://ssl-gfx.filmweb.pl/po/05/41/620541/7504936.3.jpg", "4.5/5"))
        movies.add(Movie("1236", "Bękarty Wojny", "Quentin Tarantino", "", "", "https://ssl-gfx.filmweb.pl/po/77/47/137747/7276639.3.jpg", "4.5/5"))
        movies.add(Movie("1237", "Once Upon a Time in Hollywood", "Quentin Tarantino", "", "", "https://ssl-gfx.filmweb.pl/po/45/61/804561/7891889.3.jpg", "4.5/5"))
        movies.add(Movie("1238", "Kill Bill", "Quentin Tarantino", "", "", "https://ssl-gfx.filmweb.pl/po/32/00/33200/6900531.3.jpg", "4.5/5"))
        movies.add(Movie("1239", "Reservoir Dogs", "Quentin Tarantino", "", "", "https://ssl-gfx.filmweb.pl/po/18/43/11843/6916384.3.jpg", "4.5/5"))
        movies.add(Movie("1210", "The Hateful Eight", "Quentin Tarantino", "", "", "https://ssl-gfx.filmweb.pl/po/41/92/714192/7715662.3.jpg", "4.5/5"))
        movies.add(Movie("1211", "Pulp Fiction", "Quentin Tarantino", "", "", "https://ssl-gfx.filmweb.pl/po/10/39/1039/7517880.3.jpg", "4.5/5"))
        movies.add(Movie("1212", "John Wick 3", "Chad Stahelski", "", "", "https://ssl-gfx.filmweb.pl/po/25/56/792556/7881937.3.jpg", "4.5/5"))
    }
}
