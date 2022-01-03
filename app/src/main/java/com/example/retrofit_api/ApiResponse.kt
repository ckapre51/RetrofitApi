package com.example.retrofit_api

data class ApiResponse(
    val error: Boolean,
    val msg: String,
    val data:List<Population>
)