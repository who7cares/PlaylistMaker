package com.bignerdranch.playlistmaker.data.sharedPrefSearch

import android.content.SharedPreferences
import com.bignerdranch.playlistmaker.domain.models.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


const val KEY_FOR_SEARCH_LIST: String = "key_for_search_list"

class SearchPreferences(private val sharedPreferences: SharedPreferences) : SearchPreferencesStorage {
    override fun read(): List<Track> {
        val json = sharedPreferences.getString(KEY_FOR_SEARCH_LIST, null)
        return if (json.isNullOrEmpty()) {
            ArrayList()  // Возвращаем пустой список, если данных нет
        } else {
            val type = object : TypeToken<ArrayList<Track>>() {}.type
            Gson().fromJson(json, type) ?: ArrayList()  // Если десериализация возвращает null, то возвращаем пустой список
        }
    }

    override fun write(searchTracks: List<Track>) {
        val json = Gson().toJson(searchTracks)
        sharedPreferences.edit()
            .putString(KEY_FOR_SEARCH_LIST, json)
            .apply()
    }
}