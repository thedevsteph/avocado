package com.example.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.login.databinding.ActivityLoginBinding
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    companion object {
        private lateinit var auth: FirebaseAuth
        private lateinit var viewModel: LoginViewModel
    }

    override fun onStart() {
        super.onStart()
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityLoginBinding.inflate(LayoutInflater.from(this)).apply {
            setContentView(root)
            viewModel = ViewModelProviders.of(this@LoginActivity).get(LoginViewModel::class.java)
            setLifecycleOwner(this@LoginActivity)
        }
        validateUserDetails()
    }

    private fun validateUserDetails() {

        val submitButton: TextView = findViewById(R.id.enter_image_box)
        val emailEditText: EditText = findViewById(R.id.login_email_edit_text)
        val passwordEditText: EditText = findViewById(R.id.login_password_edit_text)

        //change with data binding
        submitButton.apply {
            isClickable = true
            setOnClickListener {
                val email: String = emailEditText.text.toString()
                val password: String = passwordEditText.text.toString()
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    validateAuthenticationDetails(email, password)
                }
            }
        }
    }

    private var validationObserver = Observer<AuthResult?> { result ->
        when ((result is RuntimeException)) {
            false -> {
                Toast.makeText(this@LoginActivity, Utils.LOGIN_SUCCESSFUL_MESSAGE, Toast.LENGTH_SHORT).show()
                startHomeActivity()
            }
            true -> {
                Toast.makeText(this@LoginActivity, Utils.INVALID_DETAILS_MESSAGE, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateAuthenticationDetails(email: String, password: String) {
        viewModel.validateUserDetails(email, password).observe(this@LoginActivity, validationObserver)
    }

    private fun startHomeActivity() {
        this.startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
    }
}
