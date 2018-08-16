package com.example.fauzy.crud

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiInterface {
    @get:Headers("Content-Type: application/json", "idinternal: 027")
    @get:GET("Floor")
    val allFloor: Call<Result>

    @Headers("Content-Type: application/json", "idinternal: 027")
    @POST("Floor/CreateFloor")
    fun createFloor(@Body floor: Floor): Call<ResponseCallback>

    @Headers("Content-Type: application/json", "idinternal: 027")
    @PUT("Floor/UpdateFloor")
    fun updateFloor(@Body floor: Floor): Call<ResponseCallback>

    @Headers("Content-Type: application/json", "idinternal: 027")
    @PUT("Floor/DeleteFloor")
    fun deleteFloor(@Body listFloor: List<Floor>): Call<ResponseCallback>
}
