package com.example.nawanolza.minigame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.ImageView
import com.example.nawanolza.*
import kotlinx.android.synthetic.main.activity_game_boom.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class GameBoomActivity : AppCompatActivity() {
    var score = 0
    var imageArray = ArrayList<ImageView>()
    var handler : Handler = Handler()
    var runnable : Runnable = Runnable {  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_boom)

        score = 0

        imageArray = arrayListOf(imageView, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9)

        hideImages()

        val markerId = intent.getLongExtra("markerId",-1)
        val memberId = LoginUtil.getMember(this)!!.id
        val characterId = intent.getLongExtra("characterId",-1)



        //퀴즈 받아오기
        var retrofit = Retrofit.Builder()
            .baseUrl("https://k7d103.p.ssafy.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var service = retrofit.create(QuestService::class.java)

        object : CountDownTimer(10000, 1000) {
            override fun onFinish() {
                if(score>=5){
                    println("캐릭터 획득 성공")

                    QuestUtil.quizSuccess(
                        this@GameBoomActivity,
                        service,
                        memberId,
                        markerId,
                        characterId
                    )

                    val intent = Intent(this@GameBoomActivity, MapActivity::class.java)
                    intent.putExtra("result",true)
                    setResult(RESULT_OK, intent)
                    finish()
                }else{
                    println("캐릭터 획득 실패")

                    QuestUtil.quizFail(this@GameBoomActivity, service, markerId)

                    val intent = Intent(this@GameBoomActivity, MapActivity::class.java)
//                    intent.putExtra("result",false)
                    setResult(RESULT_CANCELED, intent)
                    finish()
                }

                timeText.text = "Time over"
                handler.removeCallbacks(runnable)
                for (image in imageArray)
                    image.visibility = View.INVISIBLE
            }
            override fun onTick(p0: Long) {
                timeText.text =  (p0 / 1000).toString()
            }
        }.start()


    }

    fun hideImages() {
        runnable = object : Runnable {
            override fun run() {

                for (image in imageArray) {
                    image.visibility = View.INVISIBLE
                }

                val random = Random()
                val index = random.nextInt(8 - 0)
                imageArray[index].setImageResource(R.drawable.boom_off)
                imageArray[index].visibility = View.VISIBLE

                handler.postDelayed(runnable, 700)

            }
        }
        handler.post(runnable)
    }

    fun increaseScore(view: View) {
        score++

        for (image in imageArray) {
            image.setImageResource(R.drawable.boom_on)
        }

        scoreText.text = "Score: " + score
    }
}