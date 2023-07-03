package ru.tickets.message.converters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.tickets.api.comment.dto.CommentDtoRequest;
import ru.tickets.message.data.Comment;
import ru.tickets.message.integrations.UserServiceIntegration;

import java.time.LocalDateTime;
@Slf4j
@Component
@RequiredArgsConstructor
public class CommentConverter {

    private final UserServiceIntegration userServiceIntegration;


    public Comment dtoRequestToEntity(CommentDtoRequest commentDtoRequest) {
        return new Comment(
                commentDtoRequest.getId(),
                commentDtoRequest.getComment(),
                userServiceIntegration.getUserByUsername(commentDtoRequest.getAuthor()).getId(),
                commentDtoRequest.getTicketId(),
                commentDtoRequest.getCommentId(),
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    public CommentDtoRequest entityToDtoRequest(Comment comment) {
        return new CommentDtoRequest(
                comment.getId(),
                comment.getComment(),
                userServiceIntegration.getUserById(comment.getAuthor()).getName(),
                comment.getTicketId(),
                comment.getCommentId(),
                comment.getCreatedAt(),
                comment.getUpdatedAt());
    }
}
