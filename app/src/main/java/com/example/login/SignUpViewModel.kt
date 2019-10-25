package com.example.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthResult

class SignUpViewModel: ViewModel(){

    companion object{
        val authRepository = AuthRepository()
    }

    private val _authenticatedUserResult = MutableLiveData<AuthResult?>()
    var authenticatedUserResult: LiveData<AuthResult?> = _authenticatedUserResult

    fun authenticateUserDetails(email: String, password: String): LiveData<AuthResult?> {
        authRepository.addNewUser(email, password).addOnCompleteListener { task ->
            _authenticatedUserResult.value = task.result
        }
        return _authenticatedUserResult
    }
}