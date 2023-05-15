package com.monster.makeover.data

import androidx.annotation.RawRes

data class Sound(
    @RawRes val resId: Int,
    var soundId: Int? = null,
)
