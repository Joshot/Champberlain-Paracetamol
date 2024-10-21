package com.example.paracetamol.api.data.group.response

import java.io.Serializable

/**
 * Data class representing the response for getting a specific group.
 * @property status The status of the response.
 * @property message The message of the response.
 * @property data The data of the response.
 */
data class GetJoinedGroupResponse(
    val status: Int,
    val message: String,
    val data: GetJoinedGroupResponseData
): Serializable

/**
 * Data class representing the data structure for the response.
 * @property data The data containing the group information.
 */
data class GetJoinedGroupResponseData(
    val groups: List<GroupItem>
): Serializable

/**
 * Data class representing information about a group.
 * @property _id The unique identifier of the group.
 * @property namaGroup The name of the group.
 * @property desc The description of the group.
 * @property refKey The reference key of the group.
 * @property status Flag indicating if the group is active.
 */
data class GroupItem(
    val _id: String,
    val namaGroup: String,
    val desc: String,
    val refKey: String,
    val status: Boolean
): Serializable
