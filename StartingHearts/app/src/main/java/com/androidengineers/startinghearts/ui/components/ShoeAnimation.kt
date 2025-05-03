package com.androidengineers.startinghearts.ui.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.ArcMode
import androidx.compose.animation.core.ExperimentalAnimationSpecApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.androidengineers.startinghearts.R
import com.androidengineers.startinghearts.data.CardItem


const val KEY_BACKGROUND = "KEY_BACKGROUND"
const val KEY_SHOE_IMAGE = "KEY_SHOE_IMAGE"
const val KEY_SHOE_TITLE = "KEY_SHOE_TITLE"
const val KEY_SHOE_PRICE = "KEY_SHOE_PRICE"
const val KEY_SHOE_DIVIDER = "KEY_SHOE_DIVIDER"

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun LazyItemScope.ShoesListView(
    shoesList: List<CardItem>,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onClick: (Int) -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { shoesList.size })

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.padding(vertical = 8.dp),
        pageSize = PageSize.Fixed(280.dp)
    ) { currentPage ->
        val currentPageOffset =
            (pagerState.currentPage + pagerState.currentPageOffsetFraction - currentPage).coerceIn(
                -1f,
                1f
            )
        val shoeRotationZ = lerp(0f, -30f, 1f - currentPageOffset)

        val shoeTranslationX = lerp(-150f, 0f, 1f - currentPageOffset) // Invert translation
        val shoesAlpha = lerp(1f, 1f, 1f - currentPageOffset) // Keep alpha the same
        val shoesOffsetX = lerp(-20f, 0f, 1f - currentPageOffset) // Invert offset direction

        ShoeItemView(
            shoe = shoesList[currentPage],
            shoeRotationZ = shoeRotationZ,
            shoeTranslationX = shoeTranslationX,
            shoesAlpha = shoesAlpha,
            shoesOffsetX = shoesOffsetX,
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = animatedVisibilityScope,
            currentPage = currentPage
        ) {
            onClick(currentPage)
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalAnimationSpecApi::class)
@Composable
fun LazyItemScope.ShoeItemView(
    shoe: CardItem,
    shoeRotationZ: Float,
    shoeTranslationX: Float,
    shoesAlpha: Float,
    shoesOffsetX: Float,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    currentPage: Int,
    onClick: () -> Unit
) {
    with(sharedTransitionScope) {
        val boundsTransform = BoundsTransform { initialBounds, targetBounds ->
            keyframes {
                durationMillis = 1000
                initialBounds at 0 using ArcMode.ArcBelow using FastOutSlowInEasing
                targetBounds at 1000
            }
        }

        Box(
            modifier = Modifier
                .width(280.dp)
                .padding(20.dp)
                .aspectRatio(0.84f)
                .clickable { onClick() }
                .sharedElement(
                    rememberSharedContentState(key = "${KEY_BACKGROUND}-$currentPage"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = boundsTransform
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
                    .background(shoe.backgroundColor)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(18.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = shoe.name,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState(key = "${KEY_SHOE_TITLE}-$currentPage"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = boundsTransform
                        )
                )
                Text(
                    text = shoe.price,
                    color = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState(key = "${KEY_SHOE_PRICE}-$currentPage"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = boundsTransform
                        )
                )
                VerticalDivider(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .width(2.dp),
                    color = Color.White.copy(alpha = 0.3f)
                )
            }

            Image(
                painter = painterResource(id = R.drawable.cardshoe),
                contentDescription = "Shoe Image",
                modifier =
                Modifier
                    .fillParentMaxWidth()
                    .zIndex(1f)
                    .aspectRatio(1.5f)
                    .graphicsLayer {
                        rotationZ = shoeRotationZ
                        translationX = shoeTranslationX
                        alpha = shoesAlpha
                        scaleX = 0.8f
                        scaleY = 0.8f
                    }
                    .offset(x = shoesOffsetX.dp, y = 50.dp)
                    .sharedElement(
                        rememberSharedContentState(key = "${KEY_SHOE_IMAGE}-$currentPage"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = boundsTransform
                    )
            )
        }
    }
}


fun lerp(start: Float, stop: Float, amount: Float): Float {
    return start + (stop - start) * amount
}