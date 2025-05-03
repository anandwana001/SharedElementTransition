package com.androidengineers.startinghearts.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.androidengineers.startinghearts.ui.theme.avenirFont
import com.androidengineers.startinghearts.ui.theme.textColor

@Composable
fun ExpandableText(text: String) {
    var expanded by remember { mutableStateOf(false) }
    var isOverflowed by remember { mutableStateOf(false) }
    var truncatedText by remember { mutableStateOf(text) }
    val maxCharCount = 100

    LaunchedEffect(text) {
        if (text.length > maxCharCount) {
            truncatedText = text.take(maxCharCount) + "... More"
            isOverflowed = true
        } else {
            isOverflowed = false
        }
    }

    Row(
        modifier = Modifier.clickable { if (isOverflowed) expanded = true }
    ) {
        Text(
            text = if (expanded) text else truncatedText,
            maxLines = if (expanded) Int.MAX_VALUE else 3,
            style = TextStyle(
                color = textColor,
                fontSize = 18.sp,
                fontFamily = avenirFont,
                fontWeight = FontWeight.Normal
            )
        )

        if (!expanded && isOverflowed) {
            Text(
                text = " More",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 18.sp,
                    fontFamily = avenirFont,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }


}
