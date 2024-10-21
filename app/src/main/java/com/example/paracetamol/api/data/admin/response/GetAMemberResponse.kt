package com.example.paracetamol.api.data.admin.response

import com.example.paracetamol.api.data.profile.Profile
import java.io.Serializable

/**
 * Data class representing the response for getting a member.
 * @property status The HTTP status code of the response.
 * @property message A descriptive message accompanying the response.
 * @property data The data containing a [GetAMemberResponseData].
 */
data class GetAMemberResponse(
    val status: Int,
    val message: String,
    val data: GetAMemberResponseData
): Serializable

/**
 * Data class representing the data structure for the response.
 * @property data The member information encapsulated in an [AMember] instance.
 */
data class GetAMemberResponseData(
    val data: AMember
): Serializable

/**
 * Data class representing detailed information about a member.
 * @property angkatan The batch or year of admission.
 * @property email The email address of the member.
 * @property nama The name of the member.
 * @property nim The student identification number.
 * @property prodi The program of study or major.
 * @property _id The unique identifier of the member.
 */
data class AMember(
    val angkatan: String,
    val email: String,
    val nama: String,
    val nim: String,
    val prodi: String,
    val _id: String
): Serializable


