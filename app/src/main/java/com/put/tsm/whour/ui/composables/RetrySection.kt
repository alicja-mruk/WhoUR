package com.put.tsm.whour.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.put.tsm.whour.R

@Composable
fun RetrySection(
    error: Exception?
) {
    val errorText = error?.message ?: stringResource(R.string.general_error_message)
    Column {
        Text(errorText, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
    }
}