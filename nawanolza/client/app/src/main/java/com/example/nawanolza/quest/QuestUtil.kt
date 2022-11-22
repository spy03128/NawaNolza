package com.example.nawanolza.quest

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.nawanolza.character.MapActivity
import com.example.nawanolza.retrofit.QuestFailResponse
import com.example.nawanolza.retrofit.QuestSuccessResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestUtil {
    companion object{
        fun quizFail(context: Activity, service: QuestService, markerId: Long) {

            service.PostFail(mapOf("markerId" to markerId.toString()))
                .enqueue(object : Callback<QuestFailResponse> {

                    override fun onResponse(
                        call: Call<QuestFailResponse>,
                        response: Response<QuestFailResponse>
                    ) {
                        val body = response.body()
                        println("====quiz fail 성공 ===")
                        val intent = Intent(context, MapActivity::class.java)
//                    intent.putExtra("result",false)
                        context.setResult(AppCompatActivity.RESULT_CANCELED, intent)
                        context.finish()
                    }

                    override fun onFailure(call: Call<QuestFailResponse>, t: Throwable) {
                        println(call)
                        println(t)
                        println("====quiz fail 에러 ===")
                    }

                })
        }

        fun quizSuccess(
            context: Activity,
            service: QuestService,
            memberId: Int,
            markerId: Long,
            characterId: Long
        ) {


            service.PostSuccess(
                mapOf(
                    "memberId" to memberId.toString(),
                    "markerId" to markerId.toString(),
                    "characterId" to characterId.toString()
                )
            ).enqueue(object : Callback<QuestSuccessResponse> {

                override fun onResponse(
                    call: Call<QuestSuccessResponse>,
                    response: Response<QuestSuccessResponse>
                ) {
                    val body = response.body()
                    println("====quiz success 성공 ===")
                    val intent = Intent(context, MapActivity::class.java)
                    intent.putExtra("result",true)
                    context.setResult(AppCompatActivity.RESULT_OK, intent)
                    context.finish()
                }

                override fun onFailure(call: Call<QuestSuccessResponse>, t: Throwable) {
                    println(call)
                    println(t)
                    println("====quiz success 에러 ===")
                }

            })
        }

    }
}