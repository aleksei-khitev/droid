package ru.akhitev.seng.droid.google.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class GoogleConnectionService{
    private HttpTransport httpTransport;
    private JsonFactory jsonFactory;
    private GoogleClientSecrets clientSecrets;
    private String authorizationCode;
    private String sourceUrl;
    private Credential credential;
    private Environment env;

    public GoogleConnectionService(Environment env) {
        this.env = env;
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
        jsonFactory = JacksonFactory.getDefaultInstance();
    }


    private InputStream getSecretFile() throws FileNotFoundException {
        String credentialsJsonFileName = env.getProperty("GOOGLE_CREDENTIALS_JSON");
        if (credentialsJsonFileName == null) {
            credentialsJsonFileName = "/opt/seng/credentials.json";
        }
        InputStream resource = new FileInputStream(credentialsJsonFileName);
        return resource;
    }

    public GoogleClientSecrets getClientSecrets() {
        if (clientSecrets == null) {
            try {
                InputStreamReader clientSecretsReader = new InputStreamReader(getSecretFile());
                clientSecrets = GoogleClientSecrets.load(jsonFactory, clientSecretsReader);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return clientSecrets;
    }

    public Credential getCredentials() {
        return credential;
    }

    public boolean exchangeCode(String code) {
        this.authorizationCode = code;

        // Step 2: Exchange --> exchange code for tokens
        boolean result = false;
        String callbackUri = clientSecrets.getDetails().getRedirectUris().get(0);
        GoogleTokenResponse response;
        try {
            response = new GoogleAuthorizationCodeTokenRequest(httpTransport, jsonFactory,
                    clientSecrets.getDetails().getClientId(),
                    clientSecrets.getDetails().getClientSecret(), code, callbackUri).execute();

            // Build a new GoogleCredential instance and return it.
            credential = new GoogleCredential.Builder()
                    .setClientSecrets(clientSecrets)
                    .setJsonFactory(jsonFactory)
                    .setTransport(httpTransport)
                    .build()
                    .setAccessToken(response.getAccessToken())
                    .setRefreshToken(response.getRefreshToken());
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String redirectUrl) {
        this.sourceUrl = redirectUrl;
    }

    public String getRedirectUrl() {
        if(clientSecrets != null) {
            return clientSecrets.getDetails().getRedirectUris().get(0);
        }
        return null;
    }

    HttpTransport getHttpTransport() {
        return httpTransport;
    }

    JsonFactory getJsonFactory() {
        return jsonFactory;
    }

    Credential getCredential() {
        return credential;
    }
}
