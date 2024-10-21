package com.example.paracetamol.api.data.denda.request

/**
 * Data class representing the request for deleting a fine.
 * @property id_denda The unique identifier of the fine to be deleted.
 * @property groupRefKey The reference key of the group associated with the fine.
 */
data class DeleteDendaRequest(
    val id_denda: String,
    val groupRefKey: String
)