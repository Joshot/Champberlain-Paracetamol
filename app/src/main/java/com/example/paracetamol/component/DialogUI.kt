package com.example.paracetamol.component

import androidx.compose.foundation.background
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.paracetamol.R

@Composable
fun DialogUI(title: String, desc: String) {
    // A mutable state to track whether the dialog should be shown or not
    val shouldShowDialog = remember { mutableStateOf(true) }

    // Display the AlertDialog only if shouldShowDialog is true
    if (shouldShowDialog.value) {
        AlertDialog(
            // Callback for when the dialog is dismissed
            onDismissRequest = {
                shouldShowDialog.value = false
            },
            // Title of the dialog
            title = {
                Text(text = title)
            },
            // Description or content of the dialog
            text = {
                Text(text = desc)
            },
            // Confirm button with a "Close" label
            confirmButton = {
                Button(
                    onClick = {
                        // Close button clicked, set shouldShowDialog to false
                        shouldShowDialog.value = false
                    },
                    modifier = Modifier.background(color = colorResource(id = R.color.purple_200))
                ) {
                    Text(text = "Close")
                }
            }
        )
    }
}
