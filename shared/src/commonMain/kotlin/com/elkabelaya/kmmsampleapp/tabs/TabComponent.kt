package com.elkabelaya.kmmsampleapp.tabs

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.items
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.elkabelaya.kmmsampleapp.FirstScreenComponent

sealed interface TabChildConfig : Parcelable {
    @Parcelize
    object FirstTab: TabChildConfig

    @Parcelize
    object SecondTab: TabChildConfig
}
interface BaseTabComponent {
    sealed class Child(val index: Int) {
        class FirstTab(val component: FirstTabFlowComponent) : Child(index = 0)
        class SecondTab(val component: SecondTabFlowComponent) : Child(index = 1)
    }
}

expect interface TabComponent: BaseTabComponent
open class DefaultBaseTabComponent(
    componentContext: ComponentContext
) : ComponentContext by  componentContext, BaseTabComponent {
     fun createChild(
        config: TabChildConfig,
        componentContext: ComponentContext
    ): BaseTabComponent.Child = when (config) {

        is TabChildConfig.FirstTab -> {
            BaseTabComponent.Child.FirstTab(
                DefaultFirstTabFlowComponent(componentContext = componentContext)
            )
        }

        is TabChildConfig.SecondTab -> {
            BaseTabComponent.Child.SecondTab(
                DefaultSecondTabFlowComponent(componentContext = componentContext)
            )
        }
    }
}
expect class DefaultTabComponent(
    componentContext: ComponentContext
) : DefaultBaseTabComponent
