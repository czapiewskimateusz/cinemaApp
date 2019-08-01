package com.czapiewski.cinemaapp.presenter

import android.content.Context
import android.widget.Toast
import com.czapiewski.cinemaapp.model.Comment
import com.czapiewski.cinemaapp.model.Comments
import com.czapiewski.cinemaapp.view.IMovieDetails
import com.google.firebase.database.*
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*


class MovieDetailPresenter(val comments: Comments, val view: IMovieDetails, val context: Context) {
    private val dBReference = FirebaseDatabase.getInstance().reference

    fun loadComments(id: Long) {
        view.showProgress(true)

        val query = dBReference.child("comments").orderByChild("movieId").equalTo(id.toDouble())
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.mapNotNullTo(comments.comments) {
                    it.getValue(Comment::class.java)
                }
                view.showProgress(false)
                view.notifyDataSetChanged()
            }
        })
    }

    fun postComment(commentTxt: String, selectedScore: Double, movieId: Long) {
        val comment = Comment(UUID.randomUUID().toString(), movieId, selectedScore, getLoggedUserName(), commentTxt)
        sendComment(comment)
        updateScore(movieId)
    }

    private fun updateScore(movieId: Long) {
        var newScore = 0.0
        comments.comments.forEach { c -> newScore += c.score }
        newScore /= comments.comments.size
        newScore = roundTo2Digits(newScore)!!
        dBReference.child("movies").child(movieId.toString()).child("score").setValue(newScore)
        view.updateScore(newScore.toString())
    }

    private fun sendComment(comment: Comment) {
        comments.comments.add(0, comment)
        view.notifyInsertedToPosition(0)
        dBReference.child("comments").child(comment.id).setValue(comment)
    }

    private fun getLoggedUserName(): String {
        val prefs = context.getSharedPreferences(
            "com.czapiewski.cinemaapp", Context.MODE_PRIVATE
        )
        return prefs.getString("USER_NAME", "")!!
    }

    fun roundTo2Digits(number: Double): Double? {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).replace(',', '.').toDouble()
    }

    fun bookTicket(movieId: Long, tickets: Long) {
        if (tickets > 0) {
            val ticketsLeft = tickets - 1
            dBReference.child("movies").child(movieId.toString()).child("tickets").setValue(ticketsLeft)
            view.updateTickets(ticketsLeft)
        } else {
            Toast.makeText(context, "Ups, There are no tickets left :(", Toast.LENGTH_SHORT).show()
        }

    }


}
