package com.example.githubclient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object Config {
    var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}
