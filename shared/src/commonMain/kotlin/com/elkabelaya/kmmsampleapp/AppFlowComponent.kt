package com.elkabelaya.kmmsampleapp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

private sealed interface ChildConfig : Parcelable {

    @Parcelize
    object FirstScreen: ChildConfig

    @Parcelize
    data class SecondScreen(val value: String): ChildConfig
}

interface AppFlowComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class FirstScreen(val component: FirstScreenComponent) : Child
        class SecondScreen(val component: SecondScreenComponent) : Child
    }
}

class DefaultAppFlowComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext, AppFlowComponent {
    private val navigation = StackNavigation<ChildConfig>()
    private val _childStack =
        childStack(
            source = navigation,
            initialConfiguration = ChildConfig.FirstScreen,
            handleBackButton = true, // Pop the back stack on back button press
            childFactory = ::createChild,
        )

    override val childStack: Value<ChildStack<*, AppFlowComponent.Child>> = _childStack

    private fun createChild(
        config: ChildConfig,
        componentContext: ComponentContext
    ): AppFlowComponent.Child = when (config) {

        is ChildConfig.FirstScreen -> {
            AppFlowComponent.Child.FirstScreen(
                DefaultFirstScreenComponent(componentContext,
                    DefaultFirstScreenRouter(navigation = navigation))
            )
        }

        is ChildConfig.SecondScreen -> {
            AppFlowComponent.Child.SecondScreen(
                DefaultSecondScreenComponent(componentContext,
                    initialValue = config.value,
                    dismiss = {
                        navigation.pop()
                    })
            )
        }
    }
}

private class DefaultFirstScreenRouter(val navigation: StackNavigation<ChildConfig>): FirstScreenRouter {
    override fun pushSecondScreen(value: String) {
        navigation.push(ChildConfig.SecondScreen(value))
    }

}

