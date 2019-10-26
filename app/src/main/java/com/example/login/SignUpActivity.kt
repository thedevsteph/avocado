package com.example.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.login.databinding.ActivitySignUpBinding
import com.google.firebase.auth.AuthResult

class SignUpActivity : AppCompatActivity() {

    companion object {
        private lateinit var viewModel: SignUpViewModel
        private const val INVALID_MESSAGE = "Sorry, account cannot be created"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivitySignUpBinding.inflate(LayoutInflater.from(this)).apply {
            setContentView(root)
            viewModel = ViewModelProviders.of(this@SignUpActivity).get(SignUpViewModel::class.java)
            setLifecycleOwner(this@SignUpActivity)
        }

        viewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)
        setContentView(R.layout.activity_sign_up)
        validateUserDetails()
    }

    public override fun onStart() {
        super.onStart()
    }

    private fun validateUserDetails() {

        val submitButton: TextView = findViewById(R.id.submit_text_view)
        val emailEditText: EditText = findViewById(R.id.email_edit_text)
        val passwordEditText: EditText = findViewById(R.id.password_edit_text)

        submitButton.apply {
            isClickable = true
            setOnClickListener {
                val email: String = emailEditText.text.toString()
                val password: String = passwordEditText.text.toString()
                createNewUser(email, password)
            }
        }
    }

    private var userValidationObserver = Observer<AuthResult?> { result ->
        when (result != null) {
            true -> {
                startHomeActivity("Jamie")
            }
            false -> Toast.makeText(this, INVALID_MESSAGE, Toast.LENGTH_SHORT).show()
        }
    }

    private fun createNewUser(email: String, password: String) {
       viewModel.authenticateUserDetails(email, password).observe(this@SignUpActivity, userValidationObserver)
    }

    private fun startHomeActivity(name: String) {
        var intent = Intent(this@SignUpActivity, HomeActivity::class.java)
        intent.putExtra("name", name)
        this.startActivity(intent)
    }
}
