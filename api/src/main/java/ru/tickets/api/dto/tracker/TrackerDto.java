package ru.tickets.api.dto.tracker;

import java.time.LocalDateTime;

public interface TrackerDto {
    public Long getId();

    public void setId(Long id);

    public String getTitle();

    public void setTitle(String title);

    public LocalDateTime getCreatedAt();

    public void setCreatedAt(LocalDateTime localDateTime);

    public LocalDateTime getUpdatedAt();

    public void setUpdatedAt(LocalDateTime localDateTime);
}
