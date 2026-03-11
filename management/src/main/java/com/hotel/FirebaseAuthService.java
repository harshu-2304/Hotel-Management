package com.hotel;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.function.Consumer;

public class FirebaseAuthService {

    // 🔐 Replace with your actual Firebase Web API Key
    private static final String API_KEY =/* "AIzaSyAt_ma1LUZLBXPlF73RHhMsQ35TXXZb3qc"*/"AIzaSyAlto4p1lOakU9Gp7i1PiL5zSdQV167kbw";

    public static void login(String email, String password, Consumer<Boolean> callback) {
        try {
            String endpoint = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + API_KEY;
            String payload = String.format(
                "{\"email\":\"%s\",\"password\":\"%s\",\"returnSecureToken\":true}",
                email, password
            );

            boolean result = sendRequest(endpoint, payload);
            callback.accept(result);
        } catch (Exception e) {
            e.printStackTrace();
            callback.accept(false);
        }
    }

    public static void signup(String email, String password, Consumer<Boolean> callback) {
        try {
            String endpoint = "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=" + API_KEY;
            String payload = String.format(
                "{\"email\":\"%s\",\"password\":\"%s\",\"returnSecureToken\":true}",
                email, password
            );

            boolean result = sendRequest(endpoint, payload);
            callback.accept(result);
        } catch (Exception e) {
            e.printStackTrace();
            callback.accept(false);
        }
    }

    // ✅ Core function to send POST request
    private static boolean sendRequest(String endpoint, String payload) throws Exception {
        URL url = new URL(endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(payload.getBytes(StandardCharsets.UTF_8));
        }

        int responseCode = conn.getResponseCode();
        Scanner scanner;

        if (responseCode == 200) {
            scanner = new Scanner(conn.getInputStream());
        } else {
            scanner = new Scanner(conn.getErrorStream());
        }

        StringBuilder response = new StringBuilder();
        while (scanner.hasNext()) {
            response.append(scanner.nextLine());
        }
        scanner.close();

        // ✅ Optional: print Firebase response (for debugging)
        System.out.println("Firebase Response: " + response);

        return responseCode == 200;
    }
}
