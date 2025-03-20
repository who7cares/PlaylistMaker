package com.bignerdranch.playlistmaker.domain.api

import com.bignerdranch.playlistmaker.domain.models.Track

interface TrackRepository {

    fun searchTracks(expression: String) : List<Track>
}