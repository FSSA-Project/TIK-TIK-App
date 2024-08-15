package com.fssa.todo.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initializeFirebase() {
        try {


//            String filePath = "/path/to/google-services.json"; // This is for checking the local


            FileInputStream serviceAccount = new FileInputStream("/etc/secrets/serviceAccountKey.json");

            // Read JSON from environment variable
            String json = System.getenv("FIREBASE_SERVICE_ACCOUNT_KEY");
            if (json == null || json.isEmpty()) {
                throw new IllegalStateException("FIREBASE_SERVICE_ACCOUNT_KEY environment variable is not set");
            }


            //  TODO: need to understand
//            ByteArrayInputStream serviceAccount = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
