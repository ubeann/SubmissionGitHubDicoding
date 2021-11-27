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
import com.dicoding.submissiongithub.databinding.FragmentFollowingBinding
import com.dicoding.submissiongithub.response.UsersResponse
import com.dicoding.submissiongithub.view_model.DetailViewModel

class FollowingFragment : Fragment() {
    // Setup variable
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Setup view model
        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]
        arguments?.getString("username")?.let {
            detailViewModel.getFollowing(it)
        }

        // Inflate the layout for this fragment
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup list users
        binding.listUser.layoutManager = LinearLayoutManager(view.context)
        binding.listUser.setHasFixedSize(true)

        // Observe list following
        detailViewModel.listFollowing.observe(viewLifecycleOwner, {
            showListUser(it, view.context)
        })

        // Observe loading
        detailViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })
    }

    fun newInstance(username: String) : FollowingFragment {
        // Setup data
        val bundle = Bundle()
        bundle.putString("username", username)

        // Set fragment
        val fragment = FollowingFragment()
        fragment.arguments = bundle
        return fragment
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
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