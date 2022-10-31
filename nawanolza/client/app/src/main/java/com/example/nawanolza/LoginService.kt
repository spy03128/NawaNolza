package com.example.nawanolza

import retrofit2.http.Field
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService{
    @POST("auth/kakao/callback")
    fun Login(
        @Body data: LoginRequest
    ) : Call<TestResponse> //아웃풋 정의
}