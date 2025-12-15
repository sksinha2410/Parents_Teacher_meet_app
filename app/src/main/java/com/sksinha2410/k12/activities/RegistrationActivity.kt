package com.sksinha2410.k12.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.sksinha2410.k12.R
import com.sksinha2410.k12.models.User
import com.sksinha2410.k12.models.UserRole
import com.sksinha2410.k12.repository.AuthRepository
import kotlinx.coroutines.launch

class RegistrationActivity : AppCompatActivity() {
    private lateinit var authRepository: AuthRepository
    private lateinit var nameInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var phoneInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var confirmPasswordInput: TextInputEditText
    private lateinit var roleSpinner: Spinner
    private lateinit var registerButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        authRepository = AuthRepository()

        nameInput = findViewById(R.id.nameInput)
        emailInput = findViewById(R.id.emailInput)
        phoneInput = findViewById(R.id.phoneInput)
        passwordInput = findViewById(R.id.passwordInput)
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput)
        roleSpinner = findViewById(R.id.roleSpinner)
        registerButton = findViewById(R.id.registerButton)

        // Setup role spinner
        val roles = arrayOf("Parent", "Teacher")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, roles)
        roleSpinner.adapter = adapter

        registerButton.setOnClickListener {
            performRegistration()
        }
    }

    private fun performRegistration() {
        val name = nameInput.text.toString().trim()
        val email = emailInput.text.toString().trim()
        val phone = phoneInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()
        val confirmPassword = confirmPasswordInput.text.toString().trim()
        val rolePosition = roleSpinner.selectedItemPosition
        val role = if (rolePosition == 0) UserRole.PARENT else UserRole.TEACHER

        if (!validateInputs(name, email, phone, password, confirmPassword)) {
            return
        }

        registerButton.isEnabled = false

        val user = User(
            name = name,
            email = email,
            phoneNumber = phone,
            role = role
        )

        lifecycleScope.launch {
            val result = authRepository.register(email, password, user)
            result.onSuccess {
                Toast.makeText(this@RegistrationActivity, "Registration successful! Please login.", Toast.LENGTH_SHORT).show()
                finish()
            }.onFailure { e ->
                Toast.makeText(this@RegistrationActivity, "Registration failed: ${e.message}", Toast.LENGTH_LONG).show()
                registerButton.isEnabled = true
            }
        }
    }

    private fun validateInputs(
        name: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (name.isEmpty()) {
            nameInput.error = "Name is required"
            return false
        }
        if (email.isEmpty()) {
            emailInput.error = "Email is required"
            return false
        }
        if (phone.isEmpty()) {
            phoneInput.error = "Phone number is required"
            return false
        }
        if (password.isEmpty()) {
            passwordInput.error = "Password is required"
            return false
        }
        if (password.length < 6) {
            passwordInput.error = "Password must be at least 6 characters"
            return false
        }
        if (password != confirmPassword) {
            confirmPasswordInput.error = "Passwords do not match"
            return false
        }
        return true
    }
}
