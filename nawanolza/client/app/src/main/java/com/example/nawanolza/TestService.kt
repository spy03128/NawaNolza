package com.example.nawanolza

import retrofit2.http.Field
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TestService{
    @POST("test")
    fun Test(
        @Body data: TestRequest
    ) : Call<TestResponse> //아웃풋 정의
}