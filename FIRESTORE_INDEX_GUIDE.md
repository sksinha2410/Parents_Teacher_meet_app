# Firestore Index Configuration Guide

## Overview
This document describes the Firestore composite indexes required for the K12 Parent-Teacher Communication App to function correctly.

## Required Composite Indexes

### 1. Students Collection Index
**Collection**: `students`
**Fields**:
- `className` (Ascending)
- `rollNumber` (Ascending)

**Reason**: This index is required for the `getStudentsByClass` query in `FirestoreRepository.kt` which filters students by class name and orders them by roll number.

**Query Example**:
```kotlin
firestore.collection("students")
    .whereEqualTo("className", className)
    .orderBy("rollNumber")
```

## How to Create Indexes

### Option 1: Firebase Console (Recommended)
1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Select your project
3. Navigate to **Firestore Database** → **Indexes**
4. Click **Create Index**
5. Fill in the following:
   - **Collection ID**: `students`
   - **Fields to index**:
     - Field path: `className`, Order: Ascending
     - Field path: `rollNumber`, Order: Ascending
   - **Query scope**: Collection
6. Click **Create**

### Option 2: Using Firebase CLI
Create a file named `firestore.indexes.json` in your project root:

```json
{
  "indexes": [
    {
      "collectionGroup": "students",
      "queryScope": "COLLECTION",
      "fields": [
        {
          "fieldPath": "className",
          "order": "ASCENDING"
        },
        {
          "fieldPath": "rollNumber",
          "order": "ASCENDING"
        }
      ]
    }
  ],
  "fieldOverrides": []
}
```

Then deploy using:
```bash
firebase deploy --only firestore:indexes
```

### Option 3: Auto-Generate from Error Link
When you run a query that requires an index, Firestore will provide an error message with a link to create the index automatically. Simply click the link and confirm.

## Troubleshooting

### Error: "The query requires an index"
If you see this error when trying to load students in the Mark Attendance screen:
1. Check the error message for a link to create the index
2. Click the link to auto-create the index
3. Wait a few minutes for the index to build
4. Try the query again

### Error: "Index creation failed"
- Ensure you have proper permissions in Firebase Console
- Verify the collection name is correct (`students`)
- Check that the field names match exactly (`className`, `rollNumber`)

### Index Building Time
- Small datasets (< 100 documents): Usually instant
- Medium datasets (100-10,000 documents): A few minutes
- Large datasets (> 10,000 documents): Can take several hours

## Important Notes

1. **rollNumber Data Type**: The `rollNumber` field has been changed from String to Int. This ensures:
   - Correct numeric ordering (1, 2, 3, ..., 10) instead of lexicographic ordering ("1", "10", "2")
   - Proper index creation and query performance

2. **Existing Data Migration**: If you have existing student records with String rollNumbers, you'll need to migrate them to Int:
   ```javascript
   // Example Firestore migration script (run in Firebase Console)
   db.collection('students').get().then(snapshot => {
     snapshot.forEach(doc => {
       const data = doc.data();
       if (typeof data.rollNumber === 'string') {
         doc.ref.update({
           rollNumber: parseInt(data.rollNumber, 10)
         });
       }
     });
   });
   ```

3. **Development vs Production**: Create indexes in both development and production environments separately.

4. **Query Optimization**: Composite indexes significantly improve query performance, especially for large datasets.

## Additional Resources

- [Firestore Index Documentation](https://firebase.google.com/docs/firestore/query-data/indexing)
- [Firestore Limits and Quotas](https://firebase.google.com/docs/firestore/quotas)
- [Best Practices for Firestore](https://firebase.google.com/docs/firestore/best-practices)
