package com.example.capstone.retrofit
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("/auth/register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

}

