package ru.akhitev.seng.droid.google.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.akhitev.seng.droid.google.service.GoogleConnectionService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class GoogleCallbackController {
    private GoogleConnectionService connection;

    @Autowired
    public GoogleCallbackController(GoogleConnectionService connection) {
        this.connection = connection;
    }

    @RequestMapping(value = "/oauth2callback", method = RequestMethod.GET)
    public void callback(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
        if (connection.exchangeCode(code)) {
            response.sendRedirect(connection.getSourceUrl());
        } else {
            response.sendRedirect("/error");
        }
    }
}
