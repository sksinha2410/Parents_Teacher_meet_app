package com.sksinha2410.k12.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import com.sksinha2410.k12.R
import com.sksinha2410.k12.repository.AuthRepository
import com.sksinha2410.k12.repository.FirestoreRepository
import kotlinx.coroutines.launch

class TeacherDashboardActivity : AppCompatActivity() {
    private lateinit var authRepository: AuthRepository
    private lateinit var firestoreRepository: FirestoreRepository
    private lateinit var welcomeText: TextView
    private lateinit var attendanceCard: CardView
    private lateinit var homeworkCard: CardView
    private lateinit var noticesCard: CardView
    private lateinit var examsCard: CardView
    private lateinit var messagesCard: CardView
    private lateinit var logoutCard: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_dashboard)

        authRepository = AuthRepository()
        firestoreRepository = FirestoreRepository()

        welcomeText = findViewById(R.id.welcomeText)
        attendanceCard = findViewById(R.id.attendanceCard)
        homeworkCard = findViewById(R.id.homeworkCard)
        noticesCard = findViewById(R.id.noticesCard)
        examsCard = findViewById(R.id.examsCard)
        messagesCard = findViewById(R.id.messagesCard)
        logoutCard = findViewById(R.id.logoutCard)

        loadUserData()
        setupClickListeners()
    }

    private fun loadUserData() {
        val userId = authRepository.getCurrentUser()?.uid ?: return
        lifecycleScope.launch {
            val result = authRepository.getUserData(userId)
            result.onSuccess { user ->
                welcomeText.text = "Welcome, ${user.name}!"
            }.onFailure { e ->
                Toast.makeText(this@TeacherDashboardActivity, "Error loading user data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupClickListeners() {
        attendanceCard.setOnClickListener {
            val intent = Intent(this, MarkAttendanceActivity::class.java)
            startActivity(intent)
        }

        homeworkCard.setOnClickListener {
            Toast.makeText(this, "Homework feature - Assign homework to students", Toast.LENGTH_SHORT).show()
        }

        noticesCard.setOnClickListener {
            Toast.makeText(this, "Notices feature - Post notices for parents", Toast.LENGTH_SHORT).show()
        }

        examsCard.setOnClickListener {
            Toast.makeText(this, "Exams feature - Schedule and manage exams", Toast.LENGTH_SHORT).show()
        }

        messagesCard.setOnClickListener {
            Toast.makeText(this, "Messages feature - Chat with parents", Toast.LENGTH_SHORT).show()
        }

        logoutCard.setOnClickListener {
            authRepository.logout()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
