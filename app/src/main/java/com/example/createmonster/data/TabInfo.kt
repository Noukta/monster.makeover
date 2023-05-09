package com.example.createmonster.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class TabInfo(
    @StringRes val tabName: Int,
    @DrawableRes val iconResId: Int,
    @DrawableRes val selectedIconResId: Int,
    val contentSize: Int = 0
)
