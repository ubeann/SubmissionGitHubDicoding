package com.dicoding.submissiongithub

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissiongithub.adapter.UserAdapter
import com.dicoding.submissiongithub.databinding.ActivityMainBinding
import com.dicoding.submissiongithub.view_model.MainViewModel
import com.dicoding.submissiongithub.response.UsersResponse
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    // Variable Setup
    private val title:String = "GitHub User's"
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup title action bar
        val actionBar = supportActionBar
        actionBar?.title = title

        // Setup list users
        binding.mainListUser.layoutManager = LinearLayoutManager(this)
        binding.mainListUser.setHasFixedSize(true)
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.mainListUser.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.mainListUser.layoutManager = LinearLayoutManager(this)
        }

        // Observe list user
        mainViewModel.listUser.observe(this, {
            showListUser(it)
        })

        // Observe loading
        mainViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        // Observe notification
        mainViewModel.notificationText.observe(this, {
            it.getContentIfNotHandled()?.let { text ->
                Snackbar.make(
                    binding.root,
                    text,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.searchUser(query, getString(R.string.notification_search), getString(R.string.notification_search_not_found))
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    private fun showListUser(listUser: List<UsersResponse>) {
        val userAdapter = UserAdapter(listUser)
        binding.mainListUser.adapter = userAdapter
        mainViewModel.isLoading.postValue(false)
        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UsersResponse) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: UsersResponse) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_USER, user)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}