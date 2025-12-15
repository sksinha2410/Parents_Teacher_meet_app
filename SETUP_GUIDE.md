# K12 Parent-Teacher Communication App - Setup Guide

## Quick Start

Since you've already connected the app to your Firebase project, follow these steps to build and run the app:

### 1. Open in Android Studio
1. Launch Android Studio
2. Select **"Open an Existing Project"**
3. Navigate to this directory and click OK
4. Wait for Gradle sync to complete

### 2. Verify Firebase Configuration
✅ You've already added your `google-services.json` file

Make sure these Firebase services are enabled in your Firebase Console:
- [ ] **Authentication** → Enable Email/Password sign-in method
- [ ] **Cloud Firestore** → Create database (start in test mode or set up security rules)
- [ ] **Cloud Messaging** → Automatically enabled with Firebase

### 3. Build and Run
**In Android Studio:**
- Click the green "Run" button (▶️) in the toolbar
- Select your emulator or connected device
- Wait for the app to install and launch

**From Command Line:**
```bash
./gradlew assembleDebug
```

### 4. Test the App

#### Create Your First User
1. App opens → Click "Register"
2. Fill in the registration form:
   - Name: Test Teacher
   - Email: teacher@test.com
   - Phone: 1234567890
   - Role: Select "Teacher"
   - Password: test123
   - Confirm Password: test123
3. Click "Register"
4. You'll be redirected to login screen
5. Login with the credentials you just created

#### Explore Teacher Dashboard
After login, you'll see the Teacher Dashboard with these cards:
- 📊 **Attendance** - Mark student attendance
- 📚 **Homework** - Assign homework to students
- 📢 **Notices** - Post notices for parents
- 📝 **Exams** - Schedule and manage exams
- 💬 **Messages** - Chat with parents
- 🚪 **Logout** - Sign out

#### Create a Parent Account
1. Logout from teacher account
2. Register a new user with role "Parent"
3. Login and explore Parent Dashboard

## App Architecture

```
K12 App Structure
│
├── 🔐 Authentication Layer
│   ├── LoginActivity
│   ├── RegistrationActivity
│   └── AuthRepository (Firebase Auth)
│
├── 👨‍🏫 Teacher Module
│   └── TeacherDashboardActivity
│       ├── Mark Attendance
│       ├── Assign Homework
│       ├── Post Notices
│       ├── Schedule Exams
│       └── Send Messages
│
├── 👨‍👩‍👧‍👦 Parent Module
│   └── ParentDashboardActivity
│       ├── View Attendance
│       ├── View Homework
│       ├── View Notices
│       ├── View Exams
│       ├── View Fees
│       └── Send Messages
│
├── 💾 Data Layer
│   ├── Models (User, Student, Attendance, etc.)
│   └── FirestoreRepository (Database operations)
│
└── 🔔 Notification Service
    └── K12MessagingService (Push notifications)
```

## Firestore Database Structure

After you start using the app, Firestore will automatically create these collections:

```
Firestore Database
├── users/
│   └── {userId}
│       ├── email
│       ├── name
│       ├── role (PARENT/TEACHER)
│       ├── phoneNumber
│       └── ...
│
├── students/
│   └── {studentId}
│       ├── name
│       ├── className
│       ├── parentId
│       └── ...
│
├── attendance/
│   └── {attendanceId}
│       ├── studentId
│       ├── date
│       ├── status (PRESENT/ABSENT/LATE)
│       └── ...
│
├── homework/
│   └── {homeworkId}
│       ├── title
│       ├── subject
│       ├── dueDate
│       └── ...
│
├── notices/
│   └── {noticeId}
│       ├── title
│       ├── description
│       ├── priority
│       └── ...
│
├── exams/
│   └── {examId}
│       ├── examName
│       ├── examDate
│       ├── subject
│       └── ...
│
├── fees/
│   └── {feeId}
│       ├── studentId
│       ├── amount
│       ├── status (PAID/PENDING)
│       └── ...
│
└── messages/
    └── {messageId}
        ├── senderId
        ├── receiverId
        ├── content
        └── ...
```

