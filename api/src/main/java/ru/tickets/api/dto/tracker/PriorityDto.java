package ru.tickets.api.dto.tracker;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriorityDto {
    private Long id;
    private String title;
    private Short priorityValue;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
