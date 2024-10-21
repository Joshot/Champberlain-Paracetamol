package com.example.paracetamol.api.data.admin.request

/**
 * Data class representing the request for member settings.
 * @property groupRefKey The reference key of the group.
 * @property memberID The unique identifier of the member.
 */
data class MemberSettingRequest(
    val groupRefKey: String,
    val memberID: String
)