package com.tools.tooldetection

import okhttp3.MultipartBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FileApi {

    @Multipart
    @POST("")
    suspend fun uploadImage(
        @Part image:MultipartBody.Part
    ):Int

    companion object{
        val instance by lazy {
            Retrofit.Builder()
                .baseUrl("BASE_URL")
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(FileApi::class.java)
        }
    }
}