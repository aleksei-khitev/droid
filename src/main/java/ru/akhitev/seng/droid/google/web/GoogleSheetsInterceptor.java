package ru.akhitev.seng.droid.google.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import ru.akhitev.seng.droid.google.service.GoogleConnectionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class GoogleSheetsInterceptor implements HandlerInterceptor {
    @Autowired
    private GoogleConnectionService connection;

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse response, Object arg2, Exception arg3) {
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse response, Object arg2, ModelAndView arg3) {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        System.out.println("Pre-handle");
        System.out.println(request.getRequestURI());

        // is connected
        if (connection.getCredentials() == null) {
            connection.setSourceUrl(request.getRequestURI());
            response.sendRedirect(request.getContextPath() + "/ask");
            return false;
        }

        return true;
    }

}
