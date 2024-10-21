package com.example.paracetamol.api.data.group.response

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.Serializable

/**
 * Data class representing the response for getting all groups.
 * @property status The status of the response.
 * @property message The message of the response.
 * @property data The data of the response.
 */
data class GetAllMemberResponse(
    val status: Int,
    val message: String,
    val data: GetAllMemberResponseData
): Serializable

/**
 * Data class representing the data structure for the response.
 * @property data The data containing the list of groups.
 */
data class GetAllMemberResponseData(
    val data: List<Member>
): Serializable

/**
 * Data class representing information about a group.
 * @property _id The unique identifier of the group.
 * @property nama The name of the group.
 * @property is_admin Flag indicating if the user is an admin of the group.
 * @property totalDenda The total amount of fines associated with the group.
 */
data class Member(
    val _id: String,
    val nama: String,
    val is_admin: Boolean,
    val totalDenda: Int = 0
) {
}