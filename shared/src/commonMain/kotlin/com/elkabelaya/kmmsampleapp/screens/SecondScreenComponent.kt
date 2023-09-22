package com.elkabelaya.kmmsampleapp.screens

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

data class SecondScreenState(
    val value: String
)

interface SecondScreenComponent {
    val state: Value<SecondScreenState>
    fun onBackClick()
}

class DefaultSecondScreenComponent(
    componentContext: ComponentContext,
    initialValue: String,
    val dismiss: () -> Unit
) : ComponentContext by componentContext, SecondScreenComponent {
    override val state = MutableValue(SecondScreenState(initialValue))
    override fun onBackClick() { dismiss() }
}