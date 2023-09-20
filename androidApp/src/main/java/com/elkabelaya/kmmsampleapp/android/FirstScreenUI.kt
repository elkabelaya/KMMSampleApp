package com.elkabelaya.kmmsampleapp.android

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.elkabelaya.kmmsampleapp.FirstScreenComponent
import com.elkabelaya.kmmsampleapp.MR.MR
import dev.icerock.moko.resources.format
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FirstScreenUI(
    firstScreenComponent: FirstScreenComponent,
    onBackClicked: () -> Unit
) {
    val state by firstScreenComponent.state.subscribeAsState()
    fun getCounterText(quantity: Int, context: Context): String {
        val counter =  MR.plurals.counter_plural.format(quantity, quantity)
        return MR.strings.first_screen_current_count.format(counter).toString(context)
    }
    val fontFamily: FontFamily = FontFamily(Font(MR.fonts.Mikar.mikar.fontResourceId))
    /*val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true)*/
    var showDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    //
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    var openSecondBottomSheet by rememberSaveable { mutableStateOf(false) }
    var skipPartiallyExpanded by remember { mutableStateOf(false) }
    var edgeToEdgeEnabled by remember { mutableStateOf(false) }
    //val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    val bottomSheetSecondState = rememberModalBottomSheetState(
    )


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding()
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Button(onClick = onBackClicked) {
            Text(text = "back")
        }
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

            if (showDialog) {
                Dialog(onDismissRequest = {showDialog = false}, properties = DialogProperties(usePlatformDefaultWidth = false)) {
                    // Custom layout for the dialog
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(0.dp),
                        color = Color.Yellow
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("This is a full screen dialog")
                            Button(
                                onClick = {showDialog = false},
                                modifier = Modifier.padding(top = 16.dp)
                            ) {
                                Text("Close Dialog")
                            }
                            Button(
                                // Note: If you provide logic outside of onDismissRequest to remove the sheet,
                                // you must additionally handle intended state cleanup, if any.
                                onClick = {
                                    openSecondBottomSheet = true
                                }
                            ) {
                                Text("Show second Bottom Sheet")
                            }
                        }
                        if (openSecondBottomSheet) {
                            /*val windowInsets = if (edgeToEdgeEnabled)
                                WindowInsets(0) else BottomSheetDefaults.windowInsets*/

                            ModalBottomSheet(
                                onDismissRequest = { openSecondBottomSheet = false },
                                sheetState = bottomSheetSecondState,
                            ) {
                                Column {
                                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                                        Button(
                                            // Note: If you provide logic outside of onDismissRequest to remove the sheet,
                                            // you must additionally handle intended state cleanup, if any.
                                            onClick = {
                                                scope.launch { bottomSheetSecondState.hide() }.invokeOnCompletion {
                                                    if (!bottomSheetSecondState.isVisible) {
                                                        openSecondBottomSheet = false
                                                    }
                                                }
                                            }
                                        ) {
                                            Text("Hide Second Bottom Sheet")
                                        }
                                    }
                                    var text by remember { mutableStateOf("") }
                                    OutlinedTextField(value = text, onValueChange = { text = it })
                                    Spacer(modifier = Modifier.fillMaxHeight())
                                }
                            }
                        }
                    }
                }
            }

            // Sheet content
            if (openBottomSheet) {
                /*val windowInsets = if (edgeToEdgeEnabled)
                    WindowInsets(0) else BottomSheetDefaults.windowInsets*/

                ModalBottomSheet(
                    onDismissRequest = { openBottomSheet = false },
                    sheetState = bottomSheetState,
                ) {
                    Column {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            Button(
                                // Note: If you provide logic outside of onDismissRequest to remove the sheet,
                                // you must additionally handle intended state cleanup, if any.
                                onClick = {
                                    scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                                        if (!bottomSheetState.isVisible) {
                                            openBottomSheet = false
                                        }
                                    }
                                }
                            ) {
                                Text("Hide Bottom Sheet")
                            }
                        }
                        Button(onClick = { showDialog = true }) {
                            Text(text = "Click to Show Full Screen Dialog" )
                        }
                        Button(
                            // Note: If you provide logic outside of onDismissRequest to remove the sheet,
                            // you must additionally handle intended state cleanup, if any.
                            onClick = {
                                openSecondBottomSheet = true
                            }
                        ) {
                            Text("Show second Bottom Sheet")
                        }
                        var text by remember { mutableStateOf("") }
                        OutlinedTextField(value = text, onValueChange = { text = it })
                        Spacer(modifier = Modifier.fillMaxHeight())
                    }
                }
            }





        Text("Rest of the UI")
        Spacer(Modifier.height(20.dp))
        Button(onClick = { scope.launch { openBottomSheet = true } }) {
            Text("Click to show sheet")
        }
        Button(onClick = { showDialog = true }) {
            Text(text = "Click to Show Full Screen Dialog" )
        }
    }
}