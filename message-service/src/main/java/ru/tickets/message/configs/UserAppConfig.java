package ru.tickets.message.configs;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;
import ru.tickets.message.properties.UserServiceIntegrationProperties;


@Configuration
@EnableConfigurationProperties(
        UserServiceIntegrationProperties.class
)
@RequiredArgsConstructor
public class UserAppConfig {
    private final UserServiceIntegrationProperties userServiceIntegrationProperties;

    @Bean
    public WebClient userServiceWebClient() {
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, userServiceIntegrationProperties.getConnectTimeout())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(userServiceIntegrationProperties.getReadTimeout()));
                    connection.addHandlerLast(new WriteTimeoutHandler(userServiceIntegrationProperties.getWriteTimeout()));
                });
        HttpClient httpClient = HttpClient.from(tcpClient);

        return WebClient.builder()
                .baseUrl(userServiceIntegrationProperties.getUrl())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
