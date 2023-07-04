package ru.tickets.message.validators;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.tickets.api.comment.dto.CommentDtoRequest;
import ru.tickets.api.exceptions.ValidationException;
import ru.tickets.message.integrations.TicketServiceIntegration;
import ru.tickets.message.integrations.UserServiceIntegration;
import ru.tickets.message.servicies.CommentService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommentValidator {
    private final CommentService commentService;
    private final UserServiceIntegration userServiceIntegration;
    private final TicketServiceIntegration ticketServiceIntegration;

    public void validate(CommentDtoRequest commentDtoRequest) {
        List<String> errors = new ArrayList<>();
        if (commentDtoRequest.getComment().isBlank()) {
            errors.add("Комментарий не может быть пустым");
        }


        if (userServiceIntegration.getUserByUsername(commentDtoRequest.getAuthor()) == null) {
            errors.add("Комментарий не может быть создан, так как пользователь под которым Вы пытаетесь оставить комментарий, в базе не найден!");
        }

        if (ticketServiceIntegration.getTicketById(commentDtoRequest.getTicketId()) == null) {
            errors.add("Комментарий не может быть создан, так как тикет, где вы пытаетесь оставить комментарий, не существует!");
        }


        //TODO: сделать проверку - если комментарий принадлежит к ответу на другой комментарий и родительский комментарийотносится к тому же тикету что и дочерний


        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
