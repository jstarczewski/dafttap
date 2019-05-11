package com.clakestudio.pc.dafttapchallange.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clakestudio.pc.dafttapchallange.R
import com.clakestudio.pc.dafttapchallange.data.Score
import kotlinx.android.synthetic.main.record.view.*

class ScoresAdapter(private var scores: List<Score>) : RecyclerView.Adapter<ScoresAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.record, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = scores.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(scores[position].score, scores[position].time)
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(score: Int, timestamp: String) {
            view.text_view_score.text = score.toString()
            view.text_view_time.text = timestamp
        }

    }

    fun replaceData(scores: List<Score>) {
        this.scores = scores
        notifyDataSetChanged()

    }

}