package com.bignerdranch.playlistmaker.domain.api

import com.bignerdranch.playlistmaker.search.Track

interface TracksInteractor {

    fun searchTrack(expression: String, consumer: TrackConsumer)

    interface  TrackConsumer {
        fun consume(foundMovies: List<Track>)
    }
}