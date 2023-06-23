package ru.tickets.core.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.gb.ticket.api.settings.CommentDto;
import ru.gb.ticket.api.settings.StatusDto;
import ru.gb.ticket.api.userservice.UserDto;


@Component
@RequiredArgsConstructor
public class UserServiceIntegration {

    private final WebClient userServiceWebClient;


    public UserDto getUserByUsername(String username){
        return userServiceWebClient.get()
                .uri("/user/" + username)
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
    }


}
