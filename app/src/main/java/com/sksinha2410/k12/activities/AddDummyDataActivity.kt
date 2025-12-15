package com.sksinha2410.k12.activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sksinha2410.k12.R
import com.sksinha2410.k12.models.Student
import com.sksinha2410.k12.repository.FirestoreRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class AddDummyDataActivity : AppCompatActivity() {
    private lateinit var firestoreRepository: FirestoreRepository
    private lateinit var addStudentsButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var statusText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_dummy_data)

        firestoreRepository = FirestoreRepository()

        addStudentsButton = findViewById(R.id.addStudentsButton)
        progressBar = findViewById(R.id.progressBar)
        statusText = findViewById(R.id.statusText)

        addStudentsButton.setOnClickListener {
            addDummyStudents()
        }
    }

    private fun addDummyStudents() {
        progressBar.visibility = View.VISIBLE
        addStudentsButton.isEnabled = false
        statusText.text = "Adding dummy students..."

        lifecycleScope.launch {
            val dummyStudents = createDummyStudents()
            var successCount = 0
            var failureCount = 0

            // Add students in batches to avoid overwhelming Firestore
            val batchSize = 5
            dummyStudents.chunked(batchSize).forEach { batch ->
                val results = batch.map { student ->
                    async {
                        firestoreRepository.addStudent(student)
                    }
                }.awaitAll()

                results.forEach { result ->
                    if (result.isSuccess) {
                        successCount++
                    } else {
                        failureCount++
                    }
                }
            }

            progressBar.visibility = View.GONE
            addStudentsButton.isEnabled = true

            if (failureCount == 0) {
                statusText.text = "Successfully added $successCount students!"
                Toast.makeText(
                    this@AddDummyDataActivity,
                    "Successfully added $successCount dummy students",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                statusText.text = "Added $successCount students, $failureCount failed"
                Toast.makeText(
                    this@AddDummyDataActivity,
                    "Added $successCount students, $failureCount failed",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun createDummyStudents(): List<Student> {
        val students = mutableListOf<Student>()
        
        // Class 1 students
        students.add(Student(
            name = "Aarav Kumar",
            className = "Class 1",
            rollNumber = "1",
            parentId = "",
            dateOfBirth = "2018-05-15"
        ))
        students.add(Student(
            name = "Diya Sharma",
            className = "Class 1",
            rollNumber = "2",
            parentId = "",
            dateOfBirth = "2018-07-22"
        ))
        students.add(Student(
            name = "Arjun Patel",
            className = "Class 1",
            rollNumber = "3",
            parentId = "",
            dateOfBirth = "2018-09-10"
        ))
        students.add(Student(
            name = "Ananya Singh",
            className = "Class 1",
            rollNumber = "4",
            parentId = "",
            dateOfBirth = "2018-03-28"
        ))
        students.add(Student(
            name = "Vihaan Reddy",
            className = "Class 1",
            rollNumber = "5",
            parentId = "",
            dateOfBirth = "2018-11-05"
        ))

        // Class 2 students
        students.add(Student(
            name = "Saanvi Gupta",
            className = "Class 2",
            rollNumber = "1",
            parentId = "",
            dateOfBirth = "2017-04-12"
        ))
        students.add(Student(
            name = "Aditya Verma",
            className = "Class 2",
            rollNumber = "2",
            parentId = "",
            dateOfBirth = "2017-08-19"
        ))
        students.add(Student(
            name = "Isha Joshi",
            className = "Class 2",
            rollNumber = "3",
            parentId = "",
            dateOfBirth = "2017-01-25"
        ))
        students.add(Student(
            name = "Reyansh Mehta",
            className = "Class 2",
            rollNumber = "4",
            parentId = "",
            dateOfBirth = "2017-06-30"
        ))
        students.add(Student(
            name = "Myra Kapoor",
            className = "Class 2",
            rollNumber = "5",
            parentId = "",
            dateOfBirth = "2017-12-08"
        ))

        // Class 3 students
        students.add(Student(
            name = "Kabir Malhotra",
            className = "Class 3",
            rollNumber = "1",
            parentId = "",
            dateOfBirth = "2016-02-14"
        ))
        students.add(Student(
            name = "Navya Agarwal",
            className = "Class 3",
            rollNumber = "2",
            parentId = "",
            dateOfBirth = "2016-09-21"
        ))
        students.add(Student(
            name = "Dhruv Sinha",
            className = "Class 3",
            rollNumber = "3",
            parentId = "",
            dateOfBirth = "2016-05-07"
        ))
        students.add(Student(
            name = "Avni Desai",
            className = "Class 3",
            rollNumber = "4",
            parentId = "",
            dateOfBirth = "2016-10-18"
        ))
        students.add(Student(
            name = "Vivaan Nair",
            className = "Class 3",
            rollNumber = "5",
            parentId = "",
            dateOfBirth = "2016-03-03"
        ))

        // Class 4 students
        students.add(Student(
            name = "Aanya Pandey",
            className = "Class 4",
            rollNumber = "1",
            parentId = "",
            dateOfBirth = "2015-07-11"
        ))
        students.add(Student(
            name = "Ishaan Rao",
            className = "Class 4",
            rollNumber = "2",
            parentId = "",
            dateOfBirth = "2015-11-29"
        ))
        students.add(Student(
            name = "Kiara Bose",
            className = "Class 4",
            rollNumber = "3",
            parentId = "",
            dateOfBirth = "2015-04-16"
        ))
        students.add(Student(
            name = "Rohan Chauhan",
            className = "Class 4",
            rollNumber = "4",
            parentId = "",
            dateOfBirth = "2015-08-23"
        ))
        students.add(Student(
            name = "Sara Das",
            className = "Class 4",
            rollNumber = "5",
            parentId = "",
            dateOfBirth = "2015-12-31"
        ))

        // Class 5 students
        students.add(Student(
            name = "Advait Roy",
            className = "Class 5",
            rollNumber = "1",
            parentId = "",
            dateOfBirth = "2014-01-09"
        ))
        students.add(Student(
            name = "Pihu Shah",
            className = "Class 5",
            rollNumber = "2",
            parentId = "",
            dateOfBirth = "2014-06-26"
        ))
        students.add(Student(
            name = "Ayaan Banerjee",
            className = "Class 5",
            rollNumber = "3",
            parentId = "",
            dateOfBirth = "2014-10-14"
        ))
        students.add(Student(
            name = "Riya Iyer",
            className = "Class 5",
            rollNumber = "4",
            parentId = "",
            dateOfBirth = "2014-03-20"
        ))
        students.add(Student(
            name = "Arnav Pillai",
            className = "Class 5",
            rollNumber = "5",
            parentId = "",
            dateOfBirth = "2014-09-05"
        ))

        return students
    }
}
