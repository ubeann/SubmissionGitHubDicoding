package com.dicoding.submissiongithub

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    // Variable Setup
    private val title:String = "GitHub User's"
    private val list = ArrayList<User>()
    private lateinit var containerUser:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup title action bar
        val actionBar = supportActionBar
        actionBar?.title = title

        // Setup recycle view
        containerUser = findViewById(R.id.main_list_user)
        containerUser.setHasFixedSize(true)
        list.addAll(listUser)
        showRecyclerList()
    }

    private val listUser: ArrayList<User>
        get() {
            val dataAvatar = resources.obtainTypedArray(R.array.avatar)
            val dataName = resources.getStringArray(R.array.name)
            val dataUsername = resources.getStringArray(R.array.username)
            val dataCompany = resources.getStringArray(R.array.company)
            val dataLocation = resources.getStringArray(R.array.location)
            val dataRepository = resources.getIntArray(R.array.repository)
            val dataFollower = resources.getIntArray(R.array.followers)
            val dataFollowing = resources.getIntArray(R.array.following)
            val listUser = ArrayList<User>()
            for (i in dataName.indices) {
                val user = User(dataAvatar.getResourceId(i, -1), dataName[i], dataUsername[i], dataCompany[i], dataLocation[i], dataRepository[i], dataFollower[i], dataFollowing[i])
                listUser.add(user)
            }
            return listUser
        }

    private fun showRecyclerList() {
        containerUser.layoutManager = LinearLayoutManager(this)
        val userAdapter = UserAdapter(list)
        containerUser.adapter = userAdapter
        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showSelectedUser(data)
            }
        })
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            containerUser.layoutManager = GridLayoutManager(this, 2)
        } else {
            containerUser.layoutManager = LinearLayoutManager(this)
        }
    }

    private fun showSelectedUser(user: User) {
        val intent = Intent(this@MainActivity, SplashActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

}