package com.elkabelaya.kmmsampleapp

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.reduce

data class FirstScreenState(val count: Int)

interface FirstScreenComponent {
    val state: Value<FirstScreenState>
    fun onIncrease()
    fun onDecrease()
}

class DefaultFirstScreenComponent: FirstScreenComponent {
    override val state = MutableValue(FirstScreenState(0))
    override fun onIncrease() {
        state.reduce { it.copy(count = it.count + 1) }
    }
    override fun onDecrease() {
        state.reduce { it.copy(count = it.count - 1) }
    }
}
