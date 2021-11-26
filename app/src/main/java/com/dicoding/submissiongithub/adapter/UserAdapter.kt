package com.dicoding.submissiongithub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submissiongithub.R
import com.dicoding.submissiongithub.response.UsersResponse

class UserAdapter(private val listUserResponse: List<UsersResponse>) : RecyclerView.Adapter<UserAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    class ListViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        var userAvatar: ImageView = itemView.findViewById(R.id.user_avatar)
        var userUsername:TextView = itemView.findViewById(R.id.user_username)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder = ListViewHolder(LayoutInflater.from(parent.context).inflate(
        R.layout.list_item_user, parent, false))

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        Glide.with(holder.userAvatar.context)
            .load(listUserResponse[position].avatarUrl)
            .into(holder.userAvatar)
        holder.userUsername.text = listUserResponse[position].login
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUserResponse[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = listUserResponse.size

    interface OnItemClickCallback {
        fun onItemClicked(data: UsersResponse)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}