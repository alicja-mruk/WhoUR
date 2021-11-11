package com.put.tsm.whour

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.put.tsm.whour.screens.categoriesList.CategoriesListScreen
import com.put.tsm.whour.screens.details.DetailsScreen
import com.put.tsm.whour.ui.theme.WhoUrComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhoUrComposeTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "categories_list_screen"
                ) {
                    composable("categories_list_screen") { CategoriesListScreen(navController = navController) }
                    composable(
                        "details_screen/{categoryId}", arguments = listOf(
                            navArgument("categoryId") {
                                type = NavType.StringType
                            },

                            )
                    ) {
                        val categoryId = remember {
                            it.arguments?.getString("categoryId")
                        } ?: stringResource(id = R.string.no_category_id)
                        DetailsScreen(categoryId = categoryId, navController = navController)
                    }
                }
            }
        }
    }
}