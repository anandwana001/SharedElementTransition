package com.androidengineers.startinghearts.data

import androidx.compose.ui.graphics.Color

data class CardItem(
    val name: String,
    val price: String,
    val imageUrl: String,
    val backgroundColor: Color,
    val description: String
)

