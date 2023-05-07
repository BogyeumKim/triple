package com.gaerine.triple;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application-KEY.properties")
public class ApiKey {
    @Value("${googleapi-key}")
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }

    @Override
    public String toString() {
        return "ApiKey{" +
                "apiKey='" + apiKey + '\'' +
                '}';
    }
}
