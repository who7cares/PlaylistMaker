package com.bignerdranch.playlistmaker.domain.api

import com.bignerdranch.playlistmaker.search.Track

interface TrackRepository {

    fun searchTracks(expression: String) : List<Track>
}