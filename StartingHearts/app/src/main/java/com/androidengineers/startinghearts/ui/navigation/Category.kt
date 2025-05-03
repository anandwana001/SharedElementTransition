package com.androidengineers.startinghearts.ui.navigation

import androidx.annotation.StringRes
import com.androidengineers.startinghearts.R

enum class Category(
    @StringRes val titleId: Int,
    val route: String
) {
    HOME(R.string.home_title, "home"),
    DETAIL(R.string.detail_title, "detail"),
}