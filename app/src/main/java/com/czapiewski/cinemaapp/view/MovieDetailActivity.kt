package com.czapiewski.cinemaapp.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.czapiewski.cinemaapp.R
import com.czapiewski.cinemaapp.model.Comments
import com.czapiewski.cinemaapp.model.Movie
import com.czapiewski.cinemaapp.presenter.CommentsAdapter
import com.czapiewski.cinemaapp.presenter.MovieDetailPresenter
import com.czapiewski.cinemaapp.view.interfaces.IMovieDetails
import com.google.android.gms.common.util.Strings
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity(), IMovieDetails, AdapterView.OnItemSelectedListener {

    private var comments = Comments(ArrayList())
    private var selectedScore = 0.0
    private lateinit var movie: Movie
    private val movieDetailPresenter = MovieDetailPresenter(comments, this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        movie = intent.getParcelableExtra("MOVIE")
        displayMovieDetails(movie)
        setSpinner()
        setCommentsRV()
        setSendOnClickListener(movie)
        movieDetailPresenter.loadComments(movie.id)
        btnBook.setOnClickListener {
            movieDetailPresenter.bookTicket(movie.id, movie.tickets)
        }
    }

    private fun setSendOnClickListener(movie: Movie) {
        ivSend.setOnClickListener {
            if (!Strings.isEmptyOrWhitespace(etComment.text.toString())) {
                movieDetailPresenter.postComment(etComment.text.toString(), selectedScore, movie.id)
                etComment.text.clear()
            } else {
                Toast.makeText(this, "Write a comment first!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun notifyDataSetChanged() {
        rvComments.adapter?.notifyDataSetChanged()
        rvComments.scrollToPosition(comments.comments.size - 1)
    }

    override fun notifyInsertedToPosition(index: Int) {
        rvComments.adapter?.notifyItemInserted(index)
    }

    override fun showProgress(show: Boolean) {
        if (show)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.INVISIBLE
    }

    @SuppressLint("SetTextI18n")
    override fun updateScore(score: String) {
        tvScore.text = "$score/5"
    }

    override fun updateTickets(ticketsLeft: Long) {
        movie.tickets = ticketsLeft
        tvTickets.text = ticketsLeft.toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        selectedScore = parent?.getItemAtPosition(pos).toString().toDouble()
    }

    private fun setCommentsRV() {
        rvComments.layoutManager = LinearLayoutManager(this)
        rvComments.adapter = CommentsAdapter(comments.comments)
    }

    private fun setSpinner() {
        val spinner: Spinner = findViewById(R.id.spScore)
        spinner.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            this,
            R.array.movie_scores,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayMovieDetails(movie: Movie) {
        tvTitle.text = movie.title
        tvDirector.text = movie.director
        tvCast.text = movie.cast

        tvDescription.movementMethod = ScrollingMovementMethod()
        tvDescription.text = movie.description

        tvScore.text = movie.score.toString() + "/5"
        tvTickets.text = movie.tickets.toString()
        tvDate.text = movie.date

        Glide.with(this).load(movie.poster).into(ivPoster)
    }
}
