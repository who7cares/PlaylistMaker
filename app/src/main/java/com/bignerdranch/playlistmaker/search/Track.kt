package com.bignerdranch.playlistmaker.search

data class Track (
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Int,
    val artworkUrl100: String)