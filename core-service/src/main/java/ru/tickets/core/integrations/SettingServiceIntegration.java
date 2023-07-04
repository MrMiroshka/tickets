package ru.tickets.core.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import ru.tickets.api.dto.tracker.StatusDto;
import ru.tickets.api.exceptions.ResourceNotFoundException;


import java.util.Optional;


@Component
@RequiredArgsConstructor
public class SettingServiceIntegration {

    private final WebClient settingServiceWebClient;


    public StatusDto getDefaultStatus(){
        StatusDto statusDto = settingServiceWebClient.get()
                .uri("/api/v1/status/default")
                .retrieve()
                .bodyToMono(StatusDto.class)
                .block();
        return statusDto;
    }



}
