package com.dicoding.submissiongithub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class FavoriteActivity : AppCompatActivity() {
    // Variable Setup
    private val title:String = "Favorite Users"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        // Setup title action bar
        val actionBar = supportActionBar
        actionBar?.title = title
    }
}