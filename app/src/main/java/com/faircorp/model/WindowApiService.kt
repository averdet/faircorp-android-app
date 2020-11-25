package com.faircorp.model

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface WindowApiService {
    @GET("windows")
    fun findAll(): Call<List<WindowDto>>

    @GET("windows/{id}")
    fun findById(@Path("id") id: Long): Call<WindowDto>

    @PUT("windows/{id}/switch")
    fun updateWindow(@Path("id") id: Long, @Body window: WindowDto): Call<WindowDto>

    @PUT("windows/{id}/switch")
    fun switchWindow(@Path("id") id: Long)
}