package com.elkabelaya.kmmsampleapp.screens

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.elkabelaya.kmmsampleapp.alerts.AlertComponent
import com.elkabelaya.kmmsampleapp.alerts.DefaultAlertComponent
import com.elkabelaya.kmmsampleapp.utils.screenutils.ChildType
import com.elkabelaya.kmmsampleapp.utils.screenutils.TypedChild

data class FirstScreenState(
    val count: Int,
    var text: String
)
private sealed interface FirstScreenChildConfig : Parcelable {
    @Parcelize
    data class FirstScreen(val value: String): FirstScreenChildConfig
    @Parcelize
    data class  FirstAlert(val value: String): FirstScreenChildConfig
    @Parcelize
    data class  SecondAlert(val value: Int): FirstScreenChildConfig
    @Parcelize
    data class FirstBottomSheet(val value: String): FirstScreenChildConfig
}
interface FirstScreenComponent {
    var state: MutableValue<FirstScreenState>
    val childSlot: Value<ChildSlot<*, FirstScreenComponent.Child>>
    fun onIncrease()
    fun onDecrease()
    fun onChangeText(value: String)
    fun onNextScreen()
    fun onShowFirstAlertClick()

    fun onShowSecondAlertClick()
    fun onCloseAlertClick()

    fun onShowBottomSheetClick()
    fun onBackClicked()
    fun onCloseClicked()
    sealed class Child(override val type: ChildType): TypedChild {
        class FirstScreen(val component: SecondScreenComponent) : Child(type = ChildType.SCREEN)
        class FirstAlert(val component: AlertComponent) : Child(type = ChildType.ALERT)
        class SecondAlert(val component: AlertComponent) : Child(type = ChildType.ALERT)

        class FirstBottomSheet(val component: SecondScreenComponent) : Child(type = ChildType.BOTTOMSHEET)

    }
}
class DefaultFirstScreenComponent(
    componentContext: ComponentContext,
    val dismiss: () -> Unit
) : ComponentContext by componentContext, FirstScreenComponent {

    override var state = MutableValue(FirstScreenState(0, ""))
    private val slotNavigation = SlotNavigation<FirstScreenChildConfig>()

    private val _childSlot =
        childSlot(
            source = slotNavigation,
            handleBackButton = true,
            childFactory = ::createChild
        )
    override val childSlot: Value<ChildSlot<*, FirstScreenComponent.Child>> = _childSlot
    override fun onBackClicked() {
        slotNavigation.dismiss()
    }

    override fun onIncrease() {
        state.update { it.copy(count = it.count + 1) }
    }

    override fun onDecrease() {
        state.update { it.copy(count = it.count - 1) }
    }

    override fun onChangeText(value: String): Unit {
        state.update { it.copy(text = value) }
    }

    override fun onNextScreen() {
        slotNavigation.activate(FirstScreenChildConfig.FirstScreen(state.value.text))
    }

    override fun onShowFirstAlertClick() {
        slotNavigation.activate(FirstScreenChildConfig.FirstAlert(state.value.text))
    }

    override fun onShowSecondAlertClick() {
        slotNavigation.activate(FirstScreenChildConfig.SecondAlert(state.value.count))
    }

    override fun onShowBottomSheetClick() {
        slotNavigation.activate(FirstScreenChildConfig.FirstBottomSheet(state.value.text))
    }

    override fun onCloseAlertClick() {
        slotNavigation.dismiss()
    }
    override fun onCloseClicked() {
        dismiss()
    }
    private fun createChild(
        config: FirstScreenChildConfig,
        componentContext: ComponentContext
    ): FirstScreenComponent.Child = when (config) {

        is FirstScreenChildConfig.FirstScreen -> {
            FirstScreenComponent.Child.FirstScreen(
                DefaultSecondScreenComponent(
                    componentContext,
                    config.value,
                    dismiss = slotNavigation::dismiss
                )
            )
        }

        is FirstScreenChildConfig.FirstAlert -> {
            FirstScreenComponent.Child.FirstAlert(
                DefaultAlertComponent(
                    "entered text",
                    "${config.value}",
                    "confirm",
                    "dismiss",
                    {
                        //do something
                        slotNavigation.dismiss()
                    },
                    {
                        //do something
                        slotNavigation.dismiss()
                    },
                    componentContext
                )
            )
        }

        is FirstScreenChildConfig.SecondAlert -> {
            FirstScreenComponent.Child.FirstAlert(
                DefaultAlertComponent(
                    "entered count",
                    "${config.value}",
                    "confirm",
                    "dismiss",
                    {
                        //do something
                        slotNavigation.dismiss()
                    },
                    {
                        //do something
                        slotNavigation.dismiss()
                    },
                    componentContext
                )
            )
        }

        is FirstScreenChildConfig.FirstBottomSheet -> {
            FirstScreenComponent.Child.FirstBottomSheet(
                DefaultSecondScreenComponent(
                    componentContext,
                    config.value,
                    dismiss = slotNavigation::dismiss
                )
            )
        }

    }
}
