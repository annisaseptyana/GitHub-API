package com.bangkit.githubapi

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: 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")
    fun getUser (
        @Query("q") id: String
    ): Call<UserSearchResponse>
}