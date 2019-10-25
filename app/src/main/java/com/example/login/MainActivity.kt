package com.example.login

import android.content.Intent
import android.os.Bundle

import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureButtons()
    }

    private fun configureButtons() {
        val loginButton: TextView = findViewById(R.id.login_button)
        val signUpButton: TextView = findViewById(R.id.signup_button)
        loginButton.setOnClickListener { launchLoginActivity() }
        signUpButton.setOnClickListener { launchSignUpActivity() }
    }

    private fun launchLoginActivity() {
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun launchSignUpActivity() {
        var intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
}
