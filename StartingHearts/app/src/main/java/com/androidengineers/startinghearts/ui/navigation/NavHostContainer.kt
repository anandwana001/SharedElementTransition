package com.androidengineers.startinghearts.ui.navigation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.androidengineers.startinghearts.data.shoeList
import com.androidengineers.startinghearts.ui.screens.DetailScreen
import com.androidengineers.startinghearts.ui.screens.HomeScreen

@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }
val LocalNavAnimatedVisibilityScope = compositionLocalOf<AnimatedVisibilityScope?> { null }

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavHostContainer(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
) {
    SharedTransitionLayout {
        CompositionLocalProvider(
            LocalSharedTransitionScope provides this
        ) {
            NavHost(
                navController = navController,
                startDestination = Category.HOME.route,
                modifier = modifier,
                builder = {
                    composable(Category.HOME.route) {
                        HomeScreen(
                            modifier = modifier
                                .padding(paddingValues)
                                .consumeWindowInsets(paddingValues),
                            shoeList = shoeList,
                            sharedTransitionScope = this@SharedTransitionLayout,
                            animatedVisibilityScope = this@composable
                        ) { index ->
                            navController.navigate("detail/${index}")
                        }
                    }
                    composable(
                        "detail/{index}",
                        arguments = listOf(navArgument("index") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val index = backStackEntry.arguments?.getInt("index")
                        val shoeDetails = shoeList.getOrNull(index ?: 0)
                        shoeDetails?.let {
                            DetailScreen(
                                modifier = modifier.padding(paddingValues),
                                index = index ?: 0,
                                shoe = it,
                                sharedTransitionScope = this@SharedTransitionLayout,
                                animatedVisibilityScope = this@composable
                            ) {
                                navController.popBackStack()
                            }
                        }
                    }
                }
            )
        }
    }
}