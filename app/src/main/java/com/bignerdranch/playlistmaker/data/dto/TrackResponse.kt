package com.bignerdranch.playlistmaker.data.dto

import com.bignerdranch.playlistmaker.domain.models.Track

class TrackResponse(
    val resultCount: Int,
    val results: List<Track>
) : Response() {
}