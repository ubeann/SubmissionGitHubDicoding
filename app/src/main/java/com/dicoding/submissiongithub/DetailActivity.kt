package com.dicoding.submissiongithub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class DetailActivity : AppCompatActivity() {
    // Setup Variable
    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Receive data
        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User

        // Setup Widget Variable
        val avatar:ImageView = findViewById(R.id.user_avatar)
        val name:TextView = findViewById(R.id.user_name)
        val username:TextView = findViewById(R.id.user_username)
        val company:TextView = findViewById(R.id.user_company)
        val location:TextView = findViewById(R.id.user_location)
        val repository:TextView = findViewById(R.id.user_repository)
        val following:TextView = findViewById(R.id.user_following)
        val followers:TextView = findViewById(R.id.user_followers)

        // Set data to activity
        avatar.setImageResource(user.avatar)
        name.text = user.name
        username.text = user.username
        company.text = user.company
        location.text = user.location
        repository.text = user.repository
        following.text = user.following
        followers.text = user.follower

    }
}