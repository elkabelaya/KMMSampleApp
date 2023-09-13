package com.elkabelaya.kmmsampleapp.android

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.elkabelaya.kmmsampleapp.FirstTabFlowComponent

@Composable
fun FirstTabFlowUI(component: FirstTabFlowComponent) {
    Children(
    stack = component.childStack,
    animation = stackAnimation(fade() + slide())){ child ->
        when (val instance = child.instance) {
            is FirstTabFlowComponent.Child.FirstScreen -> FirstScreenUI(instance.component)
            is FirstTabFlowComponent.Child.SecondScreen -> SecondScreenUI(instance.component)
        }
    }
}