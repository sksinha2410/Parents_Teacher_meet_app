package com.sksinha2410.k12.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.sksinha2410.k12.models.*
import kotlinx.coroutines.tasks.await

class FirestoreRepository {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Attendance
    suspend fun addAttendance(attendance: Attendance): Result<String> {
        return try {
            val docRef = firestore.collection("attendance").document()
            val attendanceWithId = attendance.copy(attendanceId = docRef.id)
            docRef.set(attendanceWithId).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAttendanceByStudent(studentId: String): Result<List<Attendance>> {
        return try {
            val snapshot = firestore.collection("attendance")
                .whereEqualTo("studentId", studentId)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()
            val attendanceList = snapshot.toObjects(Attendance::class.java)
            Result.success(attendanceList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAttendanceByClass(className: String): Result<List<Attendance>> {
        return try {
            val snapshot = firestore.collection("attendance")
                .whereEqualTo("className", className)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()
            val attendanceList = snapshot.toObjects(Attendance::class.java)
            Result.success(attendanceList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Homework
    suspend fun addHomework(homework: Homework): Result<String> {
        return try {
            val docRef = firestore.collection("homework").document()
            val homeworkWithId = homework.copy(homeworkId = docRef.id)
            docRef.set(homeworkWithId).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getHomeworkByClass(className: String): Result<List<Homework>> {
        return try {
            val snapshot = firestore.collection("homework")
                .whereEqualTo("className", className)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()
            val homeworkList = snapshot.toObjects(Homework::class.java)
            Result.success(homeworkList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Notices
    suspend fun addNotice(notice: Notice): Result<String> {
        return try {
            val docRef = firestore.collection("notices").document()
            val noticeWithId = notice.copy(noticeId = docRef.id)
            docRef.set(noticeWithId).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getNotices(className: String = ""): Result<List<Notice>> {
        return try {
            // Note: For better performance with large datasets, consider creating a composite index
            // on (targetAudience, createdAt) in Firestore Console
            val query = if (className.isEmpty()) {
                firestore.collection("notices")
                    .whereEqualTo("targetAudience", "ALL")
            } else {
                firestore.collection("notices")
                    .whereIn("targetAudience", listOf("ALL", className))
            }
            val snapshot = query.orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()
            val noticeList = snapshot.toObjects(Notice::class.java)
            Result.success(noticeList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Exams
    suspend fun addExam(exam: Exam): Result<String> {
        return try {
            val docRef = firestore.collection("exams").document()
            val examWithId = exam.copy(examId = docRef.id)
            docRef.set(examWithId).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getExamsByClass(className: String): Result<List<Exam>> {
        return try {
            val snapshot = firestore.collection("exams")
                .whereEqualTo("className", className)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()
            val examList = snapshot.toObjects(Exam::class.java)
            Result.success(examList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Fees
    suspend fun addFee(fee: Fee): Result<String> {
        return try {
            val docRef = firestore.collection("fees").document()
            val feeWithId = fee.copy(feeId = docRef.id)
            docRef.set(feeWithId).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getFeesByStudent(studentId: String): Result<List<Fee>> {
        return try {
            val snapshot = firestore.collection("fees")
                .whereEqualTo("studentId", studentId)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()
            val feeList = snapshot.toObjects(Fee::class.java)
            Result.success(feeList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Messages
    suspend fun sendMessage(message: Message): Result<String> {
        return try {
            val docRef = firestore.collection("messages").document()
            val messageWithId = message.copy(messageId = docRef.id)
            docRef.set(messageWithId).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMessages(userId: String): Result<List<Message>> {
        return try {
            val snapshot = firestore.collection("messages")
                .whereEqualTo("receiverId", userId)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .await()
            val messageList = snapshot.toObjects(Message::class.java)
            Result.success(messageList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getConversation(userId: String, otherUserId: String): Result<List<Message>> {
        return try {
            val snapshot = firestore.collection("messages")
                .whereIn("senderId", listOf(userId, otherUserId))
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .get()
                .await()
            val messages = snapshot.toObjects(Message::class.java)
                .filter { 
                    (it.senderId == userId && it.receiverId == otherUserId) ||
                    (it.senderId == otherUserId && it.receiverId == userId)
                }
            Result.success(messages)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Students
    suspend fun getStudent(studentId: String): Result<Student> {
        return try {
            val document = firestore.collection("students")
                .document(studentId)
                .get()
                .await()
            val student = document.toObject(Student::class.java)
            student?.let {
                Result.success(it)
            } ?: Result.failure(Exception("Student not found"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getStudentsByParent(parentId: String): Result<List<Student>> {
        return try {
            val snapshot = firestore.collection("students")
                .whereEqualTo("parentId", parentId)
                .get()
                .await()
            val studentList = snapshot.toObjects(Student::class.java)
            Result.success(studentList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getStudentsByClass(className: String): Result<List<Student>> {
        return try {
            val snapshot = firestore.collection("students")
                .whereEqualTo("className", className)
                .orderBy("rollNumber")
                .get()
                .await()
            val studentList = snapshot.toObjects(Student::class.java)
            Result.success(studentList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
