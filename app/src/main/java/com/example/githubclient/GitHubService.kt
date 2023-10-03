package com.example.githubclient

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {
    @GET("/orgs/{org}/repos")
    fun listRepos(@Path("org") org: String): Call<List<Repository>>

    @GET("/repos/{owner}/{repo}")
    fun listDetailRepos(@Path("owner") owner: String,
                        @Path("repo") repo: String): Call<DetailRepository>


}