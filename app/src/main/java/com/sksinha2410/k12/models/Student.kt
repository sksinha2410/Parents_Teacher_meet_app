package com.sksinha2410.k12.models

data class Student(
    val studentId: String = "",
    val name: String = "",
    val className: String = "",
    val rollNumber: Int = 0,
    val parentId: String = "",
    val dateOfBirth: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
