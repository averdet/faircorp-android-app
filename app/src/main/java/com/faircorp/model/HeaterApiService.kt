package com.faircorp.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface HeaterApiService {
    @GET("heaters")
    fun findAll(): Call<List<HeaterDto>>

    @GET("heaters/{heater_id}")
    fun findById(@Path("heater_id") heater_id: Long): Call<HeaterDto>

    @PUT("heaters/{heater_id}/switch")
    fun switchStatus(@Path("heater_id") heater_id: Long): Call<HeaterDto>

    @PUT("heaters/{heater_id}/{power}")
    fun setPower(@Path("heater_id") heater_id: Long, @Path("power") power: Long): Call<HeaterDto>
}