package com.example.paracetamol.api.data.admin.response

import java.io.Serializable

/**
 * Data class representing the response for getting all members with admin information.
 * @property status The HTTP status code of the response.
 * @property message A descriptive message accompanying the response.
 * @property data The data containing a list of [MemberDataAdmin].
 */
data class GetAllMemberAdminResponse(
    val status: Int,
    val message: String,
    val data: GetAllMemberAdminResponseData
): Serializable

/**
 * Data class representing the data structure for the response.
 * @property data List of [MemberDataAdmin].
 */
data class GetAllMemberAdminResponseData(
    val data: List<MemberDataAdmin>
): Serializable

/**
 * Data class representing information about a member with admin details.
 * @property _id The unique identifier of the member.
 * @property nama The name of the member.
 * @property is_allowed Flag indicating if the member is allowed.
 * @property is_admin Flag indicating if the member has admin privileges.
 */
data class MemberDataAdmin(
    val _id: String,
    val nama: String,
    val is_allowed: Boolean,
    val is_admin: Boolean
): Serializable