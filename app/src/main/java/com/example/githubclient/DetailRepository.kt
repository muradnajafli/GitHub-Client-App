package com.example.githubclient

import com.google.gson.annotations.SerializedName

data class DetailRepository(
    val name: String,
    val description: String,
    val parent: Parent?,
    @SerializedName("forks_count")
    val forksCount: Int,
    @SerializedName("watchers_count")
    val watchersCount: Int,
    @SerializedName("open_issues_count")
    val issuesCount: Int
)
