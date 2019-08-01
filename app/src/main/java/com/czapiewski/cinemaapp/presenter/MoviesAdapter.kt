package com.czapiewski.cinemaapp.presenter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.czapiewski.cinemaapp.R
import com.czapiewski.cinemaapp.model.Movie
import com.czapiewski.cinemaapp.view.interfaces.ItemOnClickListener
import kotlinx.android.synthetic.main.movie_item.view.*

class MoviesAdapter(private val movies: List<Movie>, private val listener: ItemOnClickListener) : RecyclerView.Adapter<MoviesAdapter.MovieHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false))
    }

    override fun getItemCount(): Int {
       return movies.size
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(movies[position], listener)
    }

    class MovieHolder (v: View) : RecyclerView.ViewHolder(v) {

        @SuppressLint("SetTextI18n")
        fun bind(movie: Movie, listener: ItemOnClickListener) = with(itemView) {
            tvMovieTitle.text =  movie.title
            tvDirector.text = movie.director
            tvScore.text = movie.score.toString() + "/5"

            Glide.with(this).load(movie.poster).into(ivPoster)

            val cvItem = findViewById<CardView>(R.id.cvMovies)
            cvItem.setOnClickListener {
                listener.onSongClick(movie)
            }
        }

    }
}
