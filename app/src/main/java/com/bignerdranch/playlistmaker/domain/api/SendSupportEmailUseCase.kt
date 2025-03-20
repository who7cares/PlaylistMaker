package com.bignerdranch.playlistmaker.domain.api

interface SendSupportEmailUseCase {
    fun sendSupportEmail(email: String, theme: String, message: String)
}