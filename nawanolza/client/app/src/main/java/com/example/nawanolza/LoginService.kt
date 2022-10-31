package com.example.nawanolza

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService{
    @POST("auth/kakao/callback")
    fun Login(
        @Body data: Map<String, Any>
    ) : Call<Map<String, String>> //아웃풋 정의
}