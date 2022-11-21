package com.example.nawanolza.quest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.nawanolza.character.MapActivity
import com.example.nawanolza.databinding.ActivityQuizBinding
import com.example.nawanolza.login.LoginUtil
import com.example.nawanolza.retrofit.*
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.activity_quiz.timer
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
        val quizInfo : QuestResponse = GsonBuilder().create().fromJson(intent.getStringExtra("quizInfo"), QuestResponse::class.java)

        println("===============quizactivity quizinfo")
        println(quizInfo)

        //퀴즈 받아오기
        var retrofit = Retrofit.Builder()
            .baseUrl("https://k7d103.p.ssafy.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var service = retrofit.create(QuestService::class.java)

        binding.title.text = quizInfo.quiz.context


        object : CountDownTimer(10000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                var time = (millisUntilFinished / 1000).toInt()
                timer.text = time.toString()

            }

            override fun onFinish() {
                QuestUtil.quizFail(this@QuizActivity, service, markerId)

                val intent = Intent(this@QuizActivity, MapActivity::class.java)
//                    intent.putExtra("result",false)
                setResult(RESULT_CANCELED, intent)
                finish()
            }
        }.start()

        Obutton.setOnClickListener{
            if(quizInfo.quiz.answer==true){
                QuestUtil.quizSuccess(this, service, memberId, markerId, characterId)

            }else{
                QuestUtil.quizFail(this, service, markerId)
            }
        }

        Xbutton.setOnClickListener{
            if(quizInfo.quiz.answer==false){

                QuestUtil.quizSuccess(this, service, memberId, markerId, characterId)
            }else{
                QuestUtil.quizFail(this, service, markerId)
            }
        }

    }


}