package com.dicoding.submissiongithub

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.submissiongithub.adapter.SectionsPagerAdapter
import com.dicoding.submissiongithub.databinding.ActivityDetailBinding
import com.dicoding.submissiongithub.response.DetailUserResponse
import com.dicoding.submissiongithub.response.UsersResponse
import com.dicoding.submissiongithub.view_model.DetailViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    // Variable Setup
    private val detailViewModel by viewModels<DetailViewModel>()
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup title action bar
        val actionBar = supportActionBar

        // Receive data
        val user = intent.getParcelableExtra<UsersResponse>(EXTRA_USER)

        // Implement user data
        user?.let { userData ->
            // Set title
            actionBar?.title = userData.login

            // Initiate view model
            detailViewModel.getDetailUser(userData.login)

            // Observe notification
            detailViewModel.notificationText.observe(this, {
                it.getContentIfNotHandled()?.let { text ->
                    Snackbar.make(
                        binding.root,
                        text,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            })

            // Observe loading
            detailViewModel.isLoading.observe(this, {
                showLoading(it)
            })

            // Observe detail user
            detailViewModel.detailUser.observe(this, {
                setDetailUser(it)
            })

            // Viewpager2
            val sectionsPagerAdapter = SectionsPagerAdapter(this, userData.login)
            binding.viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }

    private fun setDetailUser(user: DetailUserResponse) {
        // Set data
        Glide.with(binding.userAvatar.context)
            .load(user.avatarUrl)
            .into(binding.userAvatar)
        binding.userName.text = user.name
        binding.userUsername.text = user.login
        binding.userCompany.text = user.company
        binding.userLocation.text = user.location
        binding.userRepository.text = user.publicRepos.toString()
        binding.userFollowing.text = user.following.toString()
        binding.userFollowers.text = user.followers.toString()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    // Setup Variable
    companion object {
        const val EXTRA_USER = "extra_user"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.user_following,
            R.string.user_followers
        )
    }
}