package com.example.nawanolza

class MarkerImageUtil {

    companion object {
        val charMap = mapOf<String, Any>(
            "a1" to R.drawable.a1,
            "a2" to R.drawable.a2,
            "a3" to R.drawable.a3,
            "a4" to R.drawable.a4,
            "a5" to R.drawable.a5,
            "a6" to R.drawable.a6,
            "a7" to R.drawable.a7,
            "a8" to R.drawable.a8,
            "a9" to R.drawable.a9,
            "a10" to R.drawable.a10,
            "a11" to R.drawable.a11,
            "a12" to R.drawable.a12,
            "a13" to R.drawable.a13,
            "a14" to R.drawable.a14,
            "a15" to R.drawable.a15,
            "a16" to R.drawable.a16,
            "a17" to R.drawable.a17,
            "a18" to R.drawable.a18,
            "a19" to R.drawable.a19,
            "a20" to R.drawable.a20,
            "a21" to R.drawable.a21,
            "a22" to R.drawable.a22,
            "a23" to R.drawable.a23,
            "a24" to R.drawable.a24,
            "a25" to R.drawable.a25,
            "a26" to R.drawable.a26,
            "a27" to R.drawable.a27,
            "a28" to R.drawable.a28,
            "a29" to R.drawable.a29,
            "a30" to R.drawable.a30,


        )

        fun getImage(characterId : Long): Any? {
            return charMap.get("a" + characterId)
        }
    }

}