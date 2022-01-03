package com.example.retrofit_api

data class Population(
    val city: String,
    val country: String,
    val populationCounts:List<PopulationCount>
)