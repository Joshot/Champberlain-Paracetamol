package com.example.paracetamol.api.data.group.request

/**
 * Data class representing the request for creating a group.
 * @property namaGroup The name of the group.
 * @property refKey The reference key of the group.
 * @property desc The description of the group.
 * @property status The status of the group.
 */
data class CreateGroupRequest(
    val namaGroup: String,
    val refKey: String,
    val desc: String,
    val status: Boolean,
)