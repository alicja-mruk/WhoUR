package com.put.tsm.whour.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.put.tsm.whour.R
import com.put.tsm.whour.ui.screens.login.LoginScreen
import com.put.tsm.whour.ui.screens.categoriesList.CategoriesListScreen
import com.put.tsm.whour.ui.screens.details.DetailsScreen
import com.put.tsm.whour.ui.theme.WhoUrComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            WhoUrComposeTheme() {
                MainAppState()
            }
        }
    }
}