package com.androidengineers.startinghearts.ui.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.androidengineers.startinghearts.R
import com.androidengineers.startinghearts.data.CardItem
import com.androidengineers.startinghearts.data.ShoeItem
import com.androidengineers.startinghearts.data.chipItems
import com.androidengineers.startinghearts.data.shoes
import com.androidengineers.startinghearts.ui.components.ShoesListView
import com.androidengineers.startinghearts.ui.theme.avalonFont
import com.androidengineers.startinghearts.ui.theme.avenirFont
import com.androidengineers.startinghearts.ui.theme.broder
import com.androidengineers.startinghearts.ui.theme.gothammediumFont
import com.androidengineers.startinghearts.ui.theme.unselectedBackground

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    shoeList: List<CardItem>,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onClick: (Int) -> Unit,
) {
    SharedTransitionLayout {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                TitleText()
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                HorizontalTabs()
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                ShoesListView(
                    shoesList = shoeList,
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope
                ) { index ->
                    onClick(index)
                }
            }
            item {
                Text(
                    text = "${shoes.size} OPTIONS",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 12.sp,
                        fontFamily = avenirFont
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(shoes) { shoe ->
                ListOfShoes(shoe)
            }
        }
    }
}

@Composable
private fun TitleText() {
    Text(
        text = "Shoes",
        style = TextStyle(
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 26.sp,
            fontFamily = avalonFont
        ),
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Composable
private fun HorizontalTabs() {
    var selectedIndex by remember { mutableIntStateOf(0) }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(chipItems) { index, chipItem ->
            FilterChip(
                modifier = Modifier,
                selected = selectedIndex == index,
                onClick = { selectedIndex = index },
                label = { Text(text = chipItem, modifier = Modifier.padding(horizontal = 4.dp)) },
                shape = RoundedCornerShape(18.dp),
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = unselectedBackground,
                    labelColor = MaterialTheme.colorScheme.secondary,
                    selectedContainerColor = MaterialTheme.colorScheme.secondary,
                    selectedLabelColor = Color.White
                ),
                border = FilterChipDefaults.filterChipBorder(
                    borderWidth = 1.dp,
                    borderColor = broder,
                    selectedBorderColor = MaterialTheme.colorScheme.secondary,
                    selected = selectedIndex == index,
                    enabled = true
                )
            )
        }
    }
}

@Composable
private fun ListOfShoes(shoe: ShoeItem) {
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp),
        thickness = 2.dp,
        color = unselectedBackground
    )
    ShoeItemView(shoe = shoe)
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp),
        thickness = 2.dp,
        color = unselectedBackground
    )
}

@Composable
private fun ShoeItemView(shoe: ShoeItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.shoe),
            contentDescription = "Shoe image",
            modifier = Modifier
                .fillMaxWidth(0.45f)
                .aspectRatio(5f / 3f)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Column {
            Text(
                text = shoe.name,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 16.sp,
                    fontFamily = gothammediumFont
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = shoe.price,
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                maxLines = 1
            )
        }
    }
}
