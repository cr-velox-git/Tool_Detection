package com.tools.tooldetection

import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException

class FileRepository {

    suspend fun uploadImage(file: File): Int {
        return try {
            FileApi.instance.uploadImage(
                image = MultipartBody.Part.createFormData(
                    "image",
                    file.name,
                    file.asRequestBody()
                )
            )
        } catch (e: IOException) {
            e.printStackTrace()
            -1
        } catch (e: HttpException) {
            e.printStackTrace()
            -1
        }

    }

}