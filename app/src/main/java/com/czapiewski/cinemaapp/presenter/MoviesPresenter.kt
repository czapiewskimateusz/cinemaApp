package com.czapiewski.cinemaapp.presenter

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.czapiewski.cinemaapp.model.Movie
import com.czapiewski.cinemaapp.model.Movies
import com.czapiewski.cinemaapp.view.interfaces.IMovies
import com.czapiewski.cinemaapp.view.interfaces.ItemOnClickListener
import com.czapiewski.cinemaapp.view.MovieDetailActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MoviesPresenter(private val movies: Movies, private val view : IMovies, private val context: Context) :
    ItemOnClickListener {

    private val dBReference = FirebaseDatabase.getInstance().reference

    override fun onSongClick(movie: Movie) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra("MOVIE", movie)
        context.startActivity(intent)
    }

    fun loadData() {
        view.showProgress(true)
        dBReference.child("movies").addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.mapNotNullTo(movies.movies) {
                    it.getValue(Movie::class.java)
                }
                view.showProgress(false)
                view.notifyDataSetChanged()
            }

            override fun onCancelled(dataSnapshot: DatabaseError) {
                Toast.makeText(context,"Unable to load data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
