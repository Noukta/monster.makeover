package com.monster.makeover.data

import androidx.annotation.DrawableRes
import androidx.compose.ui.geometry.Offset

data class MonsterState(
    @DrawableRes val head: Int? = null,
    @DrawableRes val eye: Int? = null,
    @DrawableRes val mouth: Int? = null,
    @DrawableRes val acc: Int? = null,
    @DrawableRes val body: Int? = null,

    val eyeOffset: Offset = Offset.Zero,
    val mouthOffset: Offset = Offset.Zero,
    val accOffset: Offset = Offset.Zero
    )