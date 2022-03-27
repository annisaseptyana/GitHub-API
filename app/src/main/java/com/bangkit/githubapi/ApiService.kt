package com.bangkit.githubapi

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: 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")
    fun getUser (
        @Query("q") q: String
    ): Call<UserSearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: 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")
    fun getDetail (
        @Path("username") username: String
    ): Call<UserDetailResponse>
}