package com.elkabelaya.kmmsampleapp.tabs

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.elkabelaya.kmmsampleapp.screens.DefaultZeroScreenComponent
import com.elkabelaya.kmmsampleapp.screens.ZeroScreenComponent

sealed interface TabChildConfig : Parcelable {
    @Parcelize
    object FirstTab: TabChildConfig

    @Parcelize
    object SecondTab: TabChildConfig
}
interface BaseTabComponent {
    sealed class Child(val index: Int) {
        class FirstTab(val component: ZeroScreenComponent) : Child(index = 0)
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
                DefaultZeroScreenComponent(componentContext = componentContext)
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
