package ru.akhitev.seng.droid.conf;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import ru.akhitev.seng.droid.google.web.GoogleSheetsInterceptor;

@Component
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private GoogleSheetsInterceptor googleSheetsInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(googleSheetsInterceptor).addPathPatterns("/progress/*");
    }
}
