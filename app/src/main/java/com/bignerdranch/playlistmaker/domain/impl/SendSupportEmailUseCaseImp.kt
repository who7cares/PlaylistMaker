package com.bignerdranch.playlistmaker.domain.impl

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.bignerdranch.playlistmaker.domain.api.SendSupportEmailUseCase

class SendSupportEmailUseCaseImp(private val context: Context) : SendSupportEmailUseCase {
    override fun sendSupportEmail(email: String, theme: String, message: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822" // Указывает на email-клиенты
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email)) // Указываем email получателя
            putExtra(Intent.EXTRA_SUBJECT, theme) // Тема письма
            putExtra(Intent.EXTRA_TEXT, message) // Тело письма
        }

        val chooser = Intent.createChooser(intent, "Отправить по почте:")
        context.startActivity(chooser)
    }
}