package ru.tickets.message.integrations;

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
                .uri("/user/" + username)
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
    }


    public UserDto getUserById(Long id){
        return userServiceWebClient.get()
                .uri("/user/" + id)
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
    }


}
