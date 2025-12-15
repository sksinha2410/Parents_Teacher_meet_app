package com.sksinha2410.k12.models

data class Exam(
    val examId: String = "",
    val examName: String = "",
    val subject: String = "",
    val className: String = "",
    val examDate: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val totalMarks: Int = 0,
    val passingMarks: Int = 0,
    val syllabus: String = "",
    val createdBy: String = "", // Teacher ID
    val createdAt: Long = System.currentTimeMillis()
)
