package com.bignerdranch.playlistmaker.presentation

import android.app.Application
import com.bignerdranch.playlistmaker.data.sharedPrefTheme.ThemeRepositoryImpl
import com.bignerdranch.playlistmaker.domain.theme.ThemeUseCase

class App: Application() {

    lateinit var themeUseCase: ThemeUseCase

    override fun onCreate() {
        super.onCreate()

        val repository = ThemeRepositoryImpl(this)
        themeUseCase = ThemeUseCase(repository)

        themeUseCase.applyTheme(themeUseCase.getTheme())
    }

}