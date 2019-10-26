package com.example.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthResult

class LoginViewModel: ViewModel(){

    companion object{
        val authRepository = AuthRepository()
    }

    private val _validateUserResult = MutableLiveData<AuthResult?>()
    var validateUserResult: LiveData<AuthResult?> = _validateUserResult


    fun validateUserDetails(email: String, password: String): LiveData<AuthResult?> {
        authRepository.validateUserDetails(email, password).addOnCompleteListener { task ->
            _validateUserResult.value = task.result
        }
        return validateUserResult
    }


}