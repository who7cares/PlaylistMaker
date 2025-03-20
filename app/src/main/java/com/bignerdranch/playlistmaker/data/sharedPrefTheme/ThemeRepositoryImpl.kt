package com.bignerdranch.playlistmaker.data.sharedPrefTheme

import android.content.Context

class ThemeRepositoryImpl(context: Context) : ThemeRepository{

    companion object {
        const val PREFS_NAME = "app_preferences" // Имя SharedPreferences
        const val THEME_KEY = "dark_theme" // Ключ для сохранения темы
    }

    private val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun isDarkThemeEnabled(): Boolean {
        return sharedPreferences.getBoolean(THEME_KEY, false)
    }

    override fun setDarkTheme(enabled: Boolean) {
        sharedPreferences.edit()
            .putBoolean(THEME_KEY, enabled)
            .apply()
    }
}