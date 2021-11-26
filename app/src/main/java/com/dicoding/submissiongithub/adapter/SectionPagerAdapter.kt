package com.dicoding.submissiongithub.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.submissiongithub.fragment.*

class SectionsPagerAdapter(activity: AppCompatActivity, username: String) : FragmentStateAdapter(activity) {
    // Setup variable
    private val user = username

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowingFragment(user)
            1 -> fragment = FollowerFragment(user)
        }
        return fragment as Fragment
    }
}