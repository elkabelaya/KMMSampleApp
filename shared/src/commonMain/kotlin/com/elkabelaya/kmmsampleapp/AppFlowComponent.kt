package com.elkabelaya.kmmsampleapp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

private sealed interface AppFlowChildConfig : Parcelable {

    @Parcelize
    object FirstScreen: AppFlowChildConfig

    @Parcelize
    data class SecondScreen(val value: String): AppFlowChildConfig
}

interface AppFlowComponent {
    val childStack: Value<ChildStack<*, Child>>
    fun onBackClicked(toIndex: Int)
    sealed interface Child {
        class FirstScreen(val component: FirstScreenComponent) : Child
        class SecondScreen(val component: SecondScreenComponent) : Child
    }
}

class DefaultAppFlowComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext, AppFlowComponent {
    private val navigation = StackNavigation<AppFlowChildConfig>()
    private val _childStack =
        childStack(
            source = navigation,
            initialConfiguration = AppFlowChildConfig.FirstScreen,
            handleBackButton = true, // Pop the back stack on back button press
            childFactory = ::createChild,
        )

    override val childStack: Value<ChildStack<*, AppFlowComponent.Child>> = _childStack

    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(index = toIndex, onComplete = {})
    }
    private fun createChild(
        config: AppFlowChildConfig,
        componentContext: ComponentContext
    ): AppFlowComponent.Child = when (config) {

        is AppFlowChildConfig.FirstScreen -> {
            AppFlowComponent.Child.FirstScreen(
                DefaultFirstScreenComponent(componentContext,
                    DefaultFirstScreenRouter(navigation = navigation))
            )
        }

        is AppFlowChildConfig.SecondScreen -> {
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

private class DefaultFirstScreenRouter(val navigation: StackNavigation<AppFlowChildConfig>): FirstScreenRouter {
    override fun pushSecondScreen(value: String) {
        navigation.push(AppFlowChildConfig.SecondScreen(value))
    }

}

