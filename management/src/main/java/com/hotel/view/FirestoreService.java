// package com.hotel.view;


// import java.util.*;
// import java.util.concurrent.ExecutionException;

// import com.google.api.core.ApiFuture;
// import com.google.cloud.firestore.DocumentSnapshot;
// import com.google.cloud.firestore.Firestore;
// import com.google.cloud.firestore.QueryDocumentSnapshot;
// import com.google.cloud.firestore.QuerySnapshot;

// public class FirestoreService {

//     private static Firestore db = FirebaseInitializer.db;

//     // CREATE or UPDATE
//     public static void setDocument(String collection, String documentId, Map<String, Object> data) {
//         db.collection(collection).document(documentId).set(data);
//     }

//     // CREATE with auto ID
//     public static void addDocument(String collection, Map<String, Object> data) {
//         db.collection(collection).add(data);
//     }

//     // READ all documents
//     public static List<QueryDocumentSnapshot> getAllDocuments(String collection) throws ExecutionException, InterruptedException {
//         ApiFuture<QuerySnapshot> query = db.collection(collection).get();
//         return query.get().getDocuments();
//     }

//     // READ single document
//     public static DocumentSnapshot getDocument(String collection, String documentId) throws ExecutionException, InterruptedException {
//         return db.collection(collection).document(documentId).get().get();
//     }

//     // UPDATE
//     public static void updateDocument(String collection, String documentId, Map<String, Object> updates) {
//         db.collection(collection).document(documentId).update(updates);
//     }

//     // DELETE
//     public static void deleteDocument(String collection, String documentId) {
//         db.collection(collection).document(documentId).delete();
//     }
// }