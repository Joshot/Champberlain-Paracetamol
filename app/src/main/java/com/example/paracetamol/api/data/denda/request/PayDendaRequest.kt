package com.example.paracetamol.api.data.denda.request

/**
 * Data class representing the request for paying a fine.
 * @property id_denda The unique identifier of the fine to be paid.
 * @property file The file containing the proof of payment.
 */
data class PayDendaRequest(
    val id_denda: String,
    val file: String
)