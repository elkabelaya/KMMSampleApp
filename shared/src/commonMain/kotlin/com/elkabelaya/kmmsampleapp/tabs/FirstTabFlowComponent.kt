package com.elkabelaya.kmmsampleapp.tabs

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.router.slot.navigate
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.elkabelaya.kmmsampleapp.DefaultFirstScreenComponent
import com.elkabelaya.kmmsampleapp.DefaultSecondScreenComponent
import com.elkabelaya.kmmsampleapp.FirstScreenComponent
import com.elkabelaya.kmmsampleapp.SecondScreenComponent

private sealed interface FirstTabFlowChildConfig : Parcelable {

    @Parcelize
    object FirstScreen: FirstTabFlowChildConfig

    @Parcelize
    data class SecondScreen(val value: String): FirstTabFlowChildConfig
}

interface FirstTabFlowComponent {
    val childSlot: Value<ChildSlot<*, Child>>
    fun onBackClicked()
    fun onFirstClicked()
    fun onSecondClicked()
    sealed interface Child {
        class FirstScreen(val component: FirstScreenComponent) : Child
        class SecondScreen(val component: SecondScreenComponent) : Child
    }
}

interface FirstTabFlowRouter {
    fun pushSecondScreen(value: String)
}
class DefaultFirstTabFlowComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext, FirstTabFlowComponent {
    private val slotNavigation = SlotNavigation<FirstTabFlowChildConfig>()

    private val _childSlot =
        childSlot(source = slotNavigation,
            handleBackButton = true,
            childFactory = ::createChild)
	override val childSlot: Value<ChildSlot<*, FirstTabFlowComponent.Child>> = _childSlot
    override fun onBackClicked() {
        slotNavigation.dismiss()
        //navigation.popTo(index = toIndex, onComplete = {})
    }

    override fun onFirstClicked() {
        slotNavigation.activate(FirstTabFlowChildConfig.FirstScreen)
    }
    override fun onSecondClicked() {
        slotNavigation.navigate { FirstTabFlowChildConfig.SecondScreen("jhg") }
    }
    private fun createChild(
        config: FirstTabFlowChildConfig,
        componentContext: ComponentContext
    ): FirstTabFlowComponent.Child = when (config) {

        is FirstTabFlowChildConfig.FirstScreen -> {
            FirstTabFlowComponent.Child.FirstScreen(
                DefaultFirstScreenComponent(
                    componentContext
                )
            )
        }

        is FirstTabFlowChildConfig.SecondScreen -> {
            FirstTabFlowComponent.Child.SecondScreen(
                DefaultSecondScreenComponent(
                    componentContext,
                    initialValue = config.value
                )
            )
        }
    }
}


private class DefaultFirstTabFlowRouter(val navigation: StackNavigation<FirstTabFlowChildConfig>):
    FirstTabFlowRouter {
    override fun pushSecondScreen(value: String) {
        navigation.push(FirstTabFlowChildConfig.SecondScreen(value))
    }

}

