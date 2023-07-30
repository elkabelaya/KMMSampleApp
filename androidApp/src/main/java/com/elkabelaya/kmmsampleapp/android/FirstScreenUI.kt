package com.elkabelaya.kmmsampleapp.android

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
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.elkabelaya.kmmsampleapp.FirstScreenComponent


@Composable
fun FirstScreenUI(firstScreenComponent: FirstScreenComponent) {
    val state by firstScreenComponent.state.subscribeAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =  Modifier
            .padding()
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Text(text = "${state.count}")
        TextField(value = state.text,
            onValueChange = firstScreenComponent::onChangeText
        )
        Button(onClick = firstScreenComponent::onIncrease) {
            Text(text = "+")
        }
        Button(onClick = firstScreenComponent::onDecrease) {
            Text(text = "-")
        }
        Button(onClick = {firstScreenComponent.onNextScreen(state.text)}) {
            Text(text = "onNext")
        }
        Button(onClick = firstScreenComponent::onShowAlertClick) {
            Text("ShowAlert")
        }

        if (state.showAlert) {
            AlertDialog(
                onDismissRequest = firstScreenComponent::onCloseAlertClick,
                title = { Text("Some alert title") },
                text = { Text("Some alert text") },
                confirmButton = {
                    TextButton(onClick = firstScreenComponent::onCloseAlertClick) {
                        Text("Do nothing")
                    }
                },
                dismissButton = {
                    TextButton(onClick = firstScreenComponent::onCloseAlertClick) {
                        Text("close it")
                    }
                },
            )
        }
    }
}