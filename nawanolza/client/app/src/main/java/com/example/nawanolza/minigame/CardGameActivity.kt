package com.example.nawanolza.minigame

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nawanolza.*
import kotlinx.android.synthetic.main.activity_card_game.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class CardGameActivity : AppCompatActivity(), View.OnClickListener{

    var buttons : ArrayList<ImageButton> = ArrayList();
    lateinit var imageList : List<Int>;
    lateinit var cards : ArrayList<MemoryCard>
    lateinit var resultText : TextView
    lateinit var resetBtn : TextView
    var preCardPostion = -1

    var markerId = 0L
    var memberId = -1
    var characterId = 0L
    var retrofit = Retrofit.Builder()
        .baseUrl("https://k7d103.p.ssafy.io/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var service = retrofit.create(QuestService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_game)

        markerId = intent.getLongExtra("markerId",-1)
        memberId = LoginUtil.getMember(this)!!.id
        characterId = intent.getLongExtra("characterId",-1)

        resultText = findViewById(R.id.result_text)


        init()




        object : CountDownTimer(15000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                var time = (millisUntilFinished / 1000).toInt()
                timer.text = time.toString()

            }

            override fun onFinish() {
                QuestUtil.quizFail(this@CardGameActivity, service, markerId)

                val intent = Intent(this@CardGameActivity, MapActivity::class.java)
//                    intent.putExtra("result",false)
                setResult(RESULT_CANCELED, intent)
                finish()
            }
        }.start()

    }

    fun init() {
        imageList = listOf(
            R.drawable.card_dog,
            R.drawable.card_pig,
            R.drawable.card_rabbit,
            R.drawable.card_cat,
            R.drawable.card_dog,
            R.drawable.card_pig,
            R.drawable.card_rabbit,
            R.drawable.card_cat,
        )

        Collections.shuffle(imageList)

        cards = ArrayList();

        for (i in 0 until 8) {
            var buttonId = "imageBtn" + i
            val resourceId = resources.getIdentifier(buttonId, "id", packageName)
            buttons.add(findViewById(resourceId))

            buttons.get(i).setOnClickListener(this)

            cards.add(MemoryCard(imageList.get(i), false, false))

            buttons.get(i).setImageResource(R.drawable.card_question)

            buttons[i].setAlpha(1.0f)
        }

        resultText.text = ""

    }

    override fun onClick(view: View?) {
        var id = view!!.id
        var position = 0
        if(id == R.id.imageBtn0){
            position = 0
        }else if(id == R.id.imageBtn1){
            position = 1
        }else if(id == R.id.imageBtn2){
            position = 2
        }else if(id == R.id.imageBtn3){
            position = 3
        }else if(id == R.id.imageBtn4){
            position = 4
        }else if(id == R.id.imageBtn5){
            position = 5
        }else if(id == R.id.imageBtn6){
            position = 6
        }else if(id == R.id.imageBtn7){
            position = 7
        }


        updateModel(position)
        updateView(position)
    }


    private fun updateModel(position: Int) {
        val card = cards.get(position)
        if(card.isFaceUp){
            Toast.makeText(this,"이미 뒤집힌 카드입니다.",Toast.LENGTH_SHORT).show()
            return
        }


        if (preCardPostion == -1){
            restoreCard()

            preCardPostion = position
        }else{
            checkForMatch(preCardPostion,position)
            preCardPostion = -1
        }

        cards.get(position).isFaceUp = !card.isFaceUp

    }



    private fun updateView(position: Int) {
        var card = cards.get(position)
        if(card.isFaceUp){
            buttons[position].setImageResource(card.imageId)
        }else{
            buttons[position].setImageResource(R.drawable.card_question)
        }
    }

    private fun restoreCard() {
        for(i in 0 until cards.size){
            if(!cards.get(i).isMatched){
                buttons[i].setImageResource(R.drawable.card_question)

                cards.get(i).isFaceUp = false
            }
        }
    }


    private fun checkForMatch(preCardPostion: Int, position: Int) {

        if(cards.get(preCardPostion).imageId == cards.get(position).imageId){
            resultText.text = "매치 성공"

            cards.get(preCardPostion).isMatched = true
            cards.get(position).isMatched = true

            buttons[preCardPostion].setAlpha(0.1f)
            buttons[position].setAlpha(0.1f)

            checkCompletion()

        }else{
            resultText.text = "매치 실패"
        }
    }

    private fun checkCompletion() {
        var count = 0
        for(i in 0 until cards.size){
            if(cards.get(i).isMatched){
                count++
            }
        }

        if(count==cards.size){
            resultText.text = "미션 성공"


            QuestUtil.quizSuccess(this@CardGameActivity, service, memberId, markerId, characterId)

            val intent = Intent(this@CardGameActivity, MapActivity::class.java)
            intent.putExtra("result",true)
            setResult(RESULT_OK, intent)
            finish()
        }
    }


}