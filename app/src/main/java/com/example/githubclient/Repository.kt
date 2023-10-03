package com.example.githubclient

import com.google.gson.annotations.SerializedName


data class Repository(
    val name: String,
    val description: String?,
    @SerializedName("owner")
    val owner: Owner
) {
    val formattedDescription: String
        get() = description ?: "No description"

}
