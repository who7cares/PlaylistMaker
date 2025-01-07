package com.bignerdranch.playlistmaker.search

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.playlistmaker.MainActivity
import com.bignerdranch.playlistmaker.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity: AppCompatActivity() {

    private lateinit var searchEditText:EditText
    private lateinit var buttonArrowBack:ImageView
    private lateinit var closeImageView:ImageView
    private lateinit var recyclerView: RecyclerView

    private var searchText: String? = null
    private val adapter = SearchAdapter()

    private val tracks = ArrayList<Track>()
    private val gson = Gson()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val iTunesService = retrofit.create(iTunesApi::class.java)



    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchEditText = findViewById(R.id.search_editText)
        buttonArrowBack = findViewById(R.id.arrow_back_search)
        closeImageView = findViewById(R.id.close_ImageView_button)
        recyclerView = findViewById(R.id.track_list)



        // логика работы RecycleView
        adapter.tracks = tracks
        recyclerView.adapter = adapter


        // Добавление TextWatcher после восстановления текста
        searchEditText.addTextChangedListener(simpleTextWatcher)


        // Очищаем содержимое EditText и прячем клаввиатуру
        closeImageView.setOnClickListener {
            searchEditText.text.clear()
            hideKeyboard()
            tracks.clear()
            adapter.notifyDataSetChanged()
        }

        buttonArrowBack.setOnClickListener {
            val intent = Intent(this@SearchActivity, MainActivity::class.java)
            startActivity(intent)
        }

        // поиск в iTunes
        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                iTunesService.findTrack(searchEditText.text.toString()).enqueue(object :
                    Callback<TrackResponse> {
                    override fun onResponse(call: Call<TrackResponse>, response: Response<TrackResponse>) {
                        if (response.code() == 200) {
                            tracks.clear()
                            if(response.body()?.results?.isNotEmpty() == true) {
                                tracks.addAll(response.body()?.results!!)
                                adapter.notifyDataSetChanged()
                            }
                            if(tracks.isEmpty()) {
                                showMessage("ичего не найдено", "")
                            } else {
                                showMessage("", "")
                            }
                        } else {
                            showMessage("что-то пошло не так", response.code().toString())
                        }
                    }

                    override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                        showMessage("что-то пошло не так", t.message.toString())
                    }

                })
                true
            }
            false
        }

    }

    private fun showMessage(text: String, additionalMessage: String){
        if(text.isNotEmpty()) {
            // placeholder Visability
            tracks.clear()
            adapter.notifyDataSetChanged()
            // playsholdermessage.text = text
            if(additionalMessage.isNotEmpty()) {
                Toast.makeText(applicationContext, additionalMessage, Toast.LENGTH_LONG).show()
            }
        } else {
            // placeholderMessage.visibility = View.GONE
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
        }
    }



    // Сохранение состояния при изменении конфигурации устройста: введенный текст и список песен
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        searchText = searchEditText.text.toString()
        outState.putString("searchText", searchText)

        val tracksJson = gson.toJson(tracks)
        outState.putString("tracks", tracksJson)
    }

    // Восстановление состояния после изменения конфигурации устройста: введенный текст и список песен
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchText = savedInstanceState.getString("searchText")
        searchEditText.setText(searchText)

        val savedTracksJson = savedInstanceState.getString("tracks")
        if (!savedTracksJson.isNullOrEmpty()) {
            val trackType = object : TypeToken<ArrayList<Track>>() {}.type
            val restoredTracks: ArrayList<Track> = gson.fromJson(savedTracksJson, trackType)
            tracks.clear()
            tracks.addAll(restoredTracks)
            adapter.notifyDataSetChanged()
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
        val view = currentFocus
        if (view != null) {
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}

