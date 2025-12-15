# 📋 Setup Checklist for K12 Parent-Teacher App

## ✅ Pre-Setup (Already Done!)
- [x] Repository cloned
- [x] Firebase project created
- [x] `google-services.json` added to `app/` directory
- [x] Firebase connected

## 🔧 Firebase Console Setup

### Enable Firebase Services
Go to [Firebase Console](https://console.firebase.google.com/) → Your Project

- [ ] **Authentication**
  - [ ] Navigate to Authentication → Sign-in method
  - [ ] Enable "Email/Password" provider
  - [ ] Save changes

- [ ] **Cloud Firestore**
  - [ ] Navigate to Firestore Database
  - [ ] Create database
  - [ ] Choose "Start in test mode" (for development)
  - [ ] Select your region
  - [ ] Click "Enable"

- [ ] **Cloud Messaging**
  - [ ] Should be automatically enabled
  - [ ] Verify in Project Settings → Cloud Messaging

### Optional: Set Up Firestore Security Rules
- [ ] Navigate to Firestore Database → Rules
- [ ] Copy rules from SETUP_GUIDE.md
- [ ] Publish rules

## 🏗️ Build Setup

### In Android Studio
- [ ] Open Android Studio
- [ ] Select "Open an Existing Project"
- [ ] Navigate to project directory
- [ ] Click "OK"
- [ ] Wait for Gradle sync (may take a few minutes)
- [ ] Wait for dependencies to download

### Verify Setup
- [ ] No red errors in build.gradle files
- [ ] Project structure shows all files
- [ ] Android SDK is installed
- [ ] Gradle sync successful

## 📱 Testing Setup

### Create Android Emulator (if needed)
- [ ] Open AVD Manager (Tools → AVD Manager)
- [ ] Click "Create Virtual Device"
- [ ] Select a device (e.g., Pixel 5)
- [ ] Select system image (API 26 or higher)
- [ ] Click "Finish"

### Build and Run
- [ ] Click green "Run" button (▶️) in Android Studio
- [ ] Select your emulator or device
- [ ] Wait for app to build and install
- [ ] App should launch automatically

## 🧪 Initial Testing

### Test Authentication
- [ ] App opens to Login screen
- [ ] Click "Register" button
- [ ] Fill in registration form:
  - [ ] Name: Test Teacher
  - [ ] Email: teacher@test.com
  - [ ] Phone: 1234567890
  - [ ] Role: Teacher
  - [ ] Password: test123 (min 6 chars)
  - [ ] Confirm Password: test123
- [ ] Click "Register" button
- [ ] Verify you're redirected to Login screen
- [ ] Login with teacher@test.com / test123
- [ ] Verify Teacher Dashboard appears

### Test Teacher Dashboard
- [ ] See welcome message with your name
- [ ] See 5 feature cards:
  - [ ] Attendance
  - [ ] Homework
  - [ ] Notices
  - [ ] Exams
  - [ ] Messages
  - [ ] Logout
- [ ] Click each card (should show toast message)
- [ ] Click Logout
- [ ] Verify you're back at Login screen

### Test Parent Account
- [ ] Click "Register" from Login screen
- [ ] Create parent account:
  - [ ] Name: Test Parent
  - [ ] Email: parent@test.com
  - [ ] Phone: 9876543210
  - [ ] Role: Parent
  - [ ] Password: test123
- [ ] Login with parent account
- [ ] Verify Parent Dashboard appears
- [ ] See 6 feature cards (includes Fees card)
- [ ] Test navigation and logout

## 🔍 Verify Firebase Integration

### Check Firebase Console
- [ ] Go to Firebase Console → Authentication
- [ ] See your test users (teacher@test.com, parent@test.com)
- [ ] Go to Firestore Database
- [ ] See "users" collection with your user data

### Test Auto-Login
- [ ] Login to app
- [ ] Close app completely
- [ ] Re-open app
- [ ] Verify you're automatically logged in
- [ ] Should go straight to dashboard

## 🐛 Troubleshooting

### Build Fails
- [ ] Run `./gradlew clean`
- [ ] File → Invalidate Caches / Restart
- [ ] Rebuild project

### Login Fails
- [ ] Check internet connection
- [ ] Verify Email/Password is enabled in Firebase Console
- [ ] Check Firebase Console for error logs
- [ ] Verify password is at least 6 characters

### App Crashes
- [ ] Check Logcat in Android Studio for errors
- [ ] Verify `google-services.json` has correct package name
- [ ] Ensure all Firebase services are enabled
- [ ] Try reinstalling the app

## 📊 Next Steps After Testing

### Add Sample Data (Optional)
You can manually add data in Firebase Console:

- [ ] Create sample students in Firestore
- [ ] Add attendance records
- [ ] Post homework assignments
- [ ] Create notices
- [ ] Schedule exams

### Explore Features
- [ ] Test messaging between users
- [ ] Test notification system
- [ ] Explore all dashboard features
- [ ] Test with different user roles

### Development
- [ ] Read FEATURES.md for implementation details
- [ ] Check repository methods in FirestoreRepository.kt
- [ ] Explore data models in models/ package
- [ ] Review UI layouts in res/layout/

## 📚 Documentation References

When you need help:
- **Quick Start**: SETUP_GUIDE.md
- **Build Issues**: BUILD.md
- **Feature Details**: FEATURES.md
- **Project Overview**: README.md
- **Summary**: SUMMARY.md

## ✅ Completion Checklist

Mark these when everything works:
- [ ] Firebase services enabled
- [ ] App builds successfully
- [ ] Can register new users
- [ ] Can login with credentials
- [ ] Teacher dashboard works
- [ ] Parent dashboard works
- [ ] Logout works
- [ ] Auto-login works
- [ ] Users visible in Firebase Console
- [ ] Ready to use!

---

## 🎉 Congratulations!

Once all items are checked, your K12 Parent-Teacher Communication App is fully set up and ready to use!

**Need Help?**
- Check the documentation files
- Review Firebase Console for errors
- Check Android Studio Logcat for crash logs
- Verify all Firebase services are enabled

**Happy Coding! 🚀**
