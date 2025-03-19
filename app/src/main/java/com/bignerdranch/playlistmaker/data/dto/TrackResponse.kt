package com.bignerdranch.playlistmaker.data.dto

import com.bignerdranch.playlistmaker.search.Track

class TrackResponse(
    val resultCount: Int,
    val results: List<Track>
) : Response() {
}