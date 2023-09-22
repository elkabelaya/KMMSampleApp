package com.elkabelaya.kmmsampleapp.android

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.elkabelaya.kmmsampleapp.screens.SecondScreenComponent


@Composable
fun SecondScreenUI(component: SecondScreenComponent) {
    val state by component.state.subscribeAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding()
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.Yellow)
    ) {
        Text("passed value is: ${state.value}")
        Button(onClick = component::onBackClick) {
            Text("Go back")
        }
    }

}