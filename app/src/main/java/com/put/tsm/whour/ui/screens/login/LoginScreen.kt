package com.put.tsm.whour.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.put.tsm.whour.R
import com.put.tsm.whour.data.models.Gender
import com.put.tsm.whour.ui.composables.ComposeMenu
import com.put.tsm.whour.ui.theme.Roboto
import kotlinx.coroutines.flow.collect

@Composable
fun LoginScreen(navController: NavController) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            LoginView(navController = navController)
        }
    }
}

@Composable
fun LoginView(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
    val genderItems = Gender.values().map {
        it.toString()
    }

    var genderExpanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    var name by rememberSaveable { mutableStateOf("") }
    var age by rememberSaveable { mutableStateOf("") }
    val isLoading by remember { viewModel.isLoading }

    val eventsFlow =
        viewModel.eventsFlow.collectAsState(initial = LoginViewModel.LoginUiEvent.Idle)

    LaunchedEffect(eventsFlow) {
        viewModel.eventsFlow.collect { event ->
            when (event) {
                is LoginViewModel.LoginUiEvent.LoginSuccess -> navController.navigate("categories_list_screen")
                is LoginViewModel.LoginUiEvent.Idle -> Unit
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.login_label),
            fontFamily = Roboto,
            fontSize = 32.sp,
            fontWeight = Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp)
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            value = name,
            onValueChange = {
                name = it
            },
            placeholder = { Text(text = stringResource(id = R.string.name_label)) },
            label = { Text(text = stringResource(id = R.string.name_label)) }
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            value = age,
            onValueChange = {
                age = it
            },
            placeholder = { Text(text = stringResource(id = R.string.age_label)) },
            label = { Text(text = stringResource(id = R.string.age_label)) }
        )
        Column {
            Text(
                text = stringResource(id = R.string.gender_label),
                fontFamily = Roboto,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp)
            )
            Box(
                modifier = Modifier
                    .padding(bottom = 8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                ComposeMenu(
                    menuItems = genderItems,
                    menuExpandedState = genderExpanded,
                    selectedIndex = selectedIndex,
                    updateMenuExpandStatus = {
                        genderExpanded = true
                    },
                    onDismissMenuView = {
                        genderExpanded = false
                    },
                    onMenuItemClick = { index ->
                        selectedIndex = index
                        genderExpanded = false
                    }
                )
            }
        }
    }
    Button(
        onClick = {
            viewModel.login(
                name = name,
                age = age.toInt(),
                gender = Gender.values()[selectedIndex]
            )
        },
        contentPadding = PaddingValues(
            start = 20.dp,
            top = 12.dp,
            end = 20.dp,
            bottom = 12.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        elevation = ButtonDefaults.elevation(),
        enabled = name.isNotEmpty() && age.isNotEmpty()
    ) {
        Text(text = stringResource(R.string.login_label))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colors.primary)
            }
        }
    }
}