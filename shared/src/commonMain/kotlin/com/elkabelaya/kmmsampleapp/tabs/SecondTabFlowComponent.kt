package com.elkabelaya.kmmsampleapp.tabs

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.router.slot.navigate
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.elkabelaya.kmmsampleapp.DefaultFirstScreenComponent
import com.elkabelaya.kmmsampleapp.DefaultSecondScreenComponent
import com.elkabelaya.kmmsampleapp.FirstScreenComponent
import com.elkabelaya.kmmsampleapp.SecondScreenComponent

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