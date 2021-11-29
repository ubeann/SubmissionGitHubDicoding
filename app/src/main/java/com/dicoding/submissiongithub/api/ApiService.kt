package com.dicoding.submissiongithub.api

import com.dicoding.submissiongithub.BuildConfig
import com.dicoding.submissiongithub.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("/users")
    fun getUsers(): Call<List<UsersResponse>>

    @GET("/users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("/users/{username}/followers")
    fun getFollowersUser(
        @Path("username") username: String
    ): Call<List<UsersResponse>>

    @GET("/users/{username}/following")
    fun getFollowingUser(
        @Path("username") username: String
    ): Call<List<UsersResponse>>

    @GET("/search/users")
    fun searchUser(
        @Query("q") username: String
    ): Call<SearchResponse>
}