package com.elkabelaya.kmmsampleapp.android

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.elkabelaya.kmmsampleapp.tabs.FirstTabFlowComponent
import com.elkabelaya.kmmsampleapp.MR.MR

@Composable
fun FirstTabFlowUI(component: FirstTabFlowComponent) {
    val childSlot by component.childSlot.subscribeAsState()
    when (val slot = childSlot.child?.instance) {
        is FirstTabFlowComponent.Child.FirstScreen -> FirstScreenUI(
            slot.component,
            onBackClicked = {component.onBackClicked()}
        )
        is FirstTabFlowComponent.Child.SecondScreen -> SecondScreenUI(
            slot.component,
            onBackClicked = {component.onBackClicked()}
        )
        else -> {
            Column {
                Button(
                    onClick = { component.onFirstClicked() }
                ) {
                    Text(stringResource(id = MR.strings.first_tab_button_first_title.resourceId))
                }

                Button(
                    onClick = { component.onSecondClicked() }
                ) {
                    Text(stringResource(id = MR.strings.first_tab_button_second_title.resourceId))
                }
            }
        }
    }
}

