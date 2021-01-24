package com.empik.github.client;

import com.empik.github.users.GitHubUserDTO;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import java.util.concurrent.TimeUnit;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Component
public class GitHubClient {

    private final WebClient webClient;

    @Autowired
    public GitHubClient(final GitHubClientProperties gitHubClientProperties) {
        val httpClient = HttpClient.create().tcpConfiguration(tcpClient -> {
            tcpClient =
                tcpClient.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, gitHubClientProperties.getConnectTimeout());
            tcpClient = tcpClient.doOnConnected(conn -> conn.addHandlerLast(
                new ReadTimeoutHandler(gitHubClientProperties.getReadTimeout(), TimeUnit.MILLISECONDS)));
            return tcpClient;
        });
        val connector = new ReactorClientHttpConnector(httpClient);

        this.webClient = WebClient.builder().baseUrl(gitHubClientProperties.getBaseUrl()).clientConnector(connector)
            .defaultHeaders(httpHeaders -> httpHeaders
                .setBasicAuth(gitHubClientProperties.getLogin(), gitHubClientProperties.getToken())).build();
    }

    public GitHubUserDTO getUserByLogin(final String login) {
        val uri = String.format("/users/%s", login);
        val gitHubUser =
            webClient.get().uri(uri).retrieve().onStatus(httpStatus -> httpStatus.value() == 404, clientResponse -> {
                throw new GitHubClientException("User not found.", clientResponse.statusCode());
            }).onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                throw new GitHubClientException("Request cannot be performed.", clientResponse.statusCode());
            }).onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                throw new GitHubClientException("External server error", clientResponse.statusCode());
            }).bodyToMono(GitHubUserDTO.class);

        return gitHubUser.block();
    }
}
