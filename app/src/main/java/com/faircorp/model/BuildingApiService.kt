package com.faircorp.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BuildingApiService {
    @GET("buildings")
    fun findAll(): Call<List<BuildingDto>>

    @GET("buildings/{building_id}")
    fun findById(@Path("building_id") building_id: Long): Call<BuildingDto>

}