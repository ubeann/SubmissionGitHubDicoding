package com.dicoding.submissiongithub

import com.google.gson.annotations.SerializedName

data class UsersResponse(

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("login")
	val login: String
)
