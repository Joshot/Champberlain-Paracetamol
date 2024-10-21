package com.example.paracetamol.api.data.group.response

import java.io.Serializable

/**
 * Data class representing the response for getting a specific group.
 * @property status The status of the response.
 * @property message The message of the response.
 * @property data The data of the response.
 */
data class GetAGroupResponse(
    val status: Int,
    val message: String,
    val data: GetAGroupResponseData
): Serializable

/**
 * Data class representing the data structure for the response.
 * @property data The data containing the group information.
 */
data class GetAGroupResponseData(
    val data: GroupItem
): Serializable