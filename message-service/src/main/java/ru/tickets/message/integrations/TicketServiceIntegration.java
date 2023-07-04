package ru.tickets.message.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tickets.api.auth.core.TicketDto;


@Component
@RequiredArgsConstructor
public class TicketServiceIntegration {
    private final WebClient userServiceWebClient;


    public TicketDto getTicketById(Long id){
        return userServiceWebClient.get()
                .uri("/ticket/" + id)
                .retrieve()
                .bodyToMono(TicketDto.class)
                .block();
    }
}
