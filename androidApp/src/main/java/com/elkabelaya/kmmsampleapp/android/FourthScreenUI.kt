package com.elkabelaya.kmmsampleapp.android

import FourthScreenComponent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.elkabelaya.kmmsampleapp.screens.FirstScreenComponent
import com.elkabelaya.kmmsampleapp.utils.screenutils.ChildType

@Composable
fun FourthScreenUI(component: FourthScreenComponent) {

    val childSlot by component.childSlot.subscribeAsState()
    Surface(
    modifier = Modifier.fillMaxSize(),
    shape = RoundedCornerShape(0.dp),
    color = Color.Yellow
    ) {
        when (childSlot.child?.instance?.type) {
            //TODO move to router
            ChildType.SCREEN -> {
                when (val slot = childSlot.child?.instance) {
                    is FourthScreenComponent.Child.FirstScreen -> FirstScreenUI(
                        slot.component
                    )

                    else -> {}
                }
            }
            else -> {
                //main content
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("This is a forth screen")
                    Button(
                        onClick = component::onCloseClick,
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("Close")
                    }
                    Button(
                        onClick = component::onNextClick,
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("go to first screen")
                    }
                }
            }
        }
    }
}