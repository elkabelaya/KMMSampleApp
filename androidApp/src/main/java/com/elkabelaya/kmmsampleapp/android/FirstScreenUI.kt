package com.elkabelaya.kmmsampleapp.android

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.elkabelaya.kmmsampleapp.FirstScreenComponent
import com.elkabelaya.kmmsampleapp.MR.MR
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.resources.format


@Composable
fun FirstScreenUI(firstScreenComponent: FirstScreenComponent) {
    val state by firstScreenComponent.state.subscribeAsState()
    fun getCounterText(quantity: Int, context: Context): String {
        val counter =  MR.plurals.counter_plural.format(quantity, quantity)
        return MR.strings.first_screen_current_count.format(counter).toString(context)
    }
    val fontFamily: FontFamily = FontFamily(Font(MR.fonts.Mikar.mikar.fontResourceId))
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding()
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Image(
            painter = painterResource(id = MR.images.frog.drawableResId),
            modifier = Modifier.size(100.dp, 100.dp),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        Text(stringResource(id = MR.strings.first_screen_title.resourceId),
            fontFamily = fontFamily,
            fontSize = 14.sp,
            color = colorResource(id = MR.colors.title_color.resourceId))
        Text(getCounterText(state.count, LocalContext.current))
        Text(text = "${state.count}")
        TextField(value = state.text,
            onValueChange = firstScreenComponent::onChangeText
        )
        Button(onClick = firstScreenComponent::onIncrease) {
            Text(text = "+")
        }
        Button(onClick = firstScreenComponent::onDecrease) {
            Text(text = "-")
        }
        Button(onClick = {firstScreenComponent.onNextScreen(state.text)}) {
            Text(text = "onNext")
        }
        Button(onClick = firstScreenComponent::onShowAlertClick) {
            Text("ShowAlert")
        }

        if (state.showAlert) {
            AlertDialog(
                onDismissRequest = firstScreenComponent::onCloseAlertClick,
                title = { Text("Some alert title") },
                text = { Text("Some alert text") },
                confirmButton = {
                    TextButton(onClick = firstScreenComponent::onCloseAlertClick) {
                        Text("Do nothing")
                    }
                },
                dismissButton = {
                    TextButton(onClick = firstScreenComponent::onCloseAlertClick) {
                        Text("close it")
                    }
                },
            )
        }
    }
}