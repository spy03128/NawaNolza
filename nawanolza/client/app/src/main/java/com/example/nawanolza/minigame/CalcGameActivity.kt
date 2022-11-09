package com.example.nawanolza.minigame

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nawanolza.databinding.ActivityCalcGameBinding
import java.util.concurrent.ThreadLocalRandom

class CalcGameActivity : AppCompatActivity() {

    companion object {
        val operatorMap = mapOf<Int, String>(0 to "+", 1 to "-")
    }

    lateinit var binding: ActivityCalcGameBinding
    lateinit var answerButtons: List<Button>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalcGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var firstNumber = (1..99).random()
        var secondNumber = (1..99).random()
        val operationNumber = (0..1).random()

        if (firstNumber < secondNumber) {
            var temp = secondNumber;
            secondNumber = firstNumber;
            firstNumber = temp;
        }

        val answer = if (operationNumber == 0) firstNumber + secondNumber else firstNumber - secondNumber
        val makeAnswerList = makeAnswerList(answer)

        setBindingBtnText(firstNumber, secondNumber, operationNumber)
        makeAnswerBtn(makeAnswerList, answer)
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
                Toast.makeText(this@CalcGameActivity, "정답입니다.", Toast.LENGTH_LONG)
            } else {
                println("오답")
                Toast.makeText(this@CalcGameActivity, "오답입니다.", Toast.LENGTH_LONG)
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