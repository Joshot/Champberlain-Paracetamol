package com.example.paracetamol.api.data.register

import java.io.Serializable

/**
 * Data class representing the response for registering.
 * @property message The message of the response.
 * @property data The data of the response.
 */
data class RegisterResponseData(
    val memberId: String,
): Serializable

/**
 * Data class representing the response for registering.
 * @property message The message of the response.
 * @property data The data of the response.
 */
data class RegisterResponse(
    val message: String,
    val data: RegisterResponseData
): Serializable