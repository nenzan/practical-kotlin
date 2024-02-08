package com.example.practicalkotlin.view.list

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practicalkotlin.R
import com.example.practicalkotlin.utils.Game
import com.example.practicalkotlin.view.detail.DetailActivity
import com.squareup.picasso.Picasso

class MainActivityAdapter(private val persons: List<Game>, val context: Context) :
    RecyclerView.Adapter<MainActivityAdapter.PersonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = persons[position]
        holder.bind(person, context)
    }

    override fun getItemCount(): Int {
        return persons.size
    }

    class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val shortDescTextView: TextView = itemView.findViewById(R.id.shortDescTextView)
        private val ratingTextView: TextView = itemView.findViewById(R.id.ratingTextView)
        private val imageGame: ImageView = itemView.findViewById(R.id.imageItem)

        fun bind(game: Game, context: Context) {
            val gameName = "${game.title}"
            val shortDesc = "${game.shortDescription}"
            val rating = game.rating

            nameTextView.text = gameName
            shortDescTextView.text = shortDesc
            if (rating == 1) {
                ratingTextView.text = "Bad"
            } else if (rating == 2) {
                ratingTextView.text = "Not to Good"
            } else if (rating == 3) {
                ratingTextView.text = "Normal"
            } else if (rating == 4) {
                ratingTextView.text = "Very Good"
            } else {
                ratingTextView.text = "Awesome"
            }

            Picasso.get()
                .load(game.thumbnail)
                .placeholder(R.drawable.baseline_fireplace_24)
                .error(R.drawable.baseline_broken_image_24)
                .fit().centerCrop()
                .into(imageGame);

            itemView.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("id", game.id)
                context.startActivity(intent)
            }
        }
    }
}
