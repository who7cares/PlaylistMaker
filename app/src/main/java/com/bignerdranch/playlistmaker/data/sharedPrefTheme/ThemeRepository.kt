package com.bignerdranch.playlistmaker.data.sharedPrefTheme

interface ThemeRepository {

    fun isDarkThemeEnabled(): Boolean
    fun setDarkTheme(enabled: Boolean)
}