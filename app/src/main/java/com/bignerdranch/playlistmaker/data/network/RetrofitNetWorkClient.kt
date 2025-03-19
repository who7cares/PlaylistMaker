package com.bignerdranch.playlistmaker.data.network

import com.bignerdranch.playlistmaker.data.NetworkClient
import com.bignerdranch.playlistmaker.data.dto.Response
import com.bignerdranch.playlistmaker.data.dto.TrackRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitNetWorkClient : NetworkClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val iTunesService = retrofit.create(iTunesApi::class.java)

    override fun doRequest(dto: Any): Response {
        if (dto is TrackRequest) {
            val resp = iTunesService.findTrack(dto.expression).execute()
            val body = resp.body() ?: Response()

            return body.apply { resultCode = resp.code() }
        } else {
            return Response().apply { resultCode = 400 }
        }
    }
}