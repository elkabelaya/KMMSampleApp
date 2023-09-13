package com.elkabelaya.kmmsampleapp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

private sealed interface FirstTabFlowChildConfig : Parcelable {

    @Parcelize
    object FirstScreen: FirstTabFlowChildConfig

    @Parcelize
    data class SecondScreen(val value: String): FirstTabFlowChildConfig
}

interface FirstTabFlowComponent {
    val childStack: Value<ChildStack<*, Child>>
    fun onBackClicked(toIndex: Int)
    sealed interface Child {
        class FirstScreen(val component: FirstScreenComponent) : Child
        class SecondScreen(val component: SecondScreenComponent) : Child
    }
}

class DefaultFirstTabFlowComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext, FirstTabFlowComponent {
    private val navigation = StackNavigation<FirstTabFlowChildConfig>()
    private val _childStack =
        childStack(
            source = navigation,
            initialConfiguration = FirstTabFlowChildConfig.FirstScreen,
            handleBackButton = true, // Pop the back stack on back button press
            childFactory = ::createChild,
        )

    override val childStack: Value<ChildStack<*, FirstTabFlowComponent.Child>> = _childStack

    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(index = toIndex, onComplete = {})
    }
    private fun createChild(
        config: FirstTabFlowChildConfig,
        componentContext: ComponentContext
    ): FirstTabFlowComponent.Child = when (config) {

        is FirstTabFlowChildConfig.FirstScreen -> {
            FirstTabFlowComponent.Child.FirstScreen(
                DefaultFirstScreenComponent(componentContext,
                    DefaultFirstScreenRouter(navigation = navigation))
            )
        }

        is FirstTabFlowChildConfig.SecondScreen -> {
            FirstTabFlowComponent.Child.SecondScreen(
                DefaultSecondScreenComponent(componentContext,
                    initialValue = config.value,
                    dismiss = {
                        navigation.pop()
                    })
            )
        }
    }
}

private class DefaultFirstScreenRouter(val navigation: StackNavigation<FirstTabFlowChildConfig>): FirstScreenRouter {
    override fun pushSecondScreen(value: String) {
        navigation.push(FirstTabFlowChildConfig.SecondScreen(value))
    }

}

