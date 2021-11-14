package com.put.tsm.whour.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.put.tsm.whour.R
import com.put.tsm.whour.ui.screens.splashScreen.SplashScreen
import com.put.tsm.whour.ui.screens.categoriesList.CategoriesListScreen
import com.put.tsm.whour.ui.screens.details.DetailsScreen
import com.put.tsm.whour.ui.screens.login.LoginScreen

object RouteDestinations {
    const val CATEGORIES_LIST = "categories_list_screen"
    const val LOGIN = "login_screen"
    const val SPLASH= "splash_screen"
    const val DETAILS = "details_screen"
    const val CATEGORY_ID_KEY = "categoryId"
}

@Composable
fun MainAppState() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = RouteDestinations.SPLASH
    ) {
        composable(RouteDestinations.SPLASH) {
            SplashScreen(
                navController = navController
            )
        }
        composable(RouteDestinations.LOGIN) { LoginScreen(navController = navController) }
        composable(RouteDestinations.CATEGORIES_LIST) {
            CategoriesListScreen(
                navController = navController
            )
        }
        composable(
            "${RouteDestinations.DETAILS}/{${RouteDestinations.CATEGORY_ID_KEY}}",
            arguments = listOf(
                navArgument(RouteDestinations.CATEGORY_ID_KEY) {
                    type = NavType.StringType
                },
                )
        ) {
            val categoryId = remember {
                it.arguments?.getString(RouteDestinations.CATEGORY_ID_KEY)
            } ?: stringResource(id = R.string.no_category_id)
            DetailsScreen(categoryId = categoryId, navController = navController)
        }
    }
}