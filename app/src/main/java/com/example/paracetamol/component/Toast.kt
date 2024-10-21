// Toaster.kt
package com.example.paracetamol.component

import android.content.Context
import android.widget.Toast

/**
 * Displays a short-duration toast message in the given [context] with the specified [message].
 *
 * @param context The context in which the toast should be displayed.
 * @param message The message to be shown in the toast.
 */
fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
