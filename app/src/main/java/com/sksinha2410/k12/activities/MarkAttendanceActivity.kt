package com.sksinha2410.k12.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.sksinha2410.k12.R
import com.sksinha2410.k12.models.Attendance
import com.sksinha2410.k12.models.AttendanceStatus
import com.sksinha2410.k12.models.Student
import com.sksinha2410.k12.repository.AuthRepository
import com.sksinha2410.k12.repository.FirestoreRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MarkAttendanceActivity : AppCompatActivity() {
    private lateinit var authRepository: AuthRepository
    private lateinit var firestoreRepository: FirestoreRepository
    private lateinit var studentsRecyclerView: RecyclerView
    private lateinit var submitButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var classNameText: TextView
    private lateinit var dateText: TextView
    
    private var className: String = ""
    private var teacherId: String = ""
    private val students = mutableListOf<Student>()
    private lateinit var adapter: StudentAttendanceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mark_attendance)

        authRepository = AuthRepository()
        firestoreRepository = FirestoreRepository()

        // Initialize views
        studentsRecyclerView = findViewById(R.id.studentsRecyclerView)
        submitButton = findViewById(R.id.submitButton)
        progressBar = findViewById(R.id.progressBar)
        classNameText = findViewById(R.id.classNameText)
        dateText = findViewById(R.id.dateText)

        // Get teacher ID
        teacherId = authRepository.getCurrentUser()?.uid ?: ""

        // Set current date
        val currentDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
        dateText.text = "Date: $currentDate"

        // Setup RecyclerView
        adapter = StudentAttendanceAdapter(students)
        studentsRecyclerView.layoutManager = LinearLayoutManager(this)
        studentsRecyclerView.adapter = adapter

        // Load teacher's class and students
        loadTeacherClassAndStudents()

        // Setup submit button
        submitButton.setOnClickListener {
            submitAttendance()
        }
    }

    private fun loadTeacherClassAndStudents() {
        progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            // First, get teacher's data to know their class
            val userResult = authRepository.getUserData(teacherId)
            userResult.onSuccess { user ->
                className = user.className
                classNameText.text = "Class: $className"

                // Now load students from that class
                loadStudents()
            }.onFailure { e ->
                progressBar.visibility = View.GONE
                Toast.makeText(
                    this@MarkAttendanceActivity,
                    getString(R.string.error_loading_students),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun loadStudents() {
        lifecycleScope.launch {
            val result = firestoreRepository.getStudentsByClass(className)
            progressBar.visibility = View.GONE

            result.onSuccess { studentList ->
                if (studentList.isEmpty()) {
                    Toast.makeText(
                        this@MarkAttendanceActivity,
                        getString(R.string.no_students_found),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    students.clear()
                    students.addAll(studentList)
                    adapter.notifyDataSetChanged()
                }
            }.onFailure { e ->
                Toast.makeText(
                    this@MarkAttendanceActivity,
                    getString(R.string.error_loading_students),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun submitAttendance() {
        if (students.isEmpty()) {
            Toast.makeText(this, getString(R.string.no_students_found), Toast.LENGTH_SHORT).show()
            return
        }

        progressBar.visibility = View.VISIBLE
        submitButton.isEnabled = false

        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        lifecycleScope.launch {
            var successCount = 0
            var failureCount = 0

            for (i in 0 until students.size) {
                val student = students[i]
                val attendanceData = adapter.getAttendanceData(i)

                val attendance = Attendance(
                    studentId = student.studentId,
                    studentName = student.name,
                    className = className,
                    date = currentDate,
                    status = attendanceData.status,
                    markedBy = teacherId,
                    remarks = attendanceData.remarks
                )

                val result = firestoreRepository.addAttendance(attendance)
                if (result.isSuccess) {
                    successCount++
                } else {
                    failureCount++
                }
            }

            progressBar.visibility = View.GONE
            submitButton.isEnabled = true

            if (failureCount == 0) {
                Toast.makeText(
                    this@MarkAttendanceActivity,
                    getString(R.string.attendance_submitted),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } else {
                Toast.makeText(
                    this@MarkAttendanceActivity,
                    getString(R.string.error_submitting_attendance),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // ViewHolder for student attendance item
    inner class StudentAttendanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val studentNameText: TextView = itemView.findViewById(R.id.studentNameText)
        val rollNumberText: TextView = itemView.findViewById(R.id.rollNumberText)
        val attendanceRadioGroup: RadioGroup = itemView.findViewById(R.id.attendanceRadioGroup)
        val presentRadio: RadioButton = itemView.findViewById(R.id.presentRadio)
        val absentRadio: RadioButton = itemView.findViewById(R.id.absentRadio)
        val lateRadio: RadioButton = itemView.findViewById(R.id.lateRadio)
        val halfDayRadio: RadioButton = itemView.findViewById(R.id.halfDayRadio)
        val remarksEditText: TextInputEditText = itemView.findViewById(R.id.remarksEditText)
    }

    // Adapter for student attendance list
    inner class StudentAttendanceAdapter(private val students: List<Student>) :
        RecyclerView.Adapter<StudentAttendanceViewHolder>() {

        private val attendanceData = mutableMapOf<Int, AttendanceData>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentAttendanceViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_student_attendance, parent, false)
            return StudentAttendanceViewHolder(view)
        }

        override fun onBindViewHolder(holder: StudentAttendanceViewHolder, position: Int) {
            val student = students[position]

            holder.studentNameText.text = student.name
            holder.rollNumberText.text = "Roll: ${student.rollNumber}"

            // Initialize with default data if not exists
            if (!attendanceData.containsKey(position)) {
                attendanceData[position] = AttendanceData(AttendanceStatus.PRESENT, "")
            }

            // Set listener for radio group changes
            holder.attendanceRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                val status = when (checkedId) {
                    R.id.presentRadio -> AttendanceStatus.PRESENT
                    R.id.absentRadio -> AttendanceStatus.ABSENT
                    R.id.lateRadio -> AttendanceStatus.LATE
                    R.id.halfDayRadio -> AttendanceStatus.HALF_DAY
                    else -> AttendanceStatus.PRESENT
                }
                attendanceData[position]?.status = status
            }

            // Set listener for remarks text changes
            holder.remarksEditText.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    attendanceData[position]?.remarks = holder.remarksEditText.text.toString()
                }
            }
        }

        override fun getItemCount(): Int = students.size

        fun getAttendanceData(position: Int): AttendanceData {
            return attendanceData[position] ?: AttendanceData(AttendanceStatus.PRESENT, "")
        }
    }

    // Data class to hold attendance status and remarks
    data class AttendanceData(
        var status: AttendanceStatus,
        var remarks: String
    )
}
