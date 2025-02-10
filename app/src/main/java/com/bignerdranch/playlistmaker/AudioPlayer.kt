package com.bignerdranch.playlistmaker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bignerdranch.playlistmaker.search.SearchActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.text.SimpleDateFormat
import java.util.Locale



class AudioPlayer : AppCompatActivity() {

    private lateinit var trackName: TextView
    private lateinit var artistName: TextView
    private lateinit var durationTime: TextView
    private lateinit var album: TextView
    private lateinit var songYear: TextView
    private lateinit var songStyle: TextView
    private lateinit var songCountry: TextView
    private lateinit var songCover: ImageView

    private lateinit var playPauseButton: ImageButton
    private lateinit var addToLikeButton: ImageButton
    private lateinit var arrowBackButton: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_player)



        // значения для интентов
        trackName = findViewById(R.id.trackName)
        artistName = findViewById(R.id.artistName)
        durationTime = findViewById(R.id.durationTime)
        album = findViewById(R.id.album)
        songYear = findViewById(R.id.sondYear)
        songStyle = findViewById(R.id.songStyle)
        songCountry = findViewById(R.id.songCountry)
        songCover = findViewById(R.id.songCover)

        //другие вью
        playPauseButton = findViewById(R.id.PlayOrStop)
        addToLikeButton = findViewById(R.id.addToLike)
        arrowBackButton = findViewById(R.id.arrow_back)




        // Установка интентов
        val intent: Intent = intent


        val songYearIntent = intent.getStringExtra("songYear") ?: return
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).parse(songYearIntent)
        val formattedYear = SimpleDateFormat("yyyy", Locale.getDefault()).format(date!!)

        val durationTimeIntent = intent.getIntExtra("durationTime", 0)
        val formattedTime = SimpleDateFormat("mm:ss", Locale.getDefault()).format(durationTimeIntent)

        val songCoverIntent = intent.getStringExtra("songCover")


        trackName.text = intent.getStringExtra("trackName")
        artistName.text = intent.getStringExtra("artistName")
        album.text = intent.getStringExtra("album")

        durationTime.text = formattedTime
        songYear.text = formattedYear

        songStyle.text = intent.getStringExtra("songStyle")
        songCountry.text = intent.getStringExtra("songCountry")

        Glide.with(applicationContext)
            .load(songCoverIntent)
            .fitCenter()
            .transform(RoundedCorners(20))
            .placeholder(R.drawable.placeholder_search)
            .into(songCover)



        arrowBackButton .setOnClickListener {

            val _intent = Intent(this@AudioPlayer, SearchActivity::class.java)
            startActivity(_intent)
        }


        playPauseButton.setOnClickListener {
            val currentDrawable = playPauseButton.drawable
            val pauseDrawable = ContextCompat.getDrawable(this, R.drawable.pause_icon)

            // Сравниваем состояния drawable, а не сами объекты
            if (currentDrawable.constantState == pauseDrawable?.constantState) {
                playPauseButton.setImageResource(R.drawable.play_arrow_icon)
            } else {
                playPauseButton.setImageResource(R.drawable.pause_icon)
            }
        }

        addToLikeButton.setOnClickListener {
            val currentDrawable = addToLikeButton.drawable
            val likeDrawable = ContextCompat.getDrawable(this, R.drawable.empty_like)

            // Сравниваем состояния drawable, а не сами объекты
            if (currentDrawable.constantState == likeDrawable?.constantState) {
                addToLikeButton.setImageResource(R.drawable.is_liked)
            } else {
                addToLikeButton.setImageResource(R.drawable.empty_like)
            }
        }


    }
}