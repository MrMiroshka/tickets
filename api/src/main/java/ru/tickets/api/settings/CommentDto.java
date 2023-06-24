package ru.tickets.api.settings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long idComment;
    private String author;
    private int ticketId;
    private int commentId;
    private String textComment;
}
