package com.elkabelaya.kmmsampleapp.android

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.elkabelaya.kmmsampleapp.SecondScreenComponent


@Composable
fun SecondScreenUI(secondScreenComponent: SecondScreenComponent) {
    val state by secondScreenComponent.state.subscribeAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(Color.Yellow)
    ) {
        BackHandler(true){}
        Spacer(modifier = Modifier.weight(1F))

        Text(text = "${state.value}")

        Spacer(modifier = Modifier.weight(1F))
        Button(onClick = secondScreenComponent::onBackClick) {
            Text("Go back")
        }
    }

}