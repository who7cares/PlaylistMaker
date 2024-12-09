package com.bignerdranch.playlistmaker

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SearchActivity : AppCompatActivity() {

    private lateinit var textInputLayout:TextInputLayout
    private lateinit var textInputEditText:TextInputEditText
    private lateinit var buttonArrowBack:ImageView

    private var searchText: String? = null


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_search)



        textInputLayout = findViewById(R.id.search_textInputLayout)
        textInputEditText = findViewById(R.id.search_textInputEditText)
        buttonArrowBack = findViewById(R.id.arrow_back_search)



        // Восстанавливаем текст, если он был сохранен
        if (savedInstanceState != null) {
            searchText = savedInstanceState.getString("searchText")
            textInputEditText.setText(searchText)
        }
        // Добавление TextWatcher после восстановления текста
        textInputEditText.addTextChangedListener(simpleTextWatcher)



        buttonArrowBack.setOnClickListener {
            val intent = Intent(this@SearchActivity, MainActivity::class.java)
            startActivity(intent)
        }

        // убираем фокус при нажатии enter
        textInputEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                textInputEditText.clearFocus()

                // Скрываем клавиатуру
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(textInputEditText.windowToken, 0)

                true
            } else {
                false
            }
        }

        // убираем фокус при нажатии за пределами бокса
        textInputLayout.setOnTouchListener { v, event ->
            // Проверяем, если касание произошло за пределами поля ввода
            val rect = Rect()
            textInputLayout.getGlobalVisibleRect(rect)
            if (!rect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                textInputEditText.clearFocus()

                // Скрываем клавиатуру
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(textInputEditText.windowToken, 0)
            }
            false
        }



    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is TextInputEditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    private val simpleTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // empty
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            searchText = p0?.toString()
        }

        override fun afterTextChanged(p0: Editable?) {
            // empty
        }
    }

//    // Сохраняем данные в Bundle при изменении конфигурации (например, при повороте экрана)
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putString("searchText", searchText)  // Сохраняем текст
//    }
//
//    // Восстанавливаем данные из Bundle при восстановлении активности
//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//        searchText = savedInstanceState.getString("searchText")
//        textInputEditText.setText(searchText)  // Восстанавливаем текст в поле ввода
//    }

}