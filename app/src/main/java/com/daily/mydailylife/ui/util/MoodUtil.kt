package com.daily.mydailylife.ui.util

import com.daily.mydailylife.R

fun getMoodImages() : List<Int> {
    return listOf(
        R.mipmap.a1,
        R.mipmap.a2,
        R.mipmap.a3,
        R.mipmap.a4,
        R.mipmap.a5,
        R.mipmap.a6,
        R.mipmap.a7,
        R.mipmap.a8,
        R.mipmap.a9,
        R.mipmap.a10,
        R.mipmap.a11,
        R.mipmap.a12,
        R.mipmap.a13,
        R.mipmap.a14,
        R.mipmap.a15,
        R.mipmap.a16,
        R.mipmap.a17,
        R.mipmap.a18,
        R.mipmap.a19,
        R.mipmap.a20,
        R.mipmap.a21,
        R.mipmap.a22,
        R.mipmap.a23,
        R.mipmap.a24,
        R.mipmap.a25,
        R.mipmap.a26,
        R.mipmap.a27,
        R.mipmap.a28,
        R.mipmap.a29,
        R.mipmap.a30,
        R.mipmap.a31,
        R.mipmap.a32,
        R.mipmap.a33,
        R.mipmap.a34,
        R.mipmap.a35,
        R.mipmap.a36,
    )
}

fun getMoodByIndex(index: Int) : Int{
    return getMoodImages()[index]
}