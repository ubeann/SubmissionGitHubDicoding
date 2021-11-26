package com.dicoding.submissiongithub.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissiongithub.DetailActivity
import com.dicoding.submissiongithub.adapter.UserAdapter
import com.dicoding.submissiongithub.databinding.FragmentFollowerBinding
import com.dicoding.submissiongithub.response.UsersResponse
import com.dicoding.submissiongithub.view_model.DetailViewModel

class FollowerFragment(username: String) : Fragment() {
    // Setup variable
    private val user = username
    private lateinit var binding: FragmentFollowerBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Setup view model
        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]
        detailViewModel.getFollowers(user)

        // Inflate the layout for this fragment
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup list users
        binding.listUser.layoutManager = LinearLayoutManager(view.context)
        binding.listUser.setHasFixedSize(true)

        // Observe list follower
        detailViewModel.listFollower.observe(viewLifecycleOwner, {
            showListUser(it, view.context)
        })
    }

    private fun showListUser(listUser: List<UsersResponse>, context: Context) {
        val userAdapter = UserAdapter(listUser)
        binding.listUser.adapter = userAdapter
        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UsersResponse) {
                showSelectedUser(data, context)
            }
        })
    }

    private fun showSelectedUser(user: UsersResponse, context: Context) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_USER, user)
        startActivity(intent)
    }
}