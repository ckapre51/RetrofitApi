package com.example.retrofit_api

import retrofit2.Response
import retrofit2.http.GET

interface populationapi {
    @GET("countries/population/cities")
    suspend fun getResponse():Response<ApiResponse>



}