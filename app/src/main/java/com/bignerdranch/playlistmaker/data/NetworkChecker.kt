package com.bignerdranch.playlistmaker.data

import android.content.Context

interface NetworkChecker {

    fun isNetworkAvailable(context: Context): Boolean
}