package com.example.paracetamol.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paracetamol.api.ApiService
import com.example.paracetamol.api.data.denda.request.PayDendaRequest
import com.example.paracetamol.api.data.denda.response.DendaItem
import com.example.paracetamol.api.data.group.request.CreateGroupRequest
import com.example.paracetamol.api.data.group.response.GroupItem
import com.example.paracetamol.api.data.login.request.LoginRequest
import com.example.paracetamol.api.data.profile.Profile
import com.example.paracetamol.api.data.register.RegisterRequest
import com.example.paracetamol.preferences.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class UserViewModel(private val context: Context): ViewModel() {
    private val _registerSuccess = MutableLiveData<Boolean?>()
    val registerSuccess: LiveData<Boolean?> get() = _registerSuccess

    private val _loginSuccess = MutableLiveData<Boolean?>()
    val loginSuccess: LiveData<Boolean?> get() = _loginSuccess

    private val _profileData = MutableLiveData<Profile?>()
    val profileData: LiveData<Profile?> get() = _profileData

    private val _joinedGroups = MutableLiveData<List<GroupItem?>?>()
    val joinedGroups: LiveData<List<GroupItem?>?> get() = _joinedGroups

    private val _dendas = MutableLiveData<List<DendaItem?>?>()
    val dendas: LiveData<List<DendaItem?>?> get() = _dendas

    private val _payDendaSuccess = MutableLiveData<Boolean?>()
    val payDendaSuccess: LiveData<Boolean?> get() = _payDendaSuccess

    private val _joinGroupSuccess = MutableLiveData<Boolean?>()
    val joinGroupSuccess: LiveData<Boolean?> get() = _joinGroupSuccess

    private val _createGroupSuccess = MutableLiveData<Boolean?>()
    val createGroupSuccess: LiveData<Boolean?> get() = _createGroupSuccess

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private fun setLoginSuccess() {
        _loginSuccess.postValue(true)
    }

    private fun setRegisterSuccess() {
        _registerSuccess.postValue(true)
    }

    private fun setProfileData(data: Profile) {
        _profileData.postValue(data)
    }

    private fun setJoinedGroups(groups: List<GroupItem>){
        _joinedGroups.postValue(groups)
    }

    private fun setUserDenda(dendas: List<DendaItem>){
        _dendas.postValue(dendas)
    }

    private fun setPaySuccess(){
        _payDendaSuccess.postValue(true)
    }

    private fun setJoinGroupSuccess(){
        _joinGroupSuccess.postValue(true)
    }

    private fun setCreateGroupSuccess(){
        _createGroupSuccess.postValue(true)
    }

    private fun clearErrorMessage(){
        if (_errorMessage.value != null) {
            _errorMessage.postValue(null)
        }
    }

    fun register(
        nama: String,
        nim: String,
        password: String,
        email: String,
        prodi: String,
        angkatan: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val registerRequest = RegisterRequest(nama,nim, password, email, prodi, angkatan)
            try {
                val response = ApiService.create().register(registerRequest)
                if (response.isSuccessful) {
                    clearErrorMessage()
                    setRegisterSuccess()
                } else {
                    handleErrorResponse(response.code())
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Register failed")
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val loginRequest = LoginRequest(email, password)
            try {
                val response = ApiService.create().login(loginRequest)
                if (response.isSuccessful) {
                    clearErrorMessage()
                    val token = response.body()!!.token
                    saveToken(token)
                    saveSession()
                    setLoginSuccess()
                } else {
                    handleErrorResponse(response.code())
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Login failed")
            }
        }
    }

    fun getProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val token = PreferenceManager.getToken(context)
                val response = ApiService.create().getProfile("Bearer $token")
                if (response.isSuccessful) {
                    clearErrorMessage()
                    val profileData = response.body()?.data?.profile
                    if (profileData != null) {
                        setProfileData(profileData)
                    } else {
                        _errorMessage.postValue("Profile data is null")
                    }
                } else {
                    handleErrorResponse(response.code())
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to get profile")
            }
        }
    }

    fun getMemberID() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val memberID = PreferenceManager.getMemberID(context)
                if(memberID != null) return@launch;
                val token = PreferenceManager.getToken(context)
                val response = ApiService.create().getProfile("Bearer $token")
                if (response.isSuccessful) {
                    clearErrorMessage()
                    val profileData = response.body()?.data?.profile
                    if (profileData != null) {
                        saveMemberID(profileData.id)
                    } else {
                        _errorMessage.postValue("Profile data is null")
                    }
                } else {
                    handleErrorResponse(response.code())
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to get memberID")
            }
        }
    }

    fun getJoinedGroup(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val token = PreferenceManager.getToken(context)
                val response = ApiService.create().getJoinedGroup("Bearer $token")
                if (response.isSuccessful) {
                    clearErrorMessage()
                    val responseBody = response.body()
                    responseBody?.let {
                        val groups = it.data?.groups
                        groups?.let {
                            setJoinedGroups(groups)
                        } ?: run {
                            _joinedGroups.postValue(null)
                        }
                    }
                } else {
                    handleErrorResponse(response.code())
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to get joined group(s)")
            }
        }
    }

    fun getAllSelfDenda(isAdmin: Boolean, memberID: String?, groupID: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var memberID =
                    if (isAdmin && memberID != null)
                        memberID
                    else
                        PreferenceManager.getMemberID(context) ?: return@launch
                val response = ApiService.create().getAllUserDenda(memberID, groupID)
                if (response.isSuccessful) {
                    clearErrorMessage()
                    val responseBody = response.body()
                    responseBody?.let {
                        val dendas = it.data?.dendas
                        dendas?.let {
                            setUserDenda(dendas)
                        } ?: run {
                            _joinedGroups.postValue(null)
                        }
                    }
                } else {
                    handleErrorResponse(response.code())
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to get all fines")
            }
        }
    }

    fun payDenda(dendaID: String, link: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val token = PreferenceManager.getToken(context)
                val idDendaRequestBody = dendaID.toRequestBody("text/plain".toMediaTypeOrNull())
                val fileRequestBody = link.toRequestBody("text/plain".toMediaTypeOrNull())
                val response = ApiService.create().payDenda("Bearer $token", idDendaRequestBody, fileRequestBody)
                if (response.isSuccessful) {
                    clearErrorMessage()
                    setPaySuccess()
                } else {
                    handleErrorResponse(response.code())
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to pay fines")
            }
        }
    }

    fun joinGroup(referralCode: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val token = PreferenceManager.getToken(context)
                val response = ApiService.create().joinGroup(referralCode,"Bearer $token")
                if (response.isSuccessful) {
                    clearErrorMessage()
                    setJoinGroupSuccess()
                } else {
                    handleErrorResponse(response.code())
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to join the group")
            }
        }
    }

    fun createGroup(namaGroup: String, refKey: String, desc: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val token = PreferenceManager.getToken(context)
                val createGroupRequest = CreateGroupRequest(namaGroup, refKey, desc, true)
                val response = ApiService.create().createGroup("Bearer $token", createGroupRequest)
                if (response.isSuccessful) {
                    clearErrorMessage()
                    setCreateGroupSuccess()
                } else {
                    handleErrorResponse(response.code())
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to create a group")
            }
        }
    }

    fun getInGroupStatus(groupRef: String, onIsAdmin: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val token = PreferenceManager.getToken(context)
                val response = ApiService.create().checkStatusAdmin(groupRef, "Bearer $token")
                if (response.isSuccessful) {
                    clearErrorMessage()
                    val responseBody = response.body()?.string()
                    val jsonObject = JSONObject(responseBody)
                    val isAdmin = jsonObject.getBoolean("isAdmin")
                    onIsAdmin(isAdmin)
                } else {
                    handleErrorResponse(response.code())
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to get your status")
            }
        }
    }

    fun logout() {
        clearPreferences()
    }

    private fun saveMemberID(memberID: String) {
        PreferenceManager.saveMemberID(context, memberID)
    }

    private fun saveSession(){
        PreferenceManager.saveIsLoggedIn(context)
    }

    private fun saveToken(token: String) {
        PreferenceManager.saveToken(context, token)
    }

    private fun clearPreferences() {
        PreferenceManager.clearPreferences(context)
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
        val model_ref = "UserViewModel"
    }
}
