package com.put.tsm.whour.ui.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.put.tsm.whour.ui.composables.RetrySection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.put.tsm.whour.R
import com.put.tsm.whour.ui.utils.loadInterstitial
import com.put.tsm.whour.ui.utils.showInterstitial
import com.put.tsm.whour.ui.theme.RobotoCondensed
import kotlinx.coroutines.flow.collect

@Composable
fun DetailsScreen(
    categoryId: String,
    navController: NavHostController
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            DetailsView(navController = navController)
        }
    }
}

@Composable
fun DetailsView(
    navController: NavHostController,
    viewModel: DetailsViewModel = hiltViewModel()
) {

    val eventsFlow =
        viewModel.eventsFlow.collectAsState(initial = DetailsViewModel.DetailsUiEvent.Idle)

    var showModal by remember { mutableStateOf(false) }
    var winnerType by remember { mutableStateOf("") }
    val context = LocalContext.current



    LaunchedEffect(eventsFlow) {
        viewModel.eventsFlow.collect { event ->
            when (event) {
                is DetailsViewModel.DetailsUiEvent.QuizFinished -> {
                    context.loadInterstitial()
                    context.showInterstitial()
                    showModal = true
                    winnerType = event.winnerType
                }
                is DetailsViewModel.DetailsUiEvent.Idle -> Unit
            }
        }
    }

    val index0Item by remember { viewModel.index0Item }
    val index1Item by remember { viewModel.index1Item }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }
    val isEmpty by remember { viewModel.isEmpty }

    val index0EntryType = index0Item?.type ?: ""
    val index0EntryDescription = index0Item?.description ?: ""
    val index0EntryImageUrl = index0Item?.imageUrl ?: ""

    val index1EntryType = index1Item?.type ?: ""
    val index1EntryDescription = index1Item?.description ?: ""
    val index1EntryImageUrl = index1Item?.imageUrl ?: ""

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (isEmpty) {
            Text(
                text = stringResource(R.string.empty_list), fontFamily = RobotoCondensed,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.wrapContentWidth()
            )
        } else {
            Box {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        QuizFinishedDialog(
                            showModal = showModal,
                            winnerType = winnerType,
                            onClick = {
                                showModal = false
                                navController.popBackStack()
                            }
                        )

                        DetailsEntry(
                            type = index0EntryType,
                            description = index0EntryDescription,
                            imageUrl = index0EntryImageUrl,
                            onClick = { viewModel.goNext(type = index0EntryType) }
                        )
                    }
                    Divider(color = Color.Gray, thickness = 1.dp)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        DetailsEntry(
                            type = index1EntryType,
                            description = index1EntryDescription,
                            imageUrl = index1EntryImageUrl,
                            onClick = { viewModel.goNext(type = index1EntryType) }
                        )
                    }
                }
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
        loadError?.let {
            RetrySection(error = it)
        }
    }
}

enum class Types(val key: String) {
    CAT("cat"),
    DOG("dog")
}

@Composable
fun getDescription(winnerType: String): String {
    return when (winnerType) {
        Types.CAT.key -> stringResource(id = R.string.cat_lover_description)
        Types.DOG.key -> stringResource(id = R.string.dog_lover_description)
        else -> stringResource(id = R.string.unknown_type)
    }
}

@Composable
fun QuizFinishedDialog(showModal: Boolean, winnerType: String, onClick: () -> Unit) {
    val description = getDescription(winnerType)

    if (showModal) {
        Dialog(
            onDismissRequest = { onClick() },
            DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
        ) {
            Surface(modifier = Modifier.size(300.dp), shape = RoundedCornerShape(8.dp)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = description, fontFamily = RobotoCondensed,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.wrapContentWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun DetailsEntry(type: String, description: String, imageUrl: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberImagePainter(
                data = imageUrl,
                builder = {
                    transformations(CircleCropTransformation())
                }
            ),
            contentDescription = description,
            modifier = Modifier.size(128.dp)
        )
        Column(
            modifier = Modifier
                .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Type: $type",
                fontFamily = RobotoCondensed,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.wrapContentWidth()
            )
            Text(
                text = "Description: $description",
                fontFamily = RobotoCondensed,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.wrapContentWidth()
            )
        }
    }
}