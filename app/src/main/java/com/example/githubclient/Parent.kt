package com.example.githubclient

import com.google.gson.annotations.SerializedName

data class Parent(
    @SerializedName("full_name")
    val fullName: String
)
