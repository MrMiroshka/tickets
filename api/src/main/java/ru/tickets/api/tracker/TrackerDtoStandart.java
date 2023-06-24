package ru.tickets.api.tracker;


import java.time.LocalDateTime;

public class TrackerDtoStandart implements TrackerDto {

    private Long id;
    private String title;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime localDateTime) {
        this.createdAt = localDateTime;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(LocalDateTime localDateTime) {
        this.updatedAt = localDateTime;
    }

    public TrackerDtoStandart(Long id, String title, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public TrackerDtoStandart(String title,Long id) {
        this.id = id;
        this.title = title;
        this.createdAt = null;
        this.updatedAt = null;
    }

}
