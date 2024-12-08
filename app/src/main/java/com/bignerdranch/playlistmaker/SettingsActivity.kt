package com.bignerdranch.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat

class SettingsActivity : AppCompatActivity() {

    private lateinit var buttonArrowBack: ImageView
    private lateinit var switch: SwitchCompat
    private lateinit var share: ImageView
    private lateinit var sendToSupport: ImageView
    private lateinit var userAgreement: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
    }
}
