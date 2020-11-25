package com.faircorp.model

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiServices {
    val windowsApiService : WindowApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://faircorp-alexandre-verdet.cleverapps.io:443/api/") /* https://dev-mind.fr/training/android/ */
            .build()
            .create(WindowApiService::class.java)
    }
}