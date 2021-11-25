package com.dicoding.submissiongithub

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("/users")
    @Headers("Authorization: token $personalAccessToken")
    fun getUsers(): Call<List<UsersResponse>>

    @GET("/users/{username}")
    @Headers("Authorization: token $personalAccessToken")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    companion object {
        private const val personalAccessToken: String = "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"
    }
}