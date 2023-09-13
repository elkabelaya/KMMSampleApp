package com.elkabelaya.kmmsampleapp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.items
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

data class TabState(
    val tabs: List<TabChildConfig>,
    var selectedTab: Int
)
sealed interface TabChildConfig : Parcelable {

    @Parcelize
    object FirstTab: TabChildConfig

    @Parcelize
    object SecondTab: TabChildConfig
}
interface TabComponent {
    var state: MutableValue<TabState>
    val childStack: Value<ChildStack<*, Child>>

    fun onFirstTabClicked()
    fun onSecondTabClicked()
    fun onTabClicked(index: Int)

    sealed class Child(val index: Int) {
        class FirstTab(val component: AppFlowComponent) : Child(index = 0)
        class SecondTab(val component: FirstScreenComponent) : Child(index = 1)
    }
}

val TabComponent.Child.index: Int
    get() =
        when (this) {
            is TabComponent.Child.FirstTab -> 0
            is TabComponent.Child.SecondTab -> 1
        }
class DefaultTabComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext, TabComponent {
    private val navigation = StackNavigation<TabChildConfig>()
    private val _childStack =
        childStack(
            source = navigation,
            initialConfiguration = TabChildConfig.FirstTab,
            handleBackButton = true, // Pop the back stack on back button press
            childFactory = ::createChild,
        )
    private val tabs = listOf(TabChildConfig.FirstTab,TabChildConfig.SecondTab)
    override var state = MutableValue(TabState(tabs, _childStack.active.instance.index))
    override val childStack: Value<ChildStack<*, TabComponent.Child>> = _childStack
    private fun createChild(
        config: TabChildConfig,
        componentContext: ComponentContext
    ): TabComponent.Child = when (config) {

        is TabChildConfig.FirstTab -> {
            TabComponent.Child.FirstTab(
                DefaultAppFlowComponent(componentContext = componentContext)
            )
        }

        is TabChildConfig.SecondTab -> {
            TabComponent.Child.SecondTab(
                DefaultFirstScreenComponent(componentContext,
                    FakeFirstScreenRouter(navigation = navigation)
                )
            )
        }
    }
    override fun onFirstTabClicked() {
        navigation.bringToFront(TabChildConfig.FirstTab)
    }
    override fun onSecondTabClicked() {
        navigation.bringToFront(TabChildConfig.SecondTab)
    }

    override fun onTabClicked(index: Int) {
        _childStack.items.firstOrNull { it.instance.index == index }?.also {
            navigation.bringToFront(it.configuration)
        }
    }
}

private class FakeFirstScreenRouter(val navigation: StackNavigation<TabChildConfig>): FirstScreenRouter {
    override fun pushSecondScreen(value: String) {
        //do nothing
    }
}