package com.example.nawanolza.minigame

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nawanolza.*
import com.example.nawanolza.databinding.ActivityNumberPuzzleGameBinding
import kotlinx.android.synthetic.main.activity_card_game.*
import java.util.*

class NumberPuzzleGameActivity : AppCompatActivity() {

    val buttons : ArrayList<ImageButton> = ArrayList()
    val numbers: ArrayList<Int> = ArrayList()
    lateinit var flags: HashMap<Int, Boolean>
    lateinit var binding: ActivityNumberPuzzleGameBinding
    var nextNumber: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNumberPuzzleGameBinding.inflate(layoutInflater)
        nextNumber = 1
        setContentView(binding.root)
        makeNumbers()
        setButtons()
        createTimer().start()
    }

    private fun createTimer() = object : CountDownTimer(15000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            var time = (millisUntilFinished / 1000).toInt()
            timer.text = time.toString()
        }

        override fun onFinish() {
    //                QuestUtil.quizFail(this@, service, markerId)
            val intent = Intent(this@NumberPuzzleGameActivity, MapActivity::class.java)
            intent.putExtra("result", false)
            setResult(RESULT_CANCELED, intent)
            finish()
        }
    }


    private fun makeNumbers() {
        flags = HashMap<Int, Boolean>()
        for (i in (1..16)) {
            numbers.add(i)
            flags.put(i-1, false)
        }
        Collections.shuffle(numbers)
    }

    private fun setButtons() {
        for (i in 0 until 16) {
            var buttonId = "imageBtn" + i
            val resourceId = resources.getIdentifier(buttonId, "id", packageName)
            buttons.add(findViewById(resourceId))
            buttons.get(i).setImageResource(NumberCardImageUtil.getImage(numbers.get(i)))
            buttons.get(i).setOnClickListener {
                if(flags.get(i) == false) {
                    if(numbers.get(i).equals(nextNumber)) {
                        nextNumber++
                        buttons.get(i).setImageResource(R.drawable.card_question)
                        flags.put(i, true)
                        if (nextNumber == 17)   gameClear()
                    } else {
                        Toast.makeText(this, "올바르지 않은 숫자 선택!", Toast.LENGTH_SHORT)
                    }
                }
            }
        }
    }

    private fun gameClear() {
        val intent = Intent(this@NumberPuzzleGameActivity, MapActivity::class.java)
        intent.putExtra("result", true)
        setResult(RESULT_OK, intent)
        finish()
    }
}