package com.example.paracetamol.api.data.denda.response

/**
 * Data class representing the response for getting a specific fine.
 * @property status The status of the response.
 * @property message The message of the response.
 * @property data The data of the response.
 */
data class GetSpesifikDendaResponse(
    val status: Int,
    val message: String,
    val data: GetSpesifikDendaResponseData
)

/**
 * Data class representing the data structure for the response.
 * @property data The data containing the link to the fine.
 */
data class GetSpesifikDendaResponseData(
    val data: DendaSpesifikItem
)

/**
 * Data class representing the link to the fine.
 * @property link The link to the fine.
 */
data class DendaSpesifikItem(
    val link: String
)