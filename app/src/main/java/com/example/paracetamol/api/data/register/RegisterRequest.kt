package com.example.paracetamol.api.data.register

/**
 * Data class representing the request for registering.
 * @property nama The name of the user.
 * @property nim The NIM of the user.
 * @property password The password of the user.
 * @property email The email of the user.
 * @property prodi The study program of the user.
 * @property angkatan The year of the user.
 */
data class RegisterRequest(
    val nama: String,
    val nim: String,
    val password: String,
    val email: String,
    val prodi: String,
    val angkatan: String,
)