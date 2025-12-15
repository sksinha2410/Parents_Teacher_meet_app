package com.sksinha2410.k12.models

data class Homework(
    val homeworkId: String = "",
    val title: String = "",
    val description: String = "",
    val subject: String = "",
    val className: String = "",
    val assignedBy: String = "", // Teacher ID
    val assignedDate: String = "",
    val dueDate: String = "",
    val status: HomeworkStatus = HomeworkStatus.ASSIGNED,
    val createdAt: Long = System.currentTimeMillis()
)

enum class HomeworkStatus {
    ASSIGNED,
    SUBMITTED,
    GRADED
}
