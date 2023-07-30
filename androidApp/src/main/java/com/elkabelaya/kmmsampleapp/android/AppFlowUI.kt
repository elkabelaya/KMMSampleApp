package com.elkabelaya.kmmsampleapp.android

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.decompose.value.getValue
import com.elkabelaya.kmmsampleapp.AppFlowComponent

@Composable
fun AppFlowUI(component: AppFlowComponent) {
    val childStack by component.childStack.subscribeAsState()

    Children(childStack) { child ->
        when (val instance = child.instance) {
            is AppFlowComponent.Child.FirstScreen -> FirstScreenUI(instance.component)
            is AppFlowComponent.Child.SecondScreen -> SecondScreenUI(instance.component)
        }
    }
}