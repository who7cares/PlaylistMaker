package com.bignerdranch.playlistmaker.data.sharedPrefSearch

import com.bignerdranch.playlistmaker.domain.models.Track

interface SearchPreferencesStorage {
    fun read(): List<Track>
    fun write(searchTracks: List<Track>)
}