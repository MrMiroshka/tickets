package ru.tickets.core.configs;

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

import ru.tickets.core.properties.SettingServiceIntegrationProperties;



@Configuration
@EnableConfigurationProperties(
        SettingServiceIntegrationProperties.class
)
@RequiredArgsConstructor
public class AppConfig {
    private final SettingServiceIntegrationProperties settingServiceIntegrationProperties;

    @Bean
    public WebClient settingServiceWebClient() {
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, settingServiceIntegrationProperties.getConnectTimeout())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(settingServiceIntegrationProperties.getReadTimeout()));
                    connection.addHandlerLast(new WriteTimeoutHandler(settingServiceIntegrationProperties.getWriteTimeout()));
                });
        HttpClient httpClient = HttpClient.from(tcpClient);

        return WebClient.builder()
                .baseUrl(settingServiceIntegrationProperties.getUrl())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
