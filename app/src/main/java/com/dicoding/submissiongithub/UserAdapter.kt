package com.dicoding.submissiongithub

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UserAdapter(private val listDetailUserResponse: List<DetailUserResponse?>) : RecyclerView.Adapter<UserAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    class ListViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        var userAvatar: ImageView = itemView.findViewById(R.id.user_avatar)
        var userName:TextView = itemView.findViewById(R.id.user_name)
        var userUsername:TextView = itemView.findViewById(R.id.user_username)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.list_item_user, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        Glide.with(holder.userAvatar.context)
            .load(listDetailUserResponse[position]?.avatarUrl)
            .into(holder.userAvatar)
        holder.userName.text = listDetailUserResponse[position]?.name
        holder.userUsername.text = listDetailUserResponse[position]?.login
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listDetailUserResponse[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = listDetailUserResponse.size

    interface OnItemClickCallback {
        fun onItemClicked(data: DetailUserResponse?)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}