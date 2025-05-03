package com.androidengineers.startinghearts.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.ArcMode
import androidx.compose.animation.core.ExperimentalAnimationSpecApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.androidengineers.startinghearts.R
import com.androidengineers.startinghearts.data.CardItem
import com.androidengineers.startinghearts.data.sizes
import com.androidengineers.startinghearts.ui.components.ExpandableText
import com.androidengineers.startinghearts.ui.components.KEY_BACKGROUND
import com.androidengineers.startinghearts.ui.components.KEY_SHOE_DIVIDER
import com.androidengineers.startinghearts.ui.components.KEY_SHOE_IMAGE
import com.androidengineers.startinghearts.ui.components.KEY_SHOE_PRICE
import com.androidengineers.startinghearts.ui.components.KEY_SHOE_TITLE
import com.androidengineers.startinghearts.ui.theme.avalonFont
import com.androidengineers.startinghearts.ui.theme.avenirFont

@OptIn(
    ExperimentalSharedTransitionApi::class, ExperimentalAnimationSpecApi::class,
    ExperimentalLayoutApi::class
)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    index: Int,
    shoe: CardItem,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onClick: () -> Unit = {}
) {
    with(sharedTransitionScope) {

        val visibleState = remember { MutableTransitionState(false) }

        LaunchedEffect(Unit) {
            visibleState.targetState = true
        }

        val boundsTransform = BoundsTransform { initialBounds, targetBounds ->
            keyframes {
                durationMillis = 1000
                initialBounds at 0 using ArcMode.ArcBelow using FastOutSlowInEasing
                targetBounds at 1000
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .sharedElement(
                    rememberSharedContentState(key = "$KEY_BACKGROUND-$index"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = boundsTransform
                )
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                val circleDiameter = size.width * 1.4f
                val circleRadius = circleDiameter / 2

                val offsetX = circleRadius / 2
                val offsetY = size.height / 2

                withTransform({
                    scale(
                        -1f,
                        1f,
                        pivot = Offset(size.width / 2, size.height / 2)
                    ) // Flip horizontally
                }) {
                    drawCircle(
                        color = shoe.backgroundColor,
                        radius = circleRadius,
                        center = Offset(offsetX, offsetY - 80)
                    )
                }
            }
            Column(modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text(
                    text = shoe.name,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .sharedElement(
                            rememberSharedContentState(key = "${KEY_SHOE_TITLE}-$index"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = boundsTransform
                        )
                )
                Text(
                    text = shoe.price,
                    color = Color.White,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .sharedElement(
                            rememberSharedContentState(key = "${KEY_SHOE_PRICE}-$index"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = boundsTransform
                        )
                )
            }

        }

        AnimatedVisibility(
            visibleState = visibleState,
            enter = slideIn(
                initialOffset = { IntOffset(0, it.height) },
                animationSpec = spring(
                    stiffness = Spring.StiffnessLow
                )
            ) + fadeIn(
                initialAlpha = 0f,
                animationSpec = tween(durationMillis = 2000)
            ),
            exit = slideOut(
                targetOffset = { IntOffset(0, it.height) },
                animationSpec = spring(
                    stiffness = Spring.StiffnessLow
                )
            ) + fadeOut(
                animationSpec = tween(durationMillis = 2000)
            ),
        ) {
            val scrollState = rememberScrollState()
            Column(
                modifier = modifier
                    .padding(16.dp)
                    .verticalScroll(scrollState)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {
                        onClick()
                        visibleState.targetState = false
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.secondary,
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.secondary,
                        )
                    }
                }
                Image(
                    painter = painterResource(id = R.drawable.cardshoe),
                    contentDescription = "KD13 EP",
                    modifier = Modifier
                        .aspectRatio(1.5f)
                        .sharedElement(
                            rememberSharedContentState(key = "$KEY_SHOE_IMAGE-$index"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = boundsTransform
                        )
                )
                Spacer(modifier = Modifier.height(16.dp))
                ExpandableText(shoe.description)
                Spacer(modifier = Modifier.height(16.dp))
                var selectedColor by remember { mutableStateOf(0) }
                val previewImages = listOf(
                    R.drawable.shoe,
                    R.drawable.shoe,
                    R.drawable.shoe
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    itemsIndexed(previewImages) { index, image ->
                        Image(
                            painter = painterResource(id = image),
                            contentDescription = "Shoe Preview",
                            modifier = Modifier
                                .width(100.dp)
                                .height(90.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.LightGray.copy(alpha = 0.2f))
                                .clickable {
                                    selectedColor = index
                                }
                                .then(
                                    if (index == selectedColor) {
                                        Modifier.border(
                                            2.dp,
                                            MaterialTheme.colorScheme.secondary,
                                            RoundedCornerShape(16.dp)
                                        )
                                    } else {
                                        Modifier
                                    }
                                ),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Select Size",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 18.sp,
                        fontFamily = avenirFont,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                var selectedSize by remember { mutableStateOf(0) }

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    sizes.forEachIndexed { index, size ->
                        SizeButton(
                            text = size.label,
                            isSelected = selectedSize == index,
                            isDisabled = size.isDisabled
                        ) {
                            selectedSize = index
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = stringResource(R.string.add_to_bag),
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = avenirFont,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}


@Composable
fun SizeButton(text: String, isSelected: Boolean, isDisabled: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = when {
                    isSelected -> Color.Black
                    isDisabled -> Color.LightGray
                    else -> Color.Gray
                },
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = if (isDisabled) Color.LightGray.copy(alpha = 0.2f) else Color.Transparent
            )
            .clickable(enabled = !isDisabled, onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            color = when {
                isSelected -> Color.Black
                isDisabled -> Color.Gray
                else -> Color.DarkGray
            },
            fontSize = 16.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}


