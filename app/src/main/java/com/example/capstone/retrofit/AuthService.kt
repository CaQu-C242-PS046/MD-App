package com.example.capstone.retrofit
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {

    @POST("/auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("/auth/register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("/auth/change")
    suspend fun changePassword(
        @Header("Authorization") token: String,  // Header untuk token otentikasi
        @Body request: ChangePasswordRequest
    ): Response<ChangePasswordResponse>
}

