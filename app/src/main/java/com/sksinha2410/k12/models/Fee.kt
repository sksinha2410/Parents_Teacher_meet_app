package com.sksinha2410.k12.models

data class Fee(
    val feeId: String = "",
    val studentId: String = "",
    val studentName: String = "",
    val className: String = "",
    val feeType: String = "", // Tuition, Transport, etc.
    val amount: Double = 0.0,
    val dueDate: String = "",
    val status: FeeStatus = FeeStatus.PENDING,
    val paymentDate: String = "",
    val remarks: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

enum class FeeStatus {
    PENDING,
    PAID,
    OVERDUE,
    PARTIALLY_PAID
}
