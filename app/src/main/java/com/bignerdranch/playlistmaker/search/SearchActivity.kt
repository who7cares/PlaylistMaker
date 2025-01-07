package com.bignerdranch.playlistmaker.search

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.playlistmaker.MainActivity
import com.bignerdranch.playlistmaker.R

class SearchActivity: AppCompatActivity() {

    private lateinit var searchEditText:EditText
    private lateinit var buttonArrowBack:ImageView
    private lateinit var closeImageView:ImageView
    private lateinit var recyclerView: RecyclerView

    private var searchText: String? = null

    private val tracks = listOf<Track>(
        Track(
            "Smells Like Teen Spirit",
            "Nirvana",
            "5:01",
            "https://is5-ssl.mzstatic.com/image/thumb/Music115/v4/7b/58/c2/7b58c21a-2b51-2bb2-e59a-9bb9b96ad8c3/00602567924166.rgb.jpg/100x100bb.jpg"),
        Track("Billie Jean",
            "Michael Jackson",
            "4:35",
            "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/3d/9d/38/3d9d3811-71f0-3a0e-1ada-3004e56ff852/827969428726.jpg/100x100bb.jpg"),
        Track("Stayin' Alive",
            "Bee Gees",
            "4:10",
            "https://is4-ssl.mzstatic.com/image/thumb/Music115/v4/1f/80/1f/1f801fc1-8c0f-ea3e-d3e5-387c6619619e/16UMGIM86640.rgb.jpg/100x100bb.jpg"),
        Track("Whole Lotta Love",
            "Led Zeppelin",
            "5:33",
            "https://is2-ssl.mzstatic.com/image/thumb/Music62/v4/7e/17/e3/7e17e33f-2efa-2a36-e916-7f808576cf6b/mzm.fyigqcbs.jpg/100x100bb.jpg"),
        Track("Sweet Child O'Mine",
            "Guns N' Roses",
            "5:03",
            "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/a0/4d/c4/a04dc484-03cc-02aa-fa82-5334fcb4bc16/18UMGIM24878.rgb.jpg/100x100bb.jpg")
    )

    private var filteredTracks = tracks



    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchEditText = findViewById(R.id.search_editText)
        buttonArrowBack = findViewById(R.id.arrow_back_search)
        closeImageView = findViewById(R.id.close_ImageView_button)
        recyclerView = findViewById(R.id.track_list)

        // логика работы RecycleView
        val adapter = SearchAdapter(filteredTracks)
        recyclerView.adapter = adapter


        // Добавление TextWatcher после восстановления текста
        searchEditText.addTextChangedListener(simpleTextWatcher)


        // Очищаем содержимое EditText и прячем клаввиатуру
        closeImageView.setOnClickListener {
            searchEditText.text.clear()
            hideKeyboard()
        }

        buttonArrowBack.setOnClickListener {
            val intent = Intent(this@SearchActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }


    private val simpleTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // empty
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            closeImageView.visibility = if (p0.isNullOrEmpty()) View.GONE else View.VISIBLE
            searchText = p0?.toString()

        }

        override fun afterTextChanged(p0: Editable?) {
            filterTrack(searchText)
        }
    }

    // фильтрация треков по веденному тексту в EditText
    private fun filterTrack(enteredText: String?) {
        val filteredTracks = if(enteredText.isNullOrEmpty()) {
            tracks
        } else {
            tracks.filter { track ->
                track.trackName.contains(enteredText, ignoreCase = true) ||
                        track.artistName.contains(enteredText, ignoreCase = true)
            }
        }
        (recyclerView.adapter as SearchAdapter).updateTracks(filteredTracks)
    }

    // Сохранение состояния при изменении конфигурации устройста
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        searchText = searchEditText.text.toString()
        outState.putString("searchText", searchText)
    }

    // Восстановление состояния после изменения конфигурации устройста
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchText = savedInstanceState.getString("searchText")
        searchEditText.setText(searchText)
    }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
        val view = currentFocus
        if (view != null) {
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}

