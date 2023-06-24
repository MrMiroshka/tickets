package ru.tickets.core.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import ru.tickets.api.settings.StatusDto;


@Component
@RequiredArgsConstructor
public class SettingServiceIntegration {

    private final WebClient settingServiceWebClient;


    public StatusDto getDefaultStatus(){
        return settingServiceWebClient.get()
                .uri("/api/v1/status/default")
                .retrieve()
                .bodyToMono(StatusDto.class)
                .block();
    }


}
