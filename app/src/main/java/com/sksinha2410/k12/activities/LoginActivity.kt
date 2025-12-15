package com.sksinha2410.k12.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.sksinha2410.k12.R
import com.sksinha2410.k12.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var authRepository: AuthRepository
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var loginButton: MaterialButton
    private lateinit var registerButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        authRepository = AuthRepository()

        // Check if user is already logged in
        if (authRepository.getCurrentUser() != null) {
            navigateToDashboard()
            return
        }

        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)

        loginButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (validateInputs(email, password)) {
                performLogin(email, password)
            }
        }

        registerButton.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            emailInput.error = "Email is required"
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
        return true
    }

    private fun performLogin(email: String, password: String) {
        loginButton.isEnabled = false
        lifecycleScope.launch {
            val result = authRepository.login(email, password)
            result.onSuccess {
                Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()
                navigateToDashboard()
            }.onFailure { e ->
                Toast.makeText(this@LoginActivity, "Login failed: ${e.message}", Toast.LENGTH_LONG).show()
                loginButton.isEnabled = true
            }
        }
    }

    private fun navigateToDashboard() {
        val userId = authRepository.getCurrentUser()?.uid ?: return
        lifecycleScope.launch {
            val result = authRepository.getUserData(userId)
            result.onSuccess { user ->
                val intent = when (user.role) {
                    com.sksinha2410.k12.models.UserRole.PARENT -> Intent(this@LoginActivity, ParentDashboardActivity::class.java)
                    com.sksinha2410.k12.models.UserRole.TEACHER -> Intent(this@LoginActivity, TeacherDashboardActivity::class.java)
                }
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }.onFailure { e ->
                Toast.makeText(this@LoginActivity, "Error loading user data: ${e.message}", Toast.LENGTH_LONG).show()
                authRepository.logout()
                loginButton.isEnabled = true
            }
        }
    }
}
