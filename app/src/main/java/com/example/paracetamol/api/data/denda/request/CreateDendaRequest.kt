package com.example.paracetamol.api.data.denda.request

/**
 * Data class representing the request for creating a fine.
 * @property id_group The unique identifier of the group associated with the fine.
 * @property id_member The unique identifier of the member associated with the fine.
 * @property title The title or name of the fine.
 * @property hari The duration or timeframe of the fine.
 * @property nominal The amount or value of the fine.
 * @property desc Additional description or details about the fine.
 * @property is_paid A flag indicating whether the fine has been paid.
 */
data class CreateDendaRequest(
    val id_group: String,
    val id_member: String,
    val title: String,
    val hari: String,
    val nominal: Int,
    val desc: String,
    val is_paid: Boolean,
)