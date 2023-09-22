package com.elkabelaya.kmmsampleapp.alerts

import com.arkivanov.decompose.ComponentContext

interface AlertComponent {
    val title: String
    val message: String

    val confirm: String
    val dismiss: String

    val onConfirm: () -> Unit
    val onDismiss: () -> Unit
}
class DefaultAlertComponent(override val title: String,
                            override val message: String,
                            override val confirm: String,
                            override val dismiss: String,
                            override val onConfirm: () -> Unit,
                            override val onDismiss: () -> Unit,
                            componentContext: ComponentContext
                    ) : ComponentContext by componentContext, AlertComponent