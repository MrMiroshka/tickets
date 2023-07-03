package ru.tickets.api.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDtoRequest {
    private Long id;
    private String comment;
    private String author;
    private Long ticketId;
    private Long commentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
