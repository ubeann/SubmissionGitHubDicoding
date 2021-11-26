package com.dicoding.submissiongithub.api

import com.dicoding.submissiongithub.response.*
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

    @GET("/search/users")
    @Headers("Authorization: token $personalAccessToken")
    fun searchUser(
        @Query("q") username: String
    ): Call<SearchResponse>

    companion object {
        private const val personalAccessToken: String = "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"
    }
}