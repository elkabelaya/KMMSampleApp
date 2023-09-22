package com.elkabelaya.kmmsampleapp.android

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.elkabelaya.kmmsampleapp.screens.FirstScreenComponent
import com.elkabelaya.kmmsampleapp.MR.MR
import dev.icerock.moko.resources.format
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import com.elkabelaya.kmmsampleapp.utils.screenutils.ChildType


@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FirstScreenUI(
    component: FirstScreenComponent
) {
    val state by component.state.subscribeAsState()
    fun getCounterText(quantity: Int, context: Context): String {
        val counter =  MR.plurals.counter_plural.format(quantity, quantity)
        return MR.strings.first_screen_current_count.format(counter).toString(context)
    }
    val fontFamily: FontFamily = FontFamily(Font(MR.fonts.Mikar.mikar.fontResourceId))
    val childSlot by component.childSlot.subscribeAsState()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    when (childSlot.child?.instance?.type) {
        //TODO move to router
        ChildType.SCREEN -> {
            when (val slot = childSlot.child?.instance) {
                is FirstScreenComponent.Child.FirstScreen -> SecondScreenUI(
                    slot.component
                )

                else -> {}
            }
        }
        else -> {
            // main content
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding()
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Button(onClick = component::onCloseClicked) {
                    Text(text = "back")
                }
                Image(
                    painter = painterResource(id = MR.images.frog.drawableResId),
                    modifier = Modifier.size(100.dp, 100.dp),
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
                Text(
                    stringResource(id = MR.strings.first_screen_title.resourceId),
                    fontFamily = fontFamily,
                    fontSize = 14.sp,
                    color = colorResource(id = MR.colors.title_color.resourceId)
                )
                Text(getCounterText(state.count, LocalContext.current))
                Text(text = "${state.count}")
                TextField(
                    value = state.text,
                    onValueChange = component::onChangeText
                )
                Button(onClick = component::onIncrease) {
                    Text(text = "+")
                }
                Button(onClick = component::onDecrease) {
                    Text(text = "-")
                }
                Button(onClick = component::onNextScreen) {
                    Text(text = "onNext")
                }
                Button(onClick = component::onShowFirstAlertClick) {
                    Text("Show text alert")
                }
                Button(onClick = component::onShowSecondAlertClick) {
                    Text("Show count alert")
                }

                Text("Rest of the UI")
                Spacer(Modifier.height(20.dp))
                Button(onClick = component::onShowBottomSheetClick) {
                    Text("Click to show sheet")
                }

                when (childSlot.child?.instance?.type) {
                    //TODO move to router
                    ChildType.ALERT -> {
                        when (val slot = childSlot.child?.instance)  {
                            is FirstScreenComponent.Child.FirstAlert -> AlertUI(alertComponent = slot.component)
                            is FirstScreenComponent.Child.SecondAlert -> AlertUI(alertComponent = slot.component)
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
                                is FirstScreenComponent.Child.FirstBottomSheet -> SecondScreenUI(
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