package com.androidengineers.startinghearts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import com.androidengineers.startinghearts.ui.navigation.NavHostContainer
import com.androidengineers.startinghearts.ui.theme.StartingHeartsTheme
import com.androidengineers.startinghearts.ui.theme.avalonFont

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StartingHeartsTheme {
                Scaffold { paddingValues ->
                   val navController = rememberNavController()
                    NavHostContainer(
                        navController = navController,
                        paddingValues = paddingValues
                    )
                }
            }
        }
    }
}

/*@Composable
fun MyShoeScreen(modifier: Modifier = Modifier) {
    var data = remember {
        mutableStateOf("Android")
    }
    Column(modifier = modifier.padding(
        start = 8.dp
    )) {
        HeadingText()
        Spacer(modifier = Modifier
            .height(8.dp))
        TextField(
            value = data.value,
            onValueChange = { newData ->
                data.value = newData
                println("== $newData")
            }
        )
        Button(
            onClick = {

            }
        ) { }
        LazyRow(
            modifier = Modifier.fillMaxWidth().background(Color.Green),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(listOf<String>("asdasda", "asdasda", "asdasda",
                "asdasda","asdasda","asdasda","asdasda","asdasda","asdasda","asdasda","asdasda","asdasda",
                "asdasda","asdasda","asdasda","asdasda","asdasda","asdasda","asdasda","asdasda","asdasda",
                "asdasda","asdasda","asdasda","asdasda","asdasda","asdasda","asdasda","asdasda",
                )) {
                index, item ->
                Text(text = item,
                    modifier = Modifier.background(Color.Red))
            }
        }
        val pagerState = rememberPagerState { 100 }
        HorizontalPager(
            pagerState
        ) { index ->
            Column(
                modifier = Modifier.fillMaxWidth()
                    .background(Color.Cyan)
                    .padding(16.dp)
            ) {
                Card(
                   modifier = Modifier.size(200.dp)
                ) {
                    HeadingText()
                }
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(20) {
                HeadingText()
            }
        }
    }
}

// 1. how can you create dummy list of shoes
// 2. design data class
// 3. interfaces?

@Composable
fun HeadingText() {
    Text(
        text = "Shoe",
        fontFamily = avalonFont
    )
}

@Preview(showBackground = true,
    showSystemUi = true)
@Composable
fun MyShoePreview() {
    MyShoeScreen()
}*/



