package com.elkabelaya.kmmsampleapp.tabs

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

actual interface TabComponent: BaseTabComponent {
    val childStack: Value<ChildStack<*, BaseTabComponent.Child>>
    fun onFirstTabClicked()
    fun onSecondTabClicked()
}
actual class DefaultTabComponent actual constructor(
    componentContext: ComponentContext
) : DefaultBaseTabComponent(componentContext), TabComponent {
    val navigation = StackNavigation<TabChildConfig>()
    private val _childStack =
        childStack(
            source = navigation,
            initialConfiguration = TabChildConfig.FirstTab,
            handleBackButton = true, // Pop the back stack on back button press
            childFactory = ::createChild,
        )
    override val childStack: Value<ChildStack<*, BaseTabComponent.Child>> = _childStack
    override fun onFirstTabClicked() {
        navigation.bringToFront(TabChildConfig.FirstTab)
    }
    override fun onSecondTabClicked() {
        navigation.bringToFront(TabChildConfig.SecondTab)
    }
}