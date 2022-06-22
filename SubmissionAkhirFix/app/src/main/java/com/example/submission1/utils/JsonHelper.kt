package com.example.submission1.utils

import android.content.Context
import com.example.submission1.data.source.remote.response.MoviesResponse
import com.example.submission1.data.source.remote.response.TvshowsResponse
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class JsonHelper(private val context: Context) {

    private fun parsingFileToString(fileName: String): String? {
        return try {
            val `is` = context.assets.open(fileName)
            val buffer = ByteArray(`is`.available())
            `is`.read(buffer)
            `is`.close()
            String(buffer)

        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    fun loadMovies(): List<MoviesResponse> {
        val list = ArrayList<MoviesResponse>()
        try {
            val responseObject = JSONObject(parsingFileToString("MoviesResponses.json").toString())
            val listArray = responseObject.getJSONArray("movies")
            for (i in 0 until listArray.length()) {
                val movie = listArray.getJSONObject(i)

                val id = movie.getString("id")
                val title = movie.getString("title")
                val description = movie.getString("description")
                val releaseDate = movie.getString("releaseDate")
                val category = movie.getString("category")
                val duration = movie.getString("duration")
                val imagePath= movie.getString("imagePath")

                val movieResponse = MoviesResponse(id, title, description, releaseDate, category, duration, imagePath)
                list.add(movieResponse)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return list
    }

    fun loadTvshow(): List<TvshowsResponse> {
        val list = ArrayList<TvshowsResponse>()
        try {
            val responseObject = JSONObject(parsingFileToString("TvshowResponses.json").toString())
            val listArray = responseObject.getJSONArray("tvshow")
            for (i in 0 until listArray.length()) {
                val tvshow = listArray.getJSONObject(i)

                val id = tvshow.getString("id")
                val title = tvshow.getString("title")
                val description = tvshow.getString("description")
                val releaseYear = tvshow.getString("releaseYear")
                val category = tvshow.getString("category")
                val imagePath= tvshow.getString("imagePath")

                val tvshowResponse = TvshowsResponse(id, title, description, releaseYear, category, imagePath)
                list.add(tvshowResponse)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return list
    }
}
