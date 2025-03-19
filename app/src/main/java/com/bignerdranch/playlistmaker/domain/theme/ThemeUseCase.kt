package com.bignerdranch.playlistmaker.domain.theme

import androidx.appcompat.app.AppCompatDelegate
import com.bignerdranch.playlistmaker.data.sharedPrefTheme.ThemeRepository

class ThemeUseCase(private val repository: ThemeRepository) {

    fun getTheme(): Boolean = repository.isDarkThemeEnabled()

    fun switchTheme (enabled: Boolean) {
        repository.setDarkTheme(enabled)
        applyTheme(enabled)

    }

      fun applyTheme(isDarkTheme: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkTheme) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
    }
}