package com.ditateum.githubappsubmission2.service.api

import com.ditateum.githubappsubmission2.BuildConfig
import com.ditateum.githubappsubmission2.data.model.DetailUserResponse
import com.ditateum.githubappsubmission2.data.model.User
import com.ditateum.githubappsubmission2.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

const val API_KEY = BuildConfig.API_KEY

interface Api {
    @GET("search/users")
    @Headers("Authorization: token $API_KEY")
    fun getSearchUser(
        @Query("q") query: String
    ) : Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token $API_KEY")
    fun getDetailUser(
        @Path("username") username: String
    ) : Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $API_KEY")
    fun getFollowers(
        @Path("username") username: String
    ) : Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $API_KEY")
    fun getFollowing(
        @Path("username") username: String
    ) : Call<ArrayList<User>>
}