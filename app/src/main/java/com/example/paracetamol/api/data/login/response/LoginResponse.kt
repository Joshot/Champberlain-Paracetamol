package com.example.paracetamol.api.data.login.response

import java.io.Serializable

/**
 * Data class representing the response for logging in.
 * @property message The message of the response.
 * @property token The token of the response.
 */
data class LoginResponse(
    val message: String,
    val token: String
): Serializable