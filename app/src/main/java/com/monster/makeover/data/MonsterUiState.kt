package com.monster.makeover.data

import androidx.annotation.DrawableRes
import androidx.compose.ui.geometry.Offset

data class MonsterUiState(
    @DrawableRes
    val head: Int? = null,
    val eye: Int? = null,
    val mouth: Int? = null,
    val acc: Int? = null,
    val body: Int? = null,

    val eyeOffset: Offset = Offset(0F, -200F),
    val mouthOffset: Offset = Offset(0F, 0F),
    val accOffset: Offset = Offset(0F, -400F),

    )