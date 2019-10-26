package com.example.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.login.databinding.ActivitySignUpBinding
import kotlinx.android.synthetic.main.activity_sign_up.view.*

class SignUpActivity : AppCompatActivity() {

    companion object {
        private lateinit var viewModel: SignUpViewModel
        private lateinit var binding: ActivitySignUpBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(LayoutInflater.from(this)).apply {
            setContentView(root)
            viewModel = ViewModelProviders.of(this@SignUpActivity).get(SignUpViewModel::class.java)
            setLifecycleOwner(this@SignUpActivity)
            updateUI()
        }
        validateUserDetails()
    }

    public override fun onStart() {
        super.onStart()
    }

    private fun updateUI() {
        viewModel.apply {
            errorMessage.observe(this@SignUpActivity, updateErrorMessageObserver)
            displayErrorMessage.observe(this@SignUpActivity, updateVisibilityObserver)
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


    private fun validateUserDetails() {
        binding.submitTextView.apply {
            isClickable = true
            setOnClickListener {
                val email: String = binding.emailEditText.text.toString()
                val password: String = binding.passwordEditText.text.toString()
                createNewUser(email, password)
            }
        }
    }

    private var userValidationObserver = Observer<Boolean?> { result ->
        when(result){
            true -> startHomeActivity()
            else -> {}
        }
    }


    private fun createNewUser(email: String, password: String) {
        viewModel.apply {
            authenticateUserDetails(email, password)
            hasAccess.observe(this@SignUpActivity, userValidationObserver)
        }
        viewModel.hasAccess.removeObservers(this@SignUpActivity)
    }

    private fun startHomeActivity() {
        var intent = Intent(this@SignUpActivity, HomeActivity::class.java)
        this.startActivity(intent)
    }
}
