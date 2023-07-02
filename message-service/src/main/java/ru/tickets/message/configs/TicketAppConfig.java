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
import ru.tickets.message.properties.TicketServiceIntegrationProperties;


@Configuration
@EnableConfigurationProperties(
        TicketServiceIntegrationProperties.class
)
@RequiredArgsConstructor
public class TicketAppConfig {
    private final TicketServiceIntegrationProperties ticketServiceIntegrationProperties;

    @Bean
    public WebClient ticketServiceWebClient() {
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, ticketServiceIntegrationProperties.getConnectTimeout())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(ticketServiceIntegrationProperties.getReadTimeout()));
                    connection.addHandlerLast(new WriteTimeoutHandler(ticketServiceIntegrationProperties.getWriteTimeout()));
                });
        HttpClient httpClient = HttpClient.from(tcpClient);

        return WebClient.builder()
                .baseUrl(ticketServiceIntegrationProperties.getUrl())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
