package com.bangkit.githubapi

import com.bangkit.githubapi.response.UserDetailResponse
import com.bangkit.githubapi.response.UserFollowersResponse
import com.bangkit.githubapi.response.UserFollowingResponse
import com.bangkit.githubapi.response.UserSearchResponse
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

    @GET("users/{username}/followers")
    @Headers("Authorization: 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")
    fun getFollowersList (
        @Path("username") username: String
    ): Call<List<UserFollowersResponse>>

    @GET("users/{username}/following")
    @Headers("Authorization: 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")
    fun getFollowingList (
        @Path("username") username: String
    ): Call<List<UserFollowingResponse>>
}