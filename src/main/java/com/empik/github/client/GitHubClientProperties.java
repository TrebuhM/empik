package com.empik.github.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "github.client")
@Getter
@Setter
public class GitHubClientProperties {

    private String baseUrl;
    private String login;
    private String token;

    private int readTimeout;
    private int connectTimeout;
}
