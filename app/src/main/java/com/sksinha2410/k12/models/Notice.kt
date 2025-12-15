package com.sksinha2410.k12.models

data class Notice(
    val noticeId: String = "",
    val title: String = "",
    val description: String = "",
    val targetAudience: String = "ALL", // ALL, CLASS_SPECIFIC, PARENT, TEACHER
    val className: String = "", // For class-specific notices
    val postedBy: String = "", // Teacher/Admin ID
    val postedDate: String = "",
    val priority: NoticePriority = NoticePriority.NORMAL,
    val createdAt: Long = System.currentTimeMillis()
)

enum class NoticePriority {
    LOW,
    NORMAL,
    HIGH,
    URGENT
}
