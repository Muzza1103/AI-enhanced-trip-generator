package com.l3g1.apitraveller.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ChatGPTConfiguration {
    //Configuration class for integrating ChatGPT with external services.

    // Injects the OpenAI API key from application.properties file
    @Value("${openai.api.key}")
    String openaiApiKey;

    // Configures RestTemplate to include OpenAI API key in the request headers
    @Bean
    public RestTemplate template(){
        RestTemplate restTemplate=new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + openaiApiKey);
            return execution.execute(request, body);
        });
        return restTemplate;
    }
}
