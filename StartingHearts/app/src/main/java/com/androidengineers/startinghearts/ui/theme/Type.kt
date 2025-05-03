package com.androidengineers.startinghearts.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.androidengineers.startinghearts.R

val avalonFont = FontFamily(
    Font(R.font.avalon, FontWeight.Normal)
)
val avenirFont = FontFamily(
    Font(R.font.avenirmedium)
)
val gothammediumFont = FontFamily(
    Font(R.font.gothammedium)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = avalonFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)