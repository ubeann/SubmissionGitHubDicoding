package com.dicoding.submissiongithub

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.submissiongithub.adapter.SectionsPagerAdapter
import com.dicoding.submissiongithub.databinding.ActivityDetailBinding
import com.dicoding.submissiongithub.factory.FavoriteViewModelFactory
import com.dicoding.submissiongithub.response.DetailUserResponse
import com.dicoding.submissiongithub.response.UsersResponse
import com.dicoding.submissiongithub.view_model.DetailViewModel
import com.dicoding.submissiongithub.view_model.FavoriteViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    // Variable Setup
    private var binding: ActivityDetailBinding? = null
    private val detailViewModel by viewModels<DetailViewModel>()
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Set variable
        favoriteViewModel = obtainViewModel(this@DetailActivity)

        // Setup title action bar
        val actionBar = supportActionBar

        // Receive data
        val user = intent.getParcelableExtra<UsersResponse>(EXTRA_USER)

        binding?.let { binding->
            // Implement user data
            user?.let { userData ->
                // Set title
                actionBar?.title = userData.login

                // Observer floating button
                favoriteViewModel.isFavoriteUser(userData.login).observe(this, {
                    whichFloatingButton(it)
                })

                // Initiate view model
                detailViewModel.getDetailUser(userData.login)

                // Observe notification
                detailViewModel.notificationText.observe(this, {
                    it.getContentIfNotHandled()?.let { text ->
                        showSnackBar(binding.root, text)
                    }
                })

                // Observe error
                detailViewModel.isError.observe(this, {
                    it.getContentIfNotHandled()?.let { _ ->
                        showSnackBar(binding.root, "Terjadi kesalahan, silahkan coba lagi")
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

                // Set floating button onclick listener
                with(binding) {
                    btnFavorite.setOnClickListener {
                        favoriteViewModel.insert(userData)
                    }
                    btnDelete.setOnClickListener {
                        favoriteViewModel.delete(userData)
                    }
                }

                // Observe notification favorite
                favoriteViewModel.notificationText.observe(this, {
                    it.getContentIfNotHandled()?.let { text ->
                        showSnackBar(binding.root, text)
                    }
                })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun setDetailUser(user: DetailUserResponse) {
        // Set data
        binding?.let { binding ->
            Glide.with(binding.userAvatar.context)
                .load(user.avatarUrl)
                .placeholder(R.drawable.ic_baseline_downloading_24)
                .error(R.drawable.ic_baseline_error_24)
                .into(binding.userAvatar)
            with(binding) {
                userName.text = user.name
                userUsername.text = user.login
                userCompany.text = user.company
                userLocation.text = user.location
                userRepository.text = user.publicRepos.toString()
                userFollowing.text = user.following.toString()
                userFollowers.text = user.followers.toString()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.GONE
        }
    }

    private fun showSnackBar(view: View, text: String) {
        Snackbar.make(
            view,
            text,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun obtainViewModel(activity: AppCompatActivity) : FavoriteViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }


    private fun whichFloatingButton(isDelete: Boolean) {
        binding?.btnDelete?.visibility = if (isDelete) View.VISIBLE else View.GONE
        binding?.btnFavorite?.visibility = if (isDelete) View.GONE else View.VISIBLE
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