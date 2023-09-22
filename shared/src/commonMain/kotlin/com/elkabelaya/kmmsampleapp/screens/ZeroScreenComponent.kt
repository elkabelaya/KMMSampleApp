package com.elkabelaya.kmmsampleapp.screens

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.router.slot.navigate
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.elkabelaya.kmmsampleapp.alerts.AlertComponent
import com.elkabelaya.kmmsampleapp.alerts.DefaultAlertComponent
import com.elkabelaya.kmmsampleapp.utils.screenutils.ChildType
import com.elkabelaya.kmmsampleapp.utils.screenutils.TypedChild

private sealed interface ZeroScreenChildConfig : Parcelable {

    @Parcelize
    object FirstScreen: ZeroScreenChildConfig

    @Parcelize
    data class SecondScreen(val value: String): ZeroScreenChildConfig
    @Parcelize
    object FirstAlert: ZeroScreenChildConfig
    @Parcelize
    object FirstBottomSheet: ZeroScreenChildConfig
}
interface ZeroScreenComponent {
    val childSlot: Value<ChildSlot<*, Child>>
    fun onBackClicked()
    fun onFirstClicked()
    fun onSecondClicked()
    fun onAlertClicked()

    fun onBottomSheetClicked()
    sealed class Child(override val type: ChildType): TypedChild {
        class FirstScreen(val component: FirstScreenComponent) : Child(type = ChildType.SCREEN)
        class SecondScreen(val component: SecondScreenComponent) : Child(type = ChildType.SCREEN)
        class FirstAlert(val component: AlertComponent) : Child(type = ChildType.ALERT)
        class FirstBottomSheet(val component: ThirdScreenComponent) : Child(type = ChildType.BOTTOMSHEET)
    }
}

class DefaultZeroScreenComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext, ZeroScreenComponent {
    private val slotNavigation = SlotNavigation<ZeroScreenChildConfig>()

    private val _childSlot =
        childSlot(source = slotNavigation,
            handleBackButton = true,
            childFactory = ::createChild)
	override val childSlot: Value<ChildSlot<*, ZeroScreenComponent.Child>> = _childSlot
    override fun onBackClicked() {
        slotNavigation.dismiss()
    }

    override fun onFirstClicked() {
        slotNavigation.activate(ZeroScreenChildConfig.FirstScreen)
    }
    override fun onSecondClicked() {
        slotNavigation.navigate { ZeroScreenChildConfig.SecondScreen("jhg") }
    }

    override fun onAlertClicked() {
        slotNavigation.activate(ZeroScreenChildConfig.FirstAlert)
    }

    override fun onBottomSheetClicked() {
        slotNavigation.activate(ZeroScreenChildConfig.FirstBottomSheet)
    }

    private fun createChild(
        config: ZeroScreenChildConfig,
        componentContext: ComponentContext
    ): ZeroScreenComponent.Child = when (config) {

        is ZeroScreenChildConfig.FirstScreen -> {
            ZeroScreenComponent.Child.FirstScreen(
                DefaultFirstScreenComponent(
                    componentContext,
                    dismiss = slotNavigation::dismiss
                )
            )
        }

        is ZeroScreenChildConfig.SecondScreen -> {
            ZeroScreenComponent.Child.SecondScreen(
                DefaultSecondScreenComponent(
                    componentContext,
                    initialValue = config.value,
                    dismiss = slotNavigation::dismiss
                )
            )
        }

        is ZeroScreenChildConfig.FirstAlert -> {
            ZeroScreenComponent.Child.FirstAlert(
                DefaultAlertComponent(
                    "title",
                    "message",
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

        is ZeroScreenChildConfig.FirstBottomSheet -> {
            ZeroScreenComponent.Child.FirstBottomSheet(
                DefaultThirdScreenComponent(
                    componentContext,
                    { slotNavigation.dismiss() }
                )
            )
        }
    }
}

