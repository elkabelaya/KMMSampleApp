package com.elkabelaya.kmmsampleapp.android

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.elkabelaya.kmmsampleapp.FirstScreenComponent


@Composable
fun FirstScreenUI(FirstScreenComponent: FirstScreenComponent) {
    val state by FirstScreenComponent.state.subscribeAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1F))

        Text(text = "${state.count}")

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = FirstScreenComponent::onIncrease) {
            Text(text = "+")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = FirstScreenComponent::onDecrease) {
            Text(text = "-")
        }

        Spacer(modifier = Modifier.weight(1F))
    }
}