package com.bignerdranch.playlistmaker.data

import com.bignerdranch.playlistmaker.data.dto.Response

interface NetworkClient {

    fun doRequest(dto: Any) : Response
}