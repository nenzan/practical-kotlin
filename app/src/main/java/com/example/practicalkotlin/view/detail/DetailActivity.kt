package com.example.practicalkotlin.view.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.practicalkotlin.R
import com.example.practicalkotlin.utils.editGameData
import com.example.practicalkotlin.utils.getGameById
import com.example.practicalkotlin.view.list.MainActivity
import com.squareup.picasso.Picasso


@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val backButton: ImageView = findViewById(R.id.back_button)
        val thumbnail: ImageView = findViewById(R.id.imageDetail)
        val nameGame: TextView = findViewById(R.id.nameTextView)
        val shortDesc: TextView = findViewById(R.id.shortDescTextView)
        val ratingBar: RatingBar = findViewById(R.id.ratingBar)

        backButton.setOnClickListener {
            finish()
        }

        val gameId: Int = intent.getIntExtra("id", 0)
        val data = getGameById(this, gameId)

        nameGame.text = data?.title
        shortDesc.text = data?.shortDescription
        when (data?.rating) {
            1 -> {
                ratingBar.rating = 1.0f
            }

            2 -> {
                ratingBar.rating = 2.0f
            }

            3 -> {
                ratingBar.rating = 3.0f
            }

            4 -> {
                ratingBar.rating = 4.0f
            }

            else -> {
                ratingBar.rating = 5.0f
            }
        }

        Picasso.get()
            .load(data?.thumbnail)
            .placeholder(R.drawable.baseline_fireplace_24)
            .error(R.drawable.baseline_broken_image_24)
            .fit().centerCrop()
            .into(thumbnail);

        ratingBar.onRatingBarChangeListener =
            OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                Log.d("RatingChange", "New rating: ${ratingBar.rating.toInt()}")
                editGameData(gameId, ratingBar.rating.toInt(), this)
                Toast.makeText(this, "Rating change", Toast.LENGTH_SHORT).show()
                finish()
            }
    }
}