package com.core.flixter

import org.json.JSONArray

data class Movie (private val posterPath: String, val overview: String, val movieId: Int, val title: String, val votAvg: Double){
    val posterImageUrl = "https://image.tmdb.org/t/p/w342/$posterPath"
    companion object{
        fun fromJsonArray(moviesJsonArray: JSONArray):List<Movie> {
            val movies = mutableListOf<Movie>()
            for (i in 0 until moviesJsonArray.length()){
                val moviesJson = moviesJsonArray.getJSONObject(i)
                movies.add(
                    Movie(
                        moviesJson.getString("poster_path"),
                        moviesJson.getString("overview"),
                        moviesJson.getInt("id"),
                        moviesJson.getString("title"),
                        moviesJson.getDouble("vote_average")
                    )
                )
            }
            return movies
        }
    }
}