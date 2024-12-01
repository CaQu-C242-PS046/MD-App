package com.example.capstone.retrofit

import retrofit2.http.GET
import retrofit2.http.Path

interface BaseService {
    // Mendapatkan daftar nama soft skills
    @GET("softSkills/all")
    suspend fun getSoftSkillNames(): ResponseSoftSkills

    // Mendapatkan detail soft skill berdasarkan nama
    @GET("softSkills/{name}")
    suspend fun getSoftSkillDetail(@Path("name") name: String): SoftSkillDetail
}
