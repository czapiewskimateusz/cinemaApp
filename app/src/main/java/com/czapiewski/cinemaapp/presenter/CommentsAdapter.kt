package com.czapiewski.cinemaapp.presenter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.czapiewski.cinemaapp.R
import com.czapiewski.cinemaapp.model.Comment
import kotlinx.android.synthetic.main.comment_item.view.*

class CommentsAdapter(val comments: List<Comment>) :
    RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        return CommentsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false))
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.bind(comments[position])
    }

    class CommentsViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        fun bind(comment: Comment) = with(itemView) {
            tvScore.text = comment.score.toString()
            tvUserName.text = comment.user
            tvComment.text = comment.comment
        }

    }
}
