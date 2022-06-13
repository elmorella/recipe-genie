package com.example.recipegenie.model

import com.example.recipegenie.BuildConfig
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RetrofitClient {

    @Headers(
        BuildConfig.HOST,
        BuildConfig.KEY
    )
    @GET("recipes/list")
    suspend fun getSearchResults(
        @Query("from") offset: Int, @Query("size") limit: Int,
        @Query("tags") tags: String, @Query("q") search: String
    ): Response<RecipeResults>

    companion object {
        var BASE_URL = "https://tasty.p.rapidapi.com/"

        fun create(): RetrofitClient {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(RetrofitClient::class.java)
        }
    }
}
