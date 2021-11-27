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

    @GET("/users/{username}/followers")
    @Headers("Authorization: token $personalAccessToken")
    fun getFollowersUser(
        @Path("username") username: String
    ): Call<List<UsersResponse>>

    @GET("/users/{username}/following")
    @Headers("Authorization: token $personalAccessToken")
    fun getFollowingUser(
        @Path("username") username: String
    ): Call<List<UsersResponse>>

    @GET("/search/users")
    @Headers("Authorization: token $personalAccessToken")
    fun searchUser(
        @Query("q") username: String
    ): Call<SearchResponse>

    companion object {
        private const val personalAccessToken: String = "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"
    }
}