package com.example.nawanolza

class MarkerImageUtil {

    companion object {
        val charMap = mapOf<String, Int>(
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
            "b1" to R.drawable.b1,
            "b2" to R.drawable.b2,
            "b3" to R.drawable.b3,
            "b4" to R.drawable.b4,
            "b5" to R.drawable.b5,
            "b6" to R.drawable.b6,
            "b7" to R.drawable.b7,
            "b8" to R.drawable.b8,
            "b9" to R.drawable.b9,
            "b10" to R.drawable.b10,
            "b11" to R.drawable.b11,
            "b12" to R.drawable.b12,
            "b13" to R.drawable.b13,
            "b14" to R.drawable.b14,
            "b15" to R.drawable.b15,
            "b16" to R.drawable.b16,
            "b17" to R.drawable.b17,
            "b18" to R.drawable.b18,
            "b19" to R.drawable.b19,
            "b20" to R.drawable.b20,
            "b21" to R.drawable.b21,
            "b22" to R.drawable.b22,
            "b23" to R.drawable.b23,
            "b24" to R.drawable.b24,
            "b25" to R.drawable.b25,
            "b26" to R.drawable.b26,
            "b27" to R.drawable.b27,
            "b28" to R.drawable.b28,
            "b29" to R.drawable.b29,
            "b30" to R.drawable.b30
        )

        fun getImage(characterId : Long): Int {
            return charMap.get("a" + characterId) as Int
        }
        fun getDarkImage(characterId : Long): Int {
            return charMap.get("b" + characterId) as Int
        }
    }

}