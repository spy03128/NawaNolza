package com.example.nawanolza.quest.minigame

import com.example.nawanolza.R

class NumberCardImageUtil {
    companion object {
        private val charMap = mapOf<Int, Int>(
            1 to R.drawable.number_card1,
            2 to R.drawable.number_card2,
            3 to R.drawable.number_card3,
            4 to R.drawable.number_card4,
            5 to R.drawable.number_card5,
            6 to R.drawable.number_card6,
            7 to R.drawable.number_card7,
            8 to R.drawable.number_card8,
            9 to R.drawable.number_card9,
            10 to R.drawable.number_card10,
            11 to R.drawable.number_card11,
            12 to R.drawable.number_card12,
            13 to R.drawable.number_card13,
            14 to R.drawable.number_card14,
            15 to R.drawable.number_card15,
            16 to R.drawable.number_card16,
        )

        fun getImage(number : Int): Int {
            return charMap.get(number) as Int
        }
    }
}