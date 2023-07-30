package com.elkabelaya.kmmsampleapp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.reduce

data class FirstScreenState(
    val count: Int,
    var text: String,
    var showAlert: Boolean
)

interface FirstScreenComponent {
    var state: MutableValue<FirstScreenState>
    fun onIncrease()
    fun onDecrease()
    fun onChangeText(value: String)
    fun onNextScreen(value: String)
    fun onShowAlertClick()
    fun onCloseAlertClick()
}

interface FirstScreenRouter {
    fun pushSecondScreen(value: String)
}
class DefaultFirstScreenComponent(
    componentContext: ComponentContext,
    val firstScreenRouter: FirstScreenRouter
) : ComponentContext by componentContext, FirstScreenComponent {
    override var state = MutableValue(FirstScreenState(0, "", showAlert = false))
    override fun onIncrease() {
        state.reduce { it.copy(count = it.count + 1) }
    }
    override fun onDecrease() {
        state.reduce { it.copy(count = it.count - 1) }
    }

    override fun onChangeText(value: String): Unit {
        state.reduce { it.copy(text = value) }
    }

    override fun onNextScreen(value: String) {
        firstScreenRouter.pushSecondScreen(value)
    }

    override fun onShowAlertClick() {
        state.reduce { it.copy(showAlert = true) }
    }

    override fun onCloseAlertClick() {
        state.reduce { it.copy(showAlert = false) }
    }
}
