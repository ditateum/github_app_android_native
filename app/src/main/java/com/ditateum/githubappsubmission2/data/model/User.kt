package com.ditateum.githubappsubmission2.data.model

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    @SerializedName("login")
    val username: String,
    val avatar_url: String,
)
