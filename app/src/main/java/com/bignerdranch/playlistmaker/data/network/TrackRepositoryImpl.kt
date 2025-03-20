package com.bignerdranch.playlistmaker.data.network

import com.bignerdranch.playlistmaker.data.NetworkClient
import com.bignerdranch.playlistmaker.data.dto.TrackRequest
import com.bignerdranch.playlistmaker.data.dto.TrackResponse
import com.bignerdranch.playlistmaker.domain.api.TrackRepository
import com.bignerdranch.playlistmaker.domain.models.Track

class TrackRepositoryImpl(private val networkClient: NetworkClient) : TrackRepository {
    override fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TrackRequest(expression))

        if (response.resultCode == 200) {
            return (response as TrackResponse).results.map {
                Track(it.trackName,
                    it.artistName,
                    it.trackTimeMillis,
                    it.artworkUrl100,
                    it.trackId,
                    it.collectionName,
                    it.releaseDate,
                    it.primaryGenreName,
                    it.country,
                    it.previewUrl
                    )
            }
        } else {
            return  emptyList()
        }
    }
}

