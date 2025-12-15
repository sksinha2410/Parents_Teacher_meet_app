# Build Instructions

## Prerequisites
- Android Studio (Arctic Fox or later)
- JDK 11 or higher
- Internet connection for downloading dependencies

## Building the Project

### Option 1: Using Android Studio (Recommended)
1. Open Android Studio
2. Select "Open an Existing Project"
3. Navigate to the project directory and select it
4. Wait for Gradle sync to complete
5. Click "Build" > "Make Project" or press Ctrl+F9 (Cmd+F9 on Mac)
6. To run on an emulator or device, click the "Run" button or press Shift+F10 (Ctrl+R on Mac)

### Option 2: Using Command Line
```bash
# On Linux/Mac
./gradlew assembleDebug

# On Windows
gradlew.bat assembleDebug
```

## Troubleshooting

### Network Issues
If you encounter network issues when downloading dependencies:
1. Ensure you have a stable internet connection
2. Check if you're behind a corporate proxy
3. Configure proxy settings in `gradle.properties` if needed:
```
systemProp.http.proxyHost=your.proxy.host
systemProp.http.proxyPort=8080
systemProp.https.proxyHost=your.proxy.host
systemProp.https.proxyPort=8080
```

### Gradle Sync Failures
If Gradle sync fails:
1. File > Invalidate Caches / Restart
2. Delete the `.gradle` directory in the project root
3. Click "File" > "Sync Project with Gradle Files"

### Build Errors
If you encounter build errors:
1. Clean the project: `./gradlew clean`
2. Rebuild: `./gradlew assembleDebug`
3. Check that you have the latest Android SDK tools installed

## Firebase Configuration

### Important: Replace Placeholder Configuration
The included `google-services.json` is a placeholder. You MUST replace it with your actual Firebase configuration:

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Select your project (or create a new one)
3. Add an Android app with package name: `com.sksinha2410.k12`
4. Download the `google-services.json` file
5. Replace `app/google-services.json` with your downloaded file

### Firebase Services Required
Enable these services in your Firebase project:
- **Authentication**: Enable Email/Password sign-in
- **Cloud Firestore**: Create a database in test mode or production mode
- **Cloud Messaging**: Enable for push notifications

### Firestore Collections
The app expects the following Firestore collections:
- `users` - User profiles
- `students` - Student information
- `attendance` - Attendance records
- `homework` - Homework assignments
- `notices` - School notices
- `exams` - Exam schedules
- `fees` - Fee information
- `messages` - Messages between parents and teachers

## Running the App

### On Emulator
1. Open AVD Manager in Android Studio
2. Create a new virtual device (Android 8.0 or higher)
3. Start the emulator
4. Click the "Run" button in Android Studio

### On Physical Device
1. Enable Developer Options on your device
2. Enable USB Debugging
3. Connect your device via USB
4. Click the "Run" button and select your device

## Testing User Roles

### Creating Test Users
1. Run the app
2. Click "Register"
3. Create a user with role "Parent" or "Teacher"
4. Login with the credentials

### Parent Features
- View attendance records
- View homework assignments
- View school notices
- View exam schedules
- View fee information
- Send messages to teachers

### Teacher Features
- Mark student attendance
- Assign homework
- Post notices
- Schedule exams
- Send messages to parents

## APK Location
After successful build, the debug APK will be located at:
```
app/build/outputs/apk/debug/app-debug.apk
```

## Release Build
To create a release build:
```bash
./gradlew assembleRelease
```
Note: You'll need to configure signing keys for release builds.
