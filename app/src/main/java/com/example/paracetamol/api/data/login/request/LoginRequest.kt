package com.example.paracetamol.api.data.login.request

/**
 * Data class representing the request for logging in.
 * @property email The email of the user.
 * @property password The password of the user.
 */
data class LoginRequest(
    val email: String,
    val password: String
)
