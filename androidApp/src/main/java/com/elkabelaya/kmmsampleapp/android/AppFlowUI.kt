package com.elkabelaya.kmmsampleapp.android

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.elkabelaya.kmmsampleapp.AppFlowComponent

@Composable
fun AppFlowUI(component: AppFlowComponent) {
    Children(
    stack = component.childStack,
    animation = stackAnimation(fade() + slide())){ child ->
        when (val instance = child.instance) {
            is AppFlowComponent.Child.FirstScreen -> FirstScreenUI(instance.component)
            is AppFlowComponent.Child.SecondScreen -> SecondScreenUI(instance.component)
        }
    }
}