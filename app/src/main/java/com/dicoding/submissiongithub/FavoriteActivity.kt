package com.dicoding.submissiongithub

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissiongithub.adapter.UserAdapter
import com.dicoding.submissiongithub.databinding.ActivityFavoriteBinding
import com.dicoding.submissiongithub.factory.FavoriteViewModelFactory
import com.dicoding.submissiongithub.response.UsersResponse
import com.dicoding.submissiongithub.view_model.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    // Variable Setup
    private val title:String = "Favorite Users"
    private var binding: ActivityFavoriteBinding? = null
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Setup title action bar
        val actionBar = supportActionBar
        actionBar?.title = title

        // Set variable
        favoriteViewModel = obtainViewModel(this@FavoriteActivity)

        binding?.let { binding ->
            // Setup list users
            binding.listUser.layoutManager = LinearLayoutManager(this)
            binding.listUser.setHasFixedSize(true)
            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                binding.listUser.layoutManager = GridLayoutManager(this, 2)
            } else {
                binding.listUser.layoutManager = LinearLayoutManager(this)
            }

            // Observe favorite user
            favoriteViewModel.getAllFavoriteUser().observe(this, {
                showListUser(it)
                showNotFound(it.isEmpty())
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity) : FavoriteViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

    private fun showListUser(listUser: List<UsersResponse>) {
        val userAdapter = UserAdapter(listUser)
        binding?.listUser?.adapter = userAdapter
        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UsersResponse) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: UsersResponse) {
        val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_USER, user)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun showNotFound(isNotFound: Boolean) {
        binding?.notFound?.text = if (isNotFound) getString(R.string.not_found, "favorite user") else "favorite user"
        binding?.notFound?.visibility = if (isNotFound) View.VISIBLE else View.GONE
    }
}