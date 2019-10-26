package com.example.login

import android.content.res.Resources
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class SignUpViewModel : ViewModel() {

    companion object {
        val authRepository = AuthRepository()
    }

    private val _displayErrorMessage: MutableLiveData<Boolean> = MutableLiveData(false)
    var displayErrorMessage: LiveData<Boolean> = _displayErrorMessage

    private val _errorMessage: MutableLiveData<Int> = MutableLiveData(R.string.empty_string)
    var errorMessage: LiveData<Int> = _errorMessage

    private val _hasAccess: MutableLiveData<Boolean> = MutableLiveData(false)
    var hasAccess: LiveData<Boolean> = _hasAccess

    fun authenticateUserDetails(email: String, password: String) {
        authRepository.addNewUser(email, password).addOnCompleteListener { task ->
            handleAuthResult(task)
        }
    }

    private fun handleAuthResult(task: Task<AuthResult?>) {
        if((task.exception as FirebaseAuthUserCollisionException).errorCode == "ERROR_EMAIL_ALREADY_IN_USE"){
            displayErrorMessage()
        } else if (task.isSuccessful) {
            _hasAccess.value = true
        }
    }

    private fun displayErrorMessage() {
        _displayErrorMessage.value = true
        _errorMessage.value = R.string.user_exists_error_message
    }
}