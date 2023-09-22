package com.elkabelaya.kmmsampleapp.android

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.elkabelaya.kmmsampleapp.screens.ThirdScreenComponent
import com.elkabelaya.kmmsampleapp.utils.screenutils.ChildType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThirdScreenUI(component: ThirdScreenComponent) {
    val childSlot by component.childSlot.subscribeAsState()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding()
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.Yellow)
    ) {
        //main content
        Text("This is The Third Screen")
        Button(
            onClick = { component.onNextScreen() }
        ) {
            Text("next screen")
        }
        Button(
            onClick = { component.onShowAlertClick() }
        ) {
            Text("show alert")
        }

        when (childSlot.child?.instance?.type) {
            //TODO move to router
            ChildType.ALERT -> {}
            ChildType.BOTTOMSHEET -> {}
            ChildType.FULLSCREENCOVER -> {
                Dialog(
                    onDismissRequest = { },
                    properties = DialogProperties(usePlatformDefaultWidth = false)
                ) {
                    when (val slot = childSlot.child?.instance) {
                        is ThirdScreenComponent.Child.ThirdFullScreen -> {
                            FourthScreenUI(component = slot.component)
                        }

                        else -> {}
                    }
                }
            }
            else -> {}
        }
    }
}