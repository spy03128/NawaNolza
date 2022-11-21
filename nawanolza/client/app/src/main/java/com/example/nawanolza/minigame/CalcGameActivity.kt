package com.example.nawanolza.minigame

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.nawanolza.login.LoginUtil
import com.example.nawanolza.character.MapActivity
import com.example.nawanolza.quest.QuestService
import com.example.nawanolza.quest.QuestUtil
import com.example.nawanolza.databinding.ActivityCalcGameBinding
import kotlinx.android.synthetic.main.activity_card_game.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CalcGameActivity : AppCompatActivity() {

    companion object {
        val operatorMap = mapOf<Int, String>(0 to "+", 1 to "-")
    }

    lateinit var binding: ActivityCalcGameBinding
    lateinit var answerButtons: List<Button>


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
        binding = ActivityCalcGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var firstNumber = (1..99).random()
        var secondNumber = (1..99).random()
        val operationNumber = (0..1).random()


        markerId = intent.getLongExtra("markerId",-1)
        memberId = LoginUtil.getMember(this)!!.id
        characterId = intent.getLongExtra("characterId",-1)

        if (firstNumber < secondNumber) {
            var temp = secondNumber;
            secondNumber = firstNumber;
            firstNumber = temp;
        }

        val answer = if (operationNumber == 0) firstNumber + secondNumber else firstNumber - secondNumber
        val makeAnswerList = makeAnswerList(answer)

        setBindingBtnText(firstNumber, secondNumber, operationNumber)
        makeAnswerBtn(makeAnswerList, answer)



        object : CountDownTimer(7000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                var time = (millisUntilFinished / 1000).toInt()
                timer.text = time.toString()

            }

            override fun onFinish() {
                QuestUtil.quizFail(this@CalcGameActivity, service, markerId)

                val intent = Intent(this@CalcGameActivity, MapActivity::class.java)
//                    intent.putExtra("result",false)
                setResult(RESULT_CANCELED, intent)
                finish()
            }
        }.start()
    }

    private fun setBindingBtnText(
        firstNumber: Int,
        secondNumber: Int,
        operationNumber: Int
    ) {
        binding.number1.text = firstNumber.toString()
        binding.number2.text = secondNumber.toString()
        binding.operation.text = operatorMap.get(operationNumber)
    }

    private fun makeAnswerBtn(
        makeAnswerList: List<Int>,
        answer: Int
    ) {
        answerButtons = listOf(binding.answer1, binding.answer2, binding.answer3, binding.answer4)
        for (i in 0..3) answerButtons.get(i).text = makeAnswerList.get(i).toString()
        for (button in answerButtons) {
            answerBtnSetOnclickEvent(button, answer)
        }
    }

    private fun answerBtnSetOnclickEvent(button: Button, answer: Int) {
        button.setOnClickListener {
            println("버튼 클릭")
            println(button.text)
            println(answer)
            if (button.text.equals(answer.toString())) {
                println("정답")
                QuestUtil.quizSuccess(this@CalcGameActivity, service, memberId, markerId, characterId)

                val intent = Intent(this@CalcGameActivity, MapActivity::class.java)
                intent.putExtra("result",true)
                setResult(RESULT_OK, intent)
                finish()
            } else {
                println("오답")
                QuestUtil.quizFail(this@CalcGameActivity, service, markerId)

                val intent = Intent(this@CalcGameActivity, MapActivity::class.java)
//                    intent.putExtra("result",false)
                setResult(RESULT_CANCELED, intent)
                finish()
            }
        }
    }

    private fun makeAnswerList(answer: Int) : List<Int> {
        val list = HashSet<Int>()
        list.add(answer)
        while (list.size < 4) {
            val makeNum = if (list.size % 2 == 0) answer + (1..answer).random() else (1..answer).random()
            list.add(makeNum)
        }
        val result = ArrayList<Int>()
        for (i in list) result.add(i)
        return result
    }
}