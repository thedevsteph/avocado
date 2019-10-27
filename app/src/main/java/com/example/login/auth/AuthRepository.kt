package com.example.login.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class AuthRepository {


    companion object {
        lateinit var auth: FirebaseAuth
    }

    init {
        auth = FirebaseAuth.getInstance()
    }

    fun validateUserDetails(email: String, password: String): Task<AuthResult?> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun addNewUser(email: String, password: String): Task<AuthResult?> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

}