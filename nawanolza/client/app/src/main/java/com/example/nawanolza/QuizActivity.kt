package com.example.nawanolza

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nawanolza.databinding.ActivityQuizBinding
import com.example.nawanolza.retrofit.*
import kotlinx.android.synthetic.main.activity_quiz.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuizActivity : AppCompatActivity() {


    var quizInfo = QuestResponse()
    lateinit var binding: ActivityQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val markerId = intent.getLongExtra("markerId",-1)
        val memberId = LoginUtil.getMember(this)!!.id
        val characterId = intent.getLongExtra("characterId",-1)



        //퀴즈 받아오기
        var retrofit = Retrofit.Builder()
            .baseUrl("https://k7d103.p.ssafy.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var service = retrofit.create(QuestService::class.java)



        service.GetQuiz(mapOf("markerId" to markerId.toString(), "questType" to "0")).enqueue(object: Callback<QuestResponse> {

            override fun onResponse(
                call: Call<QuestResponse>,
                response: Response<QuestResponse>
            ) {
                val body = response.body()

                quizInfo = response.body() ?: QuestResponse()


                println(quizInfo)
                println("=====quiz!!!!======")

                binding.title.text = body?.quiz?.context
            }

            override fun onFailure(call: Call<QuestResponse>, t: Throwable) {
                println(call)
                println(t)
                println("====quiz 에러 ===")
            }

        })



        exitButton.setOnClickListener{
            finish()
        }


        Obutton.setOnClickListener{
            if(quizInfo.quiz.answer==true){
                QuestUtil.quizSuccess(this, service, memberId, markerId, characterId)

            }else{
                QuestUtil.quizFail(this, service, memberId)
            }
        }

        Xbutton.setOnClickListener{
            if(quizInfo.quiz.answer==false){

                QuestUtil.quizSuccess(this, service, memberId, markerId, characterId)
            }else{
                QuestUtil.quizFail(this, service, memberId)
            }
        }

    }


}