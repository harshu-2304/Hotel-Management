package com.hotel.view;


import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FirebaseAuthController {

    private static final String API_KEY = "AIzaSyCfUReHdcRpUQt8VqN24DhjlROUJnbQB9U";
//     public String getCurrentUserUid() {
    
//     return this.getCurrentUserUid();
// }


    public boolean signUpWithEmailAndPassword(String email, String password) {
        try {
            URL url = new URL("https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=" + API_KEY);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String payload = String.format(
                    "{\"email\":\"%s\",\"password\":\"%s\",\"returnSecureToken\":true}",
                    email, password
            );

            OutputStream os = connection.getOutputStream();
            os.write(payload.getBytes());
            os.flush();
            os.close();

            int responseCode = connection.getResponseCode();
            return responseCode == 200;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean signInWithEmailAndPassword(String email, String password) {
        try {
            URL url = new URL("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key="+ API_KEY);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String payload = String.format(
                    "{\"email\":\"%s\",\"password\":\"%s\",\"returnSecureToken\":true}",
                    email, password
            );

            OutputStream os = connection.getOutputStream();
            os.write(payload.getBytes());
            os.flush();
            os.close();

            int responseCode = connection.getResponseCode();
            return responseCode == 200;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
