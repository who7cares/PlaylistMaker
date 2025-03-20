package com.bignerdranch.playlistmaker.domain.impl

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.bignerdranch.playlistmaker.domain.api.OpenUserAgreementUseCase

class OpenUserAgreementUseCaseImp(private val context: Context) : OpenUserAgreementUseCase {
    override fun open(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        val chooser = Intent.createChooser(intent, "Выберите браузер:")
        context.startActivity(chooser)
    }
}