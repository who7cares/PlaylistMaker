package com.bignerdranch.playlistmaker

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SearchActivity: AppCompatActivity() {

    private lateinit var searchEditText:EditText
    private lateinit var buttonArrowBack:ImageView
    private lateinit var closeImageView:ImageView

    private var searchText: String? = null


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_search)



        searchEditText = findViewById(R.id.search_editText)
        buttonArrowBack = findViewById(R.id.arrow_back_search)
        closeImageView = findViewById(R.id.close_ImageView_button)


//
//        // Восстанавливаем текст, если он был сохранен без использоввания onRestoreInstanceState
//        if (savedInstanceState != null) {
//            searchText = savedInstanceState.getString("searchText")
//            textInputEditText.setText(searchText)
//        }


        // Добавление TextWatcher после восстановления текста
        searchEditText.addTextChangedListener(simpleTextWatcher)


        closeImageView.setOnClickListener {
            // Очищаем содержимое EditText и прячем клаввиатуру
            searchEditText.text.clear()
            hideKeyboard()
        }

        buttonArrowBack.setOnClickListener {
            val intent = Intent(this@SearchActivity, MainActivity::class.java)
            startActivity(intent)
        }

//        // убираем фокус при нажатии enter
//        textInputEditText.setOnEditorActionListener { v, actionId, event ->
//            if (actionId == EditorInfo.IME_ACTION_DONE) {
//                textInputEditText.clearFocus()
//
//                // Скрываем клавиатуру
//                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                imm.hideSoftInputFromWindow(textInputEditText.windowToken, 0)
//
//                true
//            } else {
//                false
//            }
//        }
//
//        // убираем фокус при нажатии за пределами бокса
//        textInputLayout.setOnTouchListener { v, event ->
//            // Проверяем, если касание произошло за пределами поля ввода
//            val rect = Rect()
//            textInputLayout.getGlobalVisibleRect(rect)
//            if (!rect.contains(event.rawX.toInt(), event.rawY.toInt())) {
//                textInputEditText.clearFocus()
//
//                // Скрываем клавиатуру
//                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                imm.hideSoftInputFromWindow(textInputEditText.windowToken, 0)
//            }
//            false
//        }

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
            // empty
        }
    }

    // Сохранение состояния при изменении конфигурации (например, при повороте экрана)
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        searchText = searchEditText.text.toString()
        outState.putString("searchText", searchText)
    }

    // Восстановление состояния после изменения конфигурации (например, после поворота экрана)
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchText = savedInstanceState.getString("searchText")
        searchEditText.setText(searchText)
    }

    private fun hideKeyboard() {
        // Получаем InputMethodManager, который помогает работать с клавиатурой
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager

        // Проверяем, есть ли активный фокус (т.е. поле, на котором мы что-то вводим)
        val view = currentFocus

        // Если фокус есть, скрываем клавиатуру
        if (view != null) {
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}

