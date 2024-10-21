package com.example.paracetamol.api.data.denda.response

import java.io.Serializable

/**
 * Data class representing the response for getting a specific fine.
 * @property status The status of the response.
 * @property message The message of the response.
 * @property data The data of the response.
 */
data class GetUserDendaResponse(
    val status: Int,
    val message: String,
    val data: GetUserDendaResponseData
): Serializable

/**
 * Data class representing the data structure for the response.
 * @property data The data containing the list of fines.
 */
data class GetUserDendaResponseData(
    val dendas: List<DendaItem>
): Serializable

/**
 * Data class representing information about a fine.
 * @property _id The unique identifier of the fine.
 * @property id_member The unique identifier of the member associated with the fine.
 * @property id_group The unique identifier of the group associated with the fine.
 * @property title The title of the fine.
 * @property hari The date of the fine.
 * @property nominal The amount of the fine.
 * @property desc The description of the fine.
 * @property is_paid Flag indicating if the fine has been paid.
 */
data class DendaItem(
    val _id: String,
    val id_member: String,
    val id_group: String,
    val title: String,
    val hari: String,
    val nominal: Int,
    val desc: String,
    val is_paid: Boolean
): Serializable