package com.elkabelaya.kmmsampleapp.android

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.elkabelaya.kmmsampleapp.screens.ZeroScreenComponent
import com.elkabelaya.kmmsampleapp.MR.MR
import com.elkabelaya.kmmsampleapp.utils.screenutils.ChildType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZeroScreenUI(component: ZeroScreenComponent) {
    val childSlot by component.childSlot.subscribeAsState()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    when (childSlot.child?.instance?.type) {
        //TODO move to router
        ChildType.SCREEN -> {
            when (val slot = childSlot.child?.instance) {
                is ZeroScreenComponent.Child.FirstScreen -> FirstScreenUI(
                    slot.component
                )

                is ZeroScreenComponent.Child.SecondScreen -> SecondScreenUI(
                    slot.component
                )

                else -> {}
            }
        }
        else -> {
            // main content
            Column {
                Button(
                    onClick = { component.onFirstClicked() }
                ) {
                    Text(stringResource(id = MR.strings.first_tab_button_first_title.resourceId))
                }

                Button(
                    onClick = { component.onSecondClicked() }
                ) {
                    Text(stringResource(id = MR.strings.first_tab_button_second_title.resourceId))
                }

                Button(
                    onClick = { component.onAlertClicked() }
                ) {
                    Text(stringResource(id = MR.strings.first_tab_button_alert_title.resourceId))
                }

                Button(
                    onClick = { component.onBottomSheetClicked() }
                ) {
                    Text(stringResource(id = MR.strings.first_tab_button_bs_title.resourceId))
                }

                when (childSlot.child?.instance?.type) {
                    //TODO move to router
                    ChildType.ALERT -> {
                        when (val slot = childSlot.child?.instance)  {
                            is ZeroScreenComponent.Child.FirstAlert -> AlertUI(alertComponent = slot.component)
                            else -> {}
                        }
                    }
                    ChildType.BOTTOMSHEET -> {
                        //TODO make wrapper for bottom sheet, to support DRY
                        ModalBottomSheet(
                            onDismissRequest =  component::onBackClicked,
                            sheetState = bottomSheetState,
                        ) {
                            when (val slot = childSlot.child?.instance) {
                                is ZeroScreenComponent.Child.FirstBottomSheet -> ThirdScreenUI(
                                    component = slot.component
                                )
                                else -> {}
                            }
                        }
                    }
                    ChildType.FULLSCREENCOVER -> {}
                    else -> {}
                }
            }
        }
    }
}

