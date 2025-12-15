package com.sksinha2410.k12.models

data class User(
    val userId: String = "",
    val email: String = "",
    val name: String = "",
    val role: UserRole = UserRole.PARENT,
    val phoneNumber: String = "",
    val studentIds: List<String> = emptyList(), // For parents
    val className: String = "", // For teachers
    val createdAt: Long = System.currentTimeMillis()
)

enum class UserRole {
    PARENT,
    TEACHER
}
