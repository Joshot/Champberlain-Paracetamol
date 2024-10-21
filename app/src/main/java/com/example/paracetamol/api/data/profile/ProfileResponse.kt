package com.example.paracetamol.api.data.profile

import java.io.Serializable

/**
 * Data class representing the response for getting the profile.
 * @property status The status of the response.
 * @property message The message of the response.
 * @property data The data of the response.
 */
data class ProfileResponse(
    val status: Int,
    val message: String,
    val data: ProfileResponseData
): Serializable

/**
 * Data class representing the data structure for the response.
 * @property data The data containing the profile information.
 */
data class ProfileResponseData(
    val profile: Profile
): Serializable

/**
 * Data class representing information about a profile.
 * @property angkatan The year of the profile.
 * @property email The email of the profile.
 * @property nama The name of the profile.
 * @property nim The NIM of the profile.
 * @property prodi The study program of the profile.
 * @property id The unique identifier of the profile.
 */
data class Profile(
    val angkatan: String,
    val email: String,
    val nama: String,
    val nim: String,
    val prodi: String,
    val id: String
): Serializable


