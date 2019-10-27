package com.example.login.signup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.login.databinding.ActivitySignUpBinding
import com.example.login.home.HomeActivity

class SignUpActivity : AppCompatActivity() {

    companion object {
        private lateinit var viewModel: SignUpViewModel
        private lateinit var binding: ActivitySignUpBinding
        const val ACCOUNT_CREATED_TEXT = "welcome to the family."
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(LayoutInflater.from(this)).apply {
            setContentView(root)
            viewModel = ViewModelProviders.of(this@SignUpActivity).get(
                SignUpViewModel::class.java)
            setLifecycleOwner(this@SignUpActivity)
        }
        updateUI()
        validateUserDetails()
    }


    private fun updateUI() {
        viewModel.apply {
            errorMessage.observe(this@SignUpActivity, updateErrorMessageObserver)
            displayErrorMessage.observe(this@SignUpActivity, updateVisibilityObserver)
        }
    }

    private fun validateUserDetails() {
        binding.submitTextView.apply {
            isClickable = true
            setOnClickListener {
                val email: String = binding.emailEditText.text.toString()
                val password: String = binding.passwordEditText.text.toString()
                createNewUser(email, password)
                viewModel.hasAccess.observe(this@SignUpActivity, userValidationObserver)
                viewModel.resetAccess()
            }
        }
    }

    private fun createNewUser(email: String, password: String) {
        viewModel.apply {
            authenticateUserDetails(email, password)
        }
    }

    private var updateErrorMessageObserver = Observer<Int> { text ->
        binding.errorMessageText.text = this.resources.getString(text)
    }

    private var updateVisibilityObserver = Observer<Boolean> { state ->
        if (state) {
            binding.errorMessageText.visibility = View.VISIBLE
        } else {
            binding.errorMessageText.visibility = View.GONE
        }
    }

    private fun startHomeActivity() {
        val intent = Intent(this@SignUpActivity, HomeActivity::class.java)
        this.startActivity(intent)
    }

    private var userValidationObserver = Observer{ result: Boolean? ->
        when (result) {
            true -> {
                Toast.makeText(this,
                    ACCOUNT_CREATED_TEXT, Toast.LENGTH_SHORT).show()
                startHomeActivity()
            }
            else -> {}
        }
    }

    private fun removeObservers() {
        viewModel.apply {
            errorMessage.removeObservers(this@SignUpActivity)
            displayErrorMessage.removeObservers(this@SignUpActivity)
            hasAccess.removeObservers(this@SignUpActivity)
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.resetAccess()
        removeObservers()
    }

    override fun onResume() {
        super.onResume()
        validateUserDetails()
        updateUI()
    }
}
