package com.bignerdranch.playlistmaker.domain.impl

import android.view.View
import com.bignerdranch.playlistmaker.data.dto.TrackResponse
import com.bignerdranch.playlistmaker.domain.api.TrackRepository
import com.bignerdranch.playlistmaker.domain.api.TracksInteractor
import com.bignerdranch.playlistmaker.search.Track
import retrofit2.Call
import java.util.concurrent.Executors


class TrackInteractorImpl(private val repository: TrackRepository) : TracksInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun searchTrack(expression: String, consumer: TracksInteractor.TrackConsumer) {
        executor.execute {
            consumer.consume(repository.searchTracks(expression))
        }
    }
}


