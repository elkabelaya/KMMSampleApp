package com.elkabelaya.kmmsampleapp.tabs

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.essenty.parcelable.Parcelable

private sealed interface SecondTabFlowChildConfig : Parcelable {
//no children by now
}

interface SecondTabFlowComponent {

}

class DefaultSecondTabFlowComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext, SecondTabFlowComponent {
    private val slotNavigation = SlotNavigation<SecondTabFlowChildConfig>()

}