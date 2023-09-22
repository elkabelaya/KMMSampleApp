package com.elkabelaya.kmmsampleapp.android;

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable;
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.Direction
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.StackAnimator
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.isEnter
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.elkabelaya.kmmsampleapp.MR.MR
import com.elkabelaya.kmmsampleapp.tabs.BaseTabComponent
import com.elkabelaya.kmmsampleapp.tabs.TabComponent;

@Composable
fun TabUI(component: TabComponent) {
        val childStack by component.childStack.subscribeAsState()
        val activeComponent = childStack.active.instance
        val fontFamily: FontFamily = FontFamily(Font(MR.fonts.Mikar.mikar.fontResourceId))

        Column {
                Children(stack = component.childStack,
                        modifier = Modifier.weight(weight = 1F),
                        animation = tabAnimation()) {
                        when (val instance = it.instance) {
                                is BaseTabComponent.Child.FirstTab ->
                                        ZeroScreenUI(component = instance.component)

                                is BaseTabComponent.Child.SecondTab ->
                                        Text("TODO")
                        }
                }

                BottomNavigation(modifier = Modifier.fillMaxWidth()) {
                   BottomNavigationItem(
                                selected = activeComponent is BaseTabComponent.Child.FirstTab,
                                onClick = component::onFirstTabClicked,
                                icon = {
                                        Image(
                                                painter = painterResource(id = MR.images.tab_first_icon.drawableResId),
                                                modifier = Modifier.size(24.dp, 24.dp),
                                                contentDescription = null,
                                                contentScale = ContentScale.Fit
                                        )
                                },
                                label = {
                                        Text(
                                                stringResource(id = MR.strings.tab_first_title.resourceId),
                                                fontFamily = fontFamily,
                                                fontSize = 14.sp
                                        )
                                }
                        )
                        BottomNavigationItem(
                                selected = activeComponent is BaseTabComponent.Child.SecondTab,
                                onClick = component::onSecondTabClicked,
                                icon = {
                                        Image(
                                                painter = painterResource(id = MR.images.tab_second_icon.drawableResId),
                                                modifier = Modifier.size(24.dp, 24.dp),
                                                contentDescription = null,
                                                contentScale = ContentScale.Fit
                                        )
                                },
                                label = {
                                        Text(
                                                stringResource(id = MR.strings.tab_second_title.resourceId),
                                                fontFamily = fontFamily,
                                                fontSize = 14.sp
                                        )
                                }
                        )
                }
        }
}


@Composable
private fun tabAnimation(): StackAnimation<Any, BaseTabComponent.Child> =
        stackAnimation { child, otherChild, direction ->
                val index = child.instance.index
                val otherIndex = otherChild.instance.index
                val anim = slide()
                if ((index > otherIndex) == direction.isEnter) anim else anim.flipSide()
        }

private fun StackAnimator.flipSide(): StackAnimator =
        StackAnimator { direction, isInitial, onFinished, content ->
                invoke(
                        direction = direction.flipSide(),
                        isInitial = isInitial,
                        onFinished = onFinished,
                        content = content,
                )
        }

private fun Direction.flipSide(): Direction =
        when (this) {
                Direction.ENTER_FRONT -> Direction.ENTER_BACK
                Direction.EXIT_FRONT -> Direction.EXIT_BACK
                Direction.ENTER_BACK -> Direction.ENTER_FRONT
                Direction.EXIT_BACK -> Direction.EXIT_FRONT
        }