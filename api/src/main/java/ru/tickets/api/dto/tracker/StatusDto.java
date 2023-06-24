package ru.tickets.api.dto.tracker;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusDto {
    private Long id;
    private String title;
    private Boolean defaultStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
