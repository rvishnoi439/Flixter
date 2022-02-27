package com.core.flixter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RatingBar
import android.widget.TextView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import okhttp3.Headers


private const val YOUTUBE_KEY = "AIzaSyDG7IZXDTr3VbvBJNdU9zCwSMvxq_z_JRk"
private const val TRAILER_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
class MovieDetail : YouTubeBaseActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var tvDescription: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var youtubePlayer : YouTubePlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        tvTitle = findViewById<TextView>(R.id.textView4)
        tvDescription = findViewById<TextView>(R.id.textView5)
        ratingBar = findViewById<RatingBar>(R.id.ratingBar3)
        youtubePlayer = findViewById<YouTubePlayerView>(R.id.player)

        val movie_desc = getIntent().getStringExtra("movie_overview")
        val movie_title = getIntent().getStringExtra("movie_name")
        val rating = getIntent().getDoubleExtra("rating",0.0)
        val id = getIntent().getIntExtra("movie_id", 0)
        var video_url_thing = "" // default to rick roll cause I am evil

        val client = AsyncHttpClient()
        client.get(TRAILER_URL.format(id), object: JsonHttpResponseHandler(){
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                // do nothing cause I am evil
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                val results = json.jsonObject.getJSONArray("results")
                if(results.length() > 0){
                    val sauce = results.getJSONObject(0)
                    val video_url_thing = sauce.getString("key")
                    initializeYoutube(video_url_thing)
                }else{
                    initializeYoutube("iik25wqIuFo")
                }
            }

        })

        tvDescription.setText(movie_desc)
        ratingBar.rating = rating.toFloat()
        tvTitle.setText(movie_title)
    }

    private fun initializeYoutube(videao: String) {
        youtubePlayer.initialize(YOUTUBE_KEY, object: YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                p1?.cueVideo(videao);
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                // do nothing here since I am lazy
            }

        })
    }
}