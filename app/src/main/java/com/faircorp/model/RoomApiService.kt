package com.faircorp.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RoomApiService {
    @GET("rooms")
    fun findAll(): Call<List<RoomDto>>

    @GET("rooms/{id}")
    fun findById(@Path("id") id: Long): Call<RoomDto>

    @GET("rooms/building/{building_id}")
    fun findByBuildingId(@Path("building_id") building_id: Long): Call<List<RoomDto>>
}