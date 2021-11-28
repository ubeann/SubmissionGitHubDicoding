package com.dicoding.submissiongithub

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissiongithub.adapter.UserAdapter
import com.dicoding.submissiongithub.databinding.ActivityMainBinding
import com.dicoding.submissiongithub.response.UsersResponse
import com.dicoding.submissiongithub.view_model.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    // Variable Setup
    private val title:String = "GitHub User's"
    private val mainViewModel by viewModels<MainViewModel>()
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Setup title action bar
        val actionBar = supportActionBar
        actionBar?.title = title

        binding?.let { binding ->
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
                    showSnackBar(binding.root, text)
                }
            })

            // Observe error
            mainViewModel.isError.observe(this, {
                it.getContentIfNotHandled()?.let { _ ->
                    showSnackBar(binding.root, "Terjadi kesalahan, silahkan coba lagi")
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {
                val searchView = item.actionView as SearchView
                val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
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
            R.id.favorite -> {
                val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.settings -> {
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return true
        }
    }

    private fun showListUser(listUser: List<UsersResponse>) {
        val userAdapter = UserAdapter(listUser)
        binding?.mainListUser?.adapter = userAdapter
        mainViewModel.isLoading.postValue(false)
        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UsersResponse) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSnackBar(view: View, text: String) {
        Snackbar.make(
            view,
            text,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun showSelectedUser(user: UsersResponse) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_USER, user)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.GONE
        }
    }
}