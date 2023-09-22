package com.elkabelaya.kmmsampleapp.android

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.elkabelaya.kmmsampleapp.alerts.AlertComponent

@Composable
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)

fun AlertUI(alertComponent: AlertComponent) {
    AlertDialog(
        onDismissRequest = alertComponent.onDismiss,
        title = { Text(alertComponent.title) },
        text = { Text(alertComponent.message) },
        confirmButton = {
            TextButton(onClick = alertComponent.onConfirm) {
                Text(alertComponent.confirm)
            }
        },
        dismissButton = {
            TextButton(onClick = alertComponent.onDismiss) {
                Text(alertComponent.dismiss)
            }
        },
    )
}