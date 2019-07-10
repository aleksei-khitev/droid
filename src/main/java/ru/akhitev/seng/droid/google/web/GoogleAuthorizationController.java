package ru.akhitev.seng.droid.google.web;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.services.sheets.v4.SheetsScopes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.akhitev.seng.droid.google.service.GoogleConnectionService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

@RestController
public class GoogleAuthorizationController {
    private GoogleConnectionService connection;

    @Autowired
    public GoogleAuthorizationController(GoogleConnectionService connection) {
        this.connection = connection;
    }

    @RequestMapping(value = "/ask", method = RequestMethod.GET)
    public void ask(HttpServletResponse response) throws IOException {
        String url = new GoogleAuthorizationCodeRequestUrl(connection.getClientSecrets(),
                connection.getRedirectUrl(), Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY))
                .setApprovalPrompt("force")
                .build();
        System.out.println("Go to the following link in your browser: ");
        System.out.println(url);
        response.sendRedirect(url);
    }
}
