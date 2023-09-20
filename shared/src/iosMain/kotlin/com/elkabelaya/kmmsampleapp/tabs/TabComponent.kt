package com.elkabelaya.kmmsampleapp.tabs

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

data class TabState(
    var selectedTab: Int
)
actual interface TabComponent: BaseTabComponent {
    var state: MutableValue<TabState>
    val tabs: List<BaseTabComponent.Child>
    fun onTabClicked(index: Int)
}


actual class DefaultTabComponent actual constructor(
    componentContext: ComponentContext
) : DefaultBaseTabComponent(componentContext), TabComponent {
        override val tabs = listOf(TabChildConfig.FirstTab, TabChildConfig.SecondTab)
        .map {
            createChild(it, componentContext)
        }
        override var state = MutableValue(TabState(0))
        override fun onTabClicked(index: Int) {
            /*_childStack.items.firstOrNull { it.instance.index == index }?.also {
                navigation.bringToFront(it.configuration)
            }*/
        }
    }
