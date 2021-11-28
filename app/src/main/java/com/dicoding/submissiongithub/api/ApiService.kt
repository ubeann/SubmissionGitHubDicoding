package com.dicoding.submissiongithub.api

import com.dicoding.submissiongithub.BuildConfig
import com.dicoding.submissiongithub.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("/users")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getUsers(): Call<List<UsersResponse>>

    @GET("/users/{username}")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("/users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getFollowersUser(
        @Path("username") username: String
    ): Call<List<UsersResponse>>

    @GET("/users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getFollowingUser(
        @Path("username") username: String
    ): Call<List<UsersResponse>>

    @GET("/search/users")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun searchUser(
        @Query("q") username: String
    ): Call<SearchResponse>
}