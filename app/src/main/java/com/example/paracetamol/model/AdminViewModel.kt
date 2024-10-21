package com.example.paracetamol.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paracetamol.api.ApiService
import com.example.paracetamol.api.data.admin.request.MemberSettingRequest
import com.example.paracetamol.api.data.admin.response.AMember
import com.example.paracetamol.api.data.admin.response.MemberDataAdmin
import com.example.paracetamol.api.data.admin.response.MemberWithDenda
import com.example.paracetamol.api.data.denda.request.CreateDendaRequest
import com.example.paracetamol.api.data.denda.request.DeleteDendaRequest
import com.example.paracetamol.api.data.denda.response.DendaSpesifikItem
import com.example.paracetamol.api.data.group.response.Member
import com.example.paracetamol.api.data.group.response.GroupItem
import com.example.paracetamol.api.data.profile.Profile
import com.example.paracetamol.preferences.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdminViewModel(private val context: Context): ViewModel() {
    private val _groupMembersData = MutableLiveData<List<Member?>?>()
    val groupMembersData: LiveData<List<Member?>?> get() = _groupMembersData

    private val _allMembersDataAdmin = MutableLiveData<List<MemberDataAdmin?>?>()
    val allMembersData: LiveData<List<MemberDataAdmin?>?> get() = _allMembersDataAdmin

    private val _successArchiveGroup = MutableLiveData<Boolean?>()
    val successArchiveGroup: LiveData<Boolean?> get() = _successArchiveGroup

    private val _successReactivateGroup = MutableLiveData<Boolean?>()
    val successReactivateGroup: LiveData<Boolean?> get() = _successReactivateGroup

    private val _successAcceptMember = MutableLiveData<Boolean?>()
    val successAcceptMember: LiveData<Boolean?> get() = _successAcceptMember

    private val _successKickMember = MutableLiveData<Boolean?>()
    val successKickMember: LiveData<Boolean?> get() = _successKickMember

    private val _groupData = MutableLiveData<GroupItem?>()
    val groupData: LiveData<GroupItem?> get() = _groupData

    private val _aMemberData = MutableLiveData<AMember?>()
    val aMemberData: LiveData<AMember?> get() = _aMemberData

    private val _addAdminSuccess = MutableLiveData<Boolean?>()
    val addAdminSuccess: LiveData<Boolean?> get() = _addAdminSuccess

    private val _demoteAdminSuccess = MutableLiveData<Boolean?>()
    val demoteAdminSuccess: LiveData<Boolean?> get() = _demoteAdminSuccess

    private val _createDendaSuccess = MutableLiveData<Boolean?>()
    val createDendaSuccess: LiveData<Boolean?> get() = _createDendaSuccess

    private val _deleteDendaSuccess = MutableLiveData<Boolean?>()
    val deleteDendaSuccess: LiveData<Boolean?> get() = _deleteDendaSuccess

    private val _spesifikDenda = MutableLiveData<DendaSpesifikItem?>()
    val spesifikDenda: LiveData<DendaSpesifikItem?> get() = _spesifikDenda

    private val _groupMemberWithDenda = MutableLiveData<List<MemberWithDenda?>?>()
    val groupMemberWithDenda: LiveData<List<MemberWithDenda?>?> get() = _groupMemberWithDenda

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private fun setGroupMembersData(data: List<Member>) {
        _groupMembersData.postValue(data)
    }

    private fun setAllGroupMembersData(data: List<MemberDataAdmin>){
        _allMembersDataAdmin.postValue(data)
    }

    private fun setArchiveGroupSuccess(){
        _successArchiveGroup.postValue(true)
    }

    private fun setReactivateGroupSuccess(){
        _successReactivateGroup.postValue(true)
    }

    private fun setGroupData(data: GroupItem){
        _groupData.postValue(data)
    }

    private fun setSuccessAcceptMember(){
        _successAcceptMember.postValue(true)
    }

    private fun setSuccessKickMember(){
        _successKickMember.postValue(true)
    }

    private fun setAMemberData(data: AMember){
        _aMemberData.postValue(data)
    }

    private fun setAddAdminSuccess(){
        _addAdminSuccess.postValue(true)
    }

    private fun setDemoteAdminSuccess(){
        _demoteAdminSuccess.postValue(true)
    }

    private fun setCreateDendaSuccess(){
        _createDendaSuccess.postValue(true)
    }

    private fun setDeleteDendaSuccess(){
        _deleteDendaSuccess.postValue(true)
    }

    private fun setSpesifikDenda(data: DendaSpesifikItem){
        _spesifikDenda.postValue(data)
    }

    private fun setGroupMemberWithDenda(data: List<MemberWithDenda>){
        _groupMemberWithDenda.postValue(data)
    }

    private fun clearErrorMessage(){
        if (_errorMessage.value != null) {
            _errorMessage.postValue(null)
        }
    }

    fun getMembersGroupData(groupRef: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val token = PreferenceManager.getToken(context)
                val response = ApiService.create().getAllMember(groupRef, "Bearer $token")
                Log.d(model_ref, "Response: $response")
                if (response.isSuccessful) {
                    clearErrorMessage()
                    Log.d(model_ref, "Data: ${response.body()?.data!!.data}")
                    setGroupMembersData(response.body()?.data!!.data)
                } else {
                    handleErrorResponse(response.code())
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to get all fines")
            }
        }
    }

    fun getAllMembersGroupDataAdmin(groupRef: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val token = PreferenceManager.getToken(context)
                val response = ApiService.create().getAllMemberAdmin(groupRef, "Bearer $token")
                if (response.isSuccessful) {
                    clearErrorMessage()
                    Log.d(model_ref, response.body()?.data.toString())
                    setAllGroupMembersData(response.body()?.data!!.data)
                } else {
                    handleErrorResponse(response.code())
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to get all fines")
            }
        }
    }

    fun archiveGroup(groupRef: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val token = PreferenceManager.getToken(context)
                val response = ApiService.create().deactivateGroup(groupRef, "Bearer $token")
                if (response.isSuccessful) {
                    clearErrorMessage()
                    setArchiveGroupSuccess()
                } else {
                    handleErrorResponse(response.code())
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to archive group.")
            }
        }
    }

    fun reactivateGroup(groupRef: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val token = PreferenceManager.getToken(context)
                val response = ApiService.create().reactivateGroup(groupRef, "Bearer $token")
                if (response.isSuccessful) {
                    clearErrorMessage()
                    setReactivateGroupSuccess()
                } else {
                    handleErrorResponse(response.code())
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to unarchive group.")
            }
        }
    }

    fun getAGroup(groupRef: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ApiService.create().getAGroup(groupRef)
                if (response.isSuccessful) {
                    clearErrorMessage()
                    val responseBody = response.body()
                    responseBody?.let {
                        val group = it.data?.data
                        Log.d(model_ref, group.toString())
                        group?.let {
                            setGroupData(group)
                        } ?: run {
                            _groupData.postValue(null)
                        }
                    }
                } else {
                    handleErrorResponse(response.code())
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to get group.")
            }
        }
    }

    fun acceptMember(groupRef: String, memberID: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val token = PreferenceManager.getToken(context)
                val acceptMemberRequest = MemberSettingRequest(groupRef, memberID)
                val response = ApiService.create().acceptMember("Bearer $token", acceptMemberRequest)
                if (response.isSuccessful) {
                    clearErrorMessage()
                    setSuccessAcceptMember()
                } else {
                    handleErrorResponse(response.code())
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to accept member.")
            }
        }
    }

    fun kickMember(groupRef: String, memberID: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val token = PreferenceManager.getToken(context)
                val kickMemberRequest = MemberSettingRequest(groupRef, memberID)
                val response = ApiService.create().kickMember("Bearer $token", kickMemberRequest)
                if (response.isSuccessful) {
                    clearErrorMessage()
                    setSuccessKickMember()
                } else {
                    handleErrorResponse(response.code())
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to kick member.")
            }
        }
    }

    fun getAGroupMember(memberID: String, groupRef: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val token = PreferenceManager.getToken(context)
                val response = ApiService.create().getAMember(memberID, groupRef, "Bearer $token")
                if (response.isSuccessful) {
                    clearErrorMessage()
                    val responseBody = response.body()
                    responseBody?.let {
                        val aMemberData = it.data?.data
                        aMemberData?.let {
                            setAMemberData(aMemberData)
                        } ?: run {
                            _aMemberData.postValue(null)
                        }
                    }
                } else {
                    handleErrorResponse(response.code())
                }
            } catch (e: Exception) {
                Log.d(model_ref, e.toString())
                _errorMessage.postValue("Failed to get a member data.")
            }
        }
    }

    fun addAdmin(memberID: String, groupRef: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val token = PreferenceManager.getToken(context)
                val addAdminRequest = MemberSettingRequest(groupRef, memberID)
                val response = ApiService.create().addAdmin("Bearer $token", addAdminRequest)
                if (response.isSuccessful) {
                    clearErrorMessage()
                    setAddAdminSuccess()
                } else {
                    handleErrorResponse(response.code())
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to promote member.")
            }
        }
    }

    fun demoteAdmin(memberID: String, groupRef: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val token = PreferenceManager.getToken(context)
                val demoteAdminRequest = MemberSettingRequest(groupRef, memberID)
                val response = ApiService.create().demoteAdmin("Bearer $token", demoteAdminRequest)
                if (response.isSuccessful) {
                    clearErrorMessage()
                    setDemoteAdminSuccess()
                } else {
                    handleErrorResponse(response.code())
                }
            } catch (e: Exception) {
                Log.d(model_ref, e.toString())
                _errorMessage.postValue("Failed to demote admin.")
            }
        }
    }

    fun createDenda(title: String, desc: String, due: String, nominal: Int, memberID: String, groupID: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val token = PreferenceManager.getToken(context)
                val createDendaRequest = CreateDendaRequest(groupID, memberID, title, due, nominal, desc, false)
                val response = ApiService.create().createDenda("Bearer $token", createDendaRequest)
                if (response.isSuccessful) {
                    clearErrorMessage()
                    setCreateDendaSuccess()
                } else {
                    handleErrorResponse(response.code())
                }
            } catch (e: Exception) {
                Log.d(model_ref, e.toString())
                _errorMessage.postValue("Failed to create fines.")
            }
        }
    }

    fun deleteDenda(dendaID: String, groupRef: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val token = PreferenceManager.getToken(context)
                val deleteDendaRequest = DeleteDendaRequest(dendaID, groupRef)
                val response = ApiService.create().deleteDenda("Bearer $token", deleteDendaRequest)
                if (response.isSuccessful) {
                    clearErrorMessage()
                    Log.d(model_ref, response.body()!!.string())
                    setDeleteDendaSuccess()
                } else {
                    handleErrorResponse(response.code())
                }
            } catch (e: Exception) {
                Log.d(model_ref, e.toString())
                _errorMessage.postValue("Failed to delete fines.")
            }
        }
    }

    fun getSpesifikDenda(dendaID: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ApiService.create().getSpesifikDenda(dendaID)
                if (response.isSuccessful) {
                    clearErrorMessage()
                    val responseBody = response.body()
                    responseBody?.let {
                        val spesifikDendaData = it.data?.data
                        spesifikDendaData?.let {
                            setSpesifikDenda(spesifikDendaData)
                        } ?: run {
                            _spesifikDenda.postValue(null)
                        }
                    }
                } else {
                    handleErrorResponse(response.code())
                }
            } catch (e: Exception) {
                Log.d(model_ref, e.toString())
                _errorMessage.postValue("Failed to delete fines.")
            }
        }
    }

    fun getGroupMemberWithDenda(groupRef: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ApiService.create().getGroupMemberWithDenda(groupRef)
                if (response.isSuccessful) {
                    clearErrorMessage()
                    val responseBody = response.body()
                    responseBody?.let {
                        val memberWithDenda = it.data?.members
                        memberWithDenda?.let {
                            setGroupMemberWithDenda(memberWithDenda)
                        } ?: run {
                            _spesifikDenda.postValue(null)
                        }
                    }
                } else {
                    handleErrorResponse(response.code())
                }
            } catch (e: Exception) {
                Log.d(model_ref, e.toString())
                _errorMessage.postValue("Failed to delete fines.")
            }
        }
    }


    private fun handleErrorResponse(responseCode: Int) {
        when (responseCode) {
            400 -> _errorMessage.postValue("Bad Request")
            401 -> _errorMessage.postValue("Unauthorized/Invalid Credentials")
            404 -> _errorMessage.postValue("Data Not Found")
            409 -> _errorMessage.postValue("Already Joined/Registered")
            500 -> _errorMessage.postValue("Server Error")
            else -> _errorMessage.postValue("Error Code: $responseCode")
        }
    }

    companion object {
        val model_ref = "AdminViewModel"
    }
}
