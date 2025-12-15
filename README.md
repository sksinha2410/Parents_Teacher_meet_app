# K12 - Parent Teacher Communication App

A comprehensive Android application built with Kotlin and Firebase for seamless parent-teacher communication.

## Features

### Authentication
- **Firebase Authentication**: Secure login and registration for parents and teachers
- **Role-based Access**: Separate dashboards for parents and teachers

### Parent Features
- **View Attendance**: Monitor student attendance records in real-time
- **View Homework**: Access assigned homework and due dates
- **View Notices**: Receive important school notices and announcements
- **View Exam Schedules**: Stay updated with exam dates and details
- **View Fees**: Check fee status and payment history
- **Messaging**: Two-way communication with teachers

### Teacher Features
- **Mark Attendance**: Record student attendance
- **Assign Homework**: Create and assign homework to students
- **Post Notices**: Share important announcements with parents
- **Schedule Exams**: Create and manage exam schedules
- **Messaging**: Two-way communication with parents

### Technical Features
- **Firebase Firestore**: Real-time database for storing all data
- **Firebase Cloud Messaging**: Push notifications for important updates
- **Clean UI**: User-friendly Material Design interface
- **Role-based Access Control**: Secure access based on user roles

## Technology Stack

- **Language**: Kotlin
- **UI**: XML layouts with Material Design components
- **Backend**: Firebase (Authentication, Firestore, Cloud Messaging)
- **Architecture**: Repository pattern with coroutines for async operations
- **Minimum SDK**: 26 (Android 8.0)
- **Target SDK**: 34 (Android 14)

## Setup Instructions

### Prerequisites
1. Android Studio (latest version recommended)
2. Firebase project with the following services enabled:
   - Firebase Authentication
   - Cloud Firestore
   - Firebase Cloud Messaging

### Firebase Setup
1. Create a new Firebase project at [Firebase Console](https://console.firebase.google.com/)
2. Add an Android app to your Firebase project
3. Download the `google-services.json` file
4. Place the `google-services.json` file in the `app/` directory
5. Enable Email/Password authentication in Firebase Console
6. Set up Cloud Firestore with the following collections:
   - `users`
   - `students`
   - `attendance`
   - `homework`
   - `notices`
   - `exams`
   - `fees`
   - `messages`

### Firestore Security Rules (Example)
```
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Users collection
    match /users/{userId} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
    }
    
    // Other collections - adjust based on your security requirements
    match /{document=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

### Installation
1. Clone this repository
2. Open the project in Android Studio
3. Add your `google-services.json` file to the `app/` directory
4. Sync the project with Gradle files
5. Build and run the application

## Project Structure

```
app/src/main/java/com/sksinha2410/k12/
├── activities/          # All Activity classes
│   ├── LoginActivity.kt
│   ├── RegistrationActivity.kt
│   ├── ParentDashboardActivity.kt
│   └── TeacherDashboardActivity.kt
├── models/             # Data models
│   ├── User.kt
│   ├── Student.kt
│   ├── Attendance.kt
│   ├── Homework.kt
│   ├── Notice.kt
│   ├── Exam.kt
│   ├── Fee.kt
│   └── Message.kt
├── repository/         # Firebase operations
│   ├── AuthRepository.kt
│   └── FirestoreRepository.kt
├── services/          # Background services
│   └── K12MessagingService.kt
└── MainActivity.kt    # Entry point
```

## Usage

### For Parents
1. Register with email and password, selecting "Parent" as role
2. Login to access the parent dashboard
3. View student attendance, homework, notices, exams, and fees
4. Communicate with teachers through the messaging feature

### For Teachers
1. Register with email and password, selecting "Teacher" as role
2. Login to access the teacher dashboard
3. Mark attendance, assign homework, post notices, and schedule exams
4. Communicate with parents through the messaging feature

## Data Models

### User
- userId, email, name, role (PARENT/TEACHER), phoneNumber, studentIds, className

### Student
- studentId, name, className, rollNumber, parentId, dateOfBirth

### Attendance
- attendanceId, studentId, className, date, status (PRESENT/ABSENT/LATE/HALF_DAY), markedBy, remarks

### Homework
- homeworkId, title, description, subject, className, assignedBy, assignedDate, dueDate, status

### Notice
- noticeId, title, description, targetAudience, className, postedBy, postedDate, priority

### Exam
- examId, examName, subject, className, examDate, startTime, endTime, totalMarks, passingMarks, syllabus

### Fee
- feeId, studentId, className, feeType, amount, dueDate, status (PENDING/PAID/OVERDUE), paymentDate

### Message
- messageId, senderId, senderName, receiverId, receiverName, subject, content, timestamp, isRead

## Security Considerations

1. **Authentication**: All users must authenticate before accessing the app
2. **Authorization**: Role-based access ensures parents and teachers see appropriate data
3. **Data Privacy**: User data is protected by Firebase security rules
4. **Secure Communication**: All communication happens over HTTPS through Firebase

## Future Enhancements

- Add photo/document upload for homework submissions
- Implement real-time chat with typing indicators
- Add calendar view for events and exams
- Include progress reports and grade tracking
- Add multi-language support
- Implement offline mode with data synchronization

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is open source and available under the MIT License.

## Support

For issues, questions, or contributions, please open an issue in the GitHub repository.

## Notes

- The `google-services.json` file contains sensitive Firebase configuration and should never be committed to version control
- Replace the placeholder `google-services.json` with your actual Firebase configuration file
- Ensure proper Firestore security rules are set up before deploying to production
- Test thoroughly with different user roles to ensure proper access control