## Firestore Security Rules (Recommended)

Add these rules in Firebase Console → Firestore Database → Rules:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Users can read/write their own data
    match /users/{userId} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
    }
    
    // Authenticated users can read all students
    match /students/{studentId} {
      allow read: if request.auth != null;
      allow write: if request.auth != null; // Add role check for teachers only
    }
    
    // All authenticated users can read/write (adjust based on your needs)
    match /{document=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

## Features Ready to Use

### ✅ Implemented
1. **User Registration & Login**
   - Email/password authentication
   - Role selection (Parent/Teacher)
   - Input validation
   - Auto-login for returning users

2. **Teacher Dashboard**
   - Clean card-based interface
   - 5 main feature cards
   - Easy navigation

3. **Parent Dashboard**
   - Clean card-based interface
   - 6 main feature cards
   - Easy navigation

4. **Firebase Integration**
   - Authentication ready
   - Firestore database ready
   - Cloud Messaging ready
   - All repository methods implemented

5. **Data Models**
   - User, Student, Attendance
   - Homework, Notice, Exam
   - Fee, Message
   - All with proper enums and validation

### 🔧 Next Steps for Full Functionality

The UI placeholders are in place. To make features fully functional, you would need to:

1. **Create detail activities** for each feature (e.g., AttendanceListActivity, HomeworkListActivity)
2. **Add RecyclerView adapters** to display lists of data
3. **Create forms** for adding/editing data (e.g., AddHomeworkActivity)
4. **Implement detail views** for viewing individual items

## Customization

### Change App Name
Edit `app/src/main/res/values/strings.xml`:
```xml
<string name="app_name">Your School Name</string>
```

### Change Colors
Edit `app/src/main/res/values/colors.xml`:
```xml
<color name="primary">#your_color</color>
```

### Add More Features
1. Create new Activity in `activities/` package
2. Create corresponding layout in `res/layout/`
3. Add to `AndroidManifest.xml`
4. Add navigation from dashboard

## Testing Checklist

- [ ] Register as Teacher
- [ ] Login as Teacher
- [ ] View Teacher Dashboard
- [ ] Click each dashboard card
- [ ] Logout
- [ ] Register as Parent
- [ ] Login as Parent
- [ ] View Parent Dashboard
- [ ] Click each dashboard card
- [ ] Logout
- [ ] Verify Firebase Console shows registered users

**Note for Android 13+ (API 33+):**
If testing on Android 13 or higher, you may need to grant notification permissions manually:
1. Go to device Settings → Apps → K12
2. Enable Notifications
3. Alternatively, add runtime permission request in the app for POST_NOTIFICATIONS

## Troubleshooting

### "Build failed" error
- Try: `./gradlew clean` then rebuild
- Check internet connection
- Verify `google-services.json` is in `app/` directory

### "Authentication failed" error
- Verify Email/Password is enabled in Firebase Console
- Check email format is valid
- Password must be at least 6 characters

### "Cannot resolve symbol" errors
- File → Sync Project with Gradle Files
- File → Invalidate Caches / Restart

### App crashes on launch
- Check Logcat in Android Studio for error messages
- Verify `google-services.json` matches your package name
- Ensure all Firebase services are enabled

## Support & Documentation

- **README.md** - Project overview and setup
- **BUILD.md** - Detailed build instructions
- **FEATURES.md** - Complete feature documentation
- **Firebase Docs** - https://firebase.google.com/docs

## Summary

Your app is now ready to build and test! The core infrastructure is complete:
- ✅ Authentication system
- ✅ Role-based dashboards
- ✅ Firebase integration
- ✅ Data models and repositories
- ✅ Notification service
- ✅ Clean UI with Material Design

The repository methods are all implemented, so you can start adding data to Firestore and see it in the app. Happy coding! 🚀
