package com.bignerdranch.playlistmaker.domain.impl

import android.content.Context
import android.content.Intent
import com.bignerdranch.playlistmaker.domain.api.ShareAppUseCase

class ShareAppUseCaseImpl(private val context: Context): ShareAppUseCase {
    override fun share(url: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }
        val chooser = Intent.createChooser(intent, "Поделиться через")
        context.startActivity(chooser)
    }
}