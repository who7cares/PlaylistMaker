package com.bignerdranch.playlistmaker

import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textview.MaterialTextView

class SettingsActivity : AppCompatActivity() {

    private lateinit var buttonArrowBack: ImageView
    private lateinit var switch: SwitchMaterial
    private lateinit var share: MaterialTextView
    private lateinit var sendToSupport: MaterialTextView
    private lateinit var userAgreement: MaterialTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)

        buttonArrowBack = findViewById(R.id.arrow_back)
        switch = findViewById(R.id.switch_compat)
        share = findViewById(R.id.share)
        sendToSupport = findViewById(R.id.support)
        userAgreement = findViewById(R.id.user_agreement)


        buttonArrowBack.setOnClickListener {
            val intent = Intent(this@SettingsActivity, MainActivity::class.java)
            startActivity(intent)
        }


        share.setOnClickListener {
            val url = getString(R.string.share_url)

            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, url)  // передаем ссылку на страницу Google
                type = "text/plain"  // Указываем тип данных - обычный текст
            }

            // Проверяем, может ли устройство обработать этот интент
            val chooserIntent = Intent.createChooser(intent, "Поделиться через")
            startActivity(chooserIntent)
        }


        sendToSupport.setOnClickListener {
            val email = getString(R.string.sendToSupport_email)
            val theme = getString(R.string.sendToSupport_theme)
            val text = getString(R.string.sendToSupport_text)

            val intent = Intent().apply {
                action = Intent.ACTION_SENDTO
                data = Uri.parse("mailto:$email")

                putExtra(Intent.EXTRA_SUBJECT, theme)
                putExtra(Intent.EXTRA_TEXT, text)
            }

            val chooserIntent = Intent.createChooser(intent, "Отправить по почте:")
            startActivity(chooserIntent)
        }


        userAgreement.setOnClickListener {
            val url = getString(R.string.userAgreement_url)
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(url)
            }

            val chooserIntent = Intent.createChooser(intent, "Выберите бразуер:")
            startActivity(chooserIntent)
        }

//        // Получаем сохраненный режим из SharedPreferences
//        val isDarkMode = getDarkModePreference()
//
//        // Устанавливаем начальное состояние переключателя в зависимости от сохраненной темы
//        switch.isChecked = isDarkMode
//
//        switch.setOnCheckedChangeListener { _, isChecked ->
//            // Сохраняем выбор пользователя в SharedPreferences
//            saveDarkModePreference(isChecked)
//
//            // Меняем тему
//            if (isChecked) {
//                // Темная тема
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//            } else {
//                // Светлая тема
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//            }
//
//            // Перезапускаем активность, чтобы применить изменения темы
//            recreate()
//        }
//    }
//
//    // Метод для получения состояния темы из SharedPreferences
//    private fun getDarkModePreference(): Boolean {
//        val sharedPreferences = getSharedPreferences("theme_preferences", MODE_PRIVATE)
//        return sharedPreferences.getBoolean("dark_mode", false) // по умолчанию светлая тема
//    }
//
//    // Метод для сохранения состояния темы в SharedPreferences
//    private fun saveDarkModePreference(isDarkMode: Boolean) {
//        val sharedPreferences = getSharedPreferences("theme_preferences", MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.putBoolean("dark_mode", isDarkMode)
//        editor.apply()
//    }


        // Устанавливаем начальные цвета свича сразу при запуске
        switch.trackDrawable.setColorFilter(
            ContextCompat.getColor(this, R.color.track_off),
            PorterDuff.Mode.SRC_IN
        )

        switch.thumbDrawable.setColorFilter(
            ContextCompat.getColor(this, R.color.thumb_off),
            PorterDuff.Mode.SRC_IN
        )


        // изменяем цвет свича
        switch.setOnCheckedChangeListener { _, isChecked ->
            // Изменение цвета полоски (track)
            if (isChecked) {
                switch.trackDrawable.setColorFilter(
                    ContextCompat.getColor(this, R.color.track_on),
                    PorterDuff.Mode.SRC_IN
                )

                // Изменение цвета ползунка (thumb)
                switch.thumbDrawable.setColorFilter(
                    ContextCompat.getColor(this, R.color.thumb_on),
                    PorterDuff.Mode.SRC_IN
                )
            } else {
                switch.trackDrawable.setColorFilter(
                    ContextCompat.getColor(this, R.color.track_off),
                    PorterDuff.Mode.SRC_IN
                )

                // Изменение цвета ползунка (thumb)
                switch.thumbDrawable.setColorFilter(
                    ContextCompat.getColor(this, R.color.thumb_off),
                    PorterDuff.Mode.SRC_IN
                )
            }
        }
    }
}
