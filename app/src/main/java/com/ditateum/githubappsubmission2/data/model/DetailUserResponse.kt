package com.ditateum.githubappsubmission2.data.model

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(
    val id: Int,
    @SerializedName("login")
    val username: String,
    val name: String,
    val company: String,
    val location: String,
    @SerializedName("public_repos")
    val repository: Int,
    val followers: Int,
    val following: Int,
    val avatar_url: String,
    val followers_url: String,
    val following_url: String,
)
