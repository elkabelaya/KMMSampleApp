package com.elkabelaya.kmmsampleapp.screens

import DefaultFourthScreenComponent
import FourthScreenComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.elkabelaya.kmmsampleapp.alerts.AlertComponent
import com.elkabelaya.kmmsampleapp.alerts.DefaultAlertComponent
import com.elkabelaya.kmmsampleapp.utils.screenutils.ChildType
import com.elkabelaya.kmmsampleapp.utils.screenutils.TypedChild

private sealed interface ThirdScreenChildConfig : Parcelable {
    @Parcelize
    object ThirdScreen: ThirdScreenChildConfig
    @Parcelize
    object ThirdAlert: ThirdScreenChildConfig
}
interface ThirdScreenComponent {
    val childSlot: Value<ChildSlot<*, Child>>
    fun onNextScreen()
    fun onShowAlertClick()
    fun onCloseClick()
    sealed class Child(override val type: ChildType): TypedChild {
        class ThirdFullScreen(val component: FourthScreenComponent) : Child(type = ChildType.FULLSCREENCOVER)
        class ThirdAlert(val component: AlertComponent) : Child(type = ChildType.ALERT)

    }
}
class DefaultThirdScreenComponent(
    componentContext: ComponentContext,
    dismiss: () -> Unit
) : ComponentContext by componentContext, ThirdScreenComponent {
    private val slotNavigation = SlotNavigation<ThirdScreenChildConfig>()

    private val _childSlot =
        childSlot(source = slotNavigation,
            handleBackButton = true,
            childFactory = ::createChild)
    override val childSlot: Value<ChildSlot<*, ThirdScreenComponent.Child>> = _childSlot

    override fun onNextScreen() {
        slotNavigation.activate(ThirdScreenChildConfig.ThirdScreen)
    }

    override fun onShowAlertClick() {
        slotNavigation.activate(ThirdScreenChildConfig.ThirdAlert)
    }

    override fun onCloseClick() {
        slotNavigation.dismiss()
    }

    private fun createChild(
        config: ThirdScreenChildConfig,
        componentContext: ComponentContext
    ): ThirdScreenComponent.Child = when (config) {

        is ThirdScreenChildConfig.ThirdScreen -> {
            ThirdScreenComponent.Child.ThirdFullScreen(
                DefaultFourthScreenComponent(
                    componentContext,
                    "123",
                    { slotNavigation.dismiss() }
                )
            )
        }
        is ThirdScreenChildConfig.ThirdAlert -> {
            ThirdScreenComponent.Child.ThirdAlert(
                DefaultAlertComponent(
                    "ThirdAlert title",
                    "ThirdAlert message",
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
    }

}
