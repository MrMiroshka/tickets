package ru.tickets.core.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tickets.api.userservice.UserDto;


@Component
@RequiredArgsConstructor
public class UserServiceIntegration {

    private final WebClient userServiceWebClient;


    public UserDto getUserByUsername(String username){
        return userServiceWebClient.get()
                .uri("/user/get/" + username)
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
    }


}
