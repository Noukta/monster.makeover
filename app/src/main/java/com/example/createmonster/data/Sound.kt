package com.example.createmonster.data

import androidx.annotation.RawRes

data class Sound(
    @RawRes val resId: Int,
    var soundId: Int? = null,
)
