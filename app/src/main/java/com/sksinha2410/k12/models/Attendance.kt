package com.sksinha2410.k12.models

data class Attendance(
    val attendanceId: String = "",
    val studentId: String = "",
    val studentName: String = "",
    val className: String = "",
    val date: String = "",
    val status: AttendanceStatus = AttendanceStatus.PRESENT,
    val markedBy: String = "", // Teacher ID
    val remarks: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

enum class AttendanceStatus {
    PRESENT,
    ABSENT,
    LATE,
    HALF_DAY
}
