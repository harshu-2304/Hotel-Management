package com.hotel.view;


import java.io.FileInputStream;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.IOException;

public class FirebaseIntializer {
    public static Firestore db;

    public static void initialize() {
        try {
            // FileInputStream serviceAccount = new FileInputStream(
            //         "management\\src\\main\\resources\\smartdien-e1144-firebase-adminsdk-fbsvc-90704af009.json");
            FileInputStream serviceAccount = new FileInputStream(
                    "src\\main\\resources\\smartdien-e2b63-firebase-adminsdk-fbsvc-9e37e64a60.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
            db = FirestoreClient.getFirestore();
            System.out.println("Firebase Initialized Successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}