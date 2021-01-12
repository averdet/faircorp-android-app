package com.faircorp.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface WindowApiService {
    @GET("windows")
    fun findAll(): Call<List<WindowDto>>

    @GET("windows/{id}")
    fun findById(@Path("id") id: Long): Call<WindowDto>

    @GET("windows/room/{room_id}")
    fun findByRoomId(@Path("room_id") room_id: Long): Call<List<WindowDto>>

    @GET("windows/building/{building_id}")
    fun findByRoomBuildingId(@Path("building_id") building_id: Long): Call<List<WindowDto>>

    @PUT("windows/{id}/switch")
    fun switchStatus(@Path("id") id: Long): Call<WindowDto>
}