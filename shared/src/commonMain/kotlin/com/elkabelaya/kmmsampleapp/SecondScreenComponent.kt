package com.elkabelaya.kmmsampleapp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

data class SecondScreenState(
    val value: String
)

interface SecondScreenComponent {
    val state: Value<SecondScreenState>
}

class DefaultSecondScreenComponent(
    componentContext: ComponentContext,
    initialValue: String
) : ComponentContext by componentContext, SecondScreenComponent {
    override val state = MutableValue(SecondScreenState(initialValue))
}