package ru.tickets.message.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.tickets.api.comment.dto.CommentDtoRequest;
import ru.tickets.api.exceptions.ResourceNotFoundException;
import ru.tickets.message.converters.CommentConverter;
import ru.tickets.message.data.Comment;
import ru.tickets.message.servicies.CommentService;
import ru.tickets.message.validators.CommentValidator;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1//comment")
public class CommentController {
    private final CommentService commentService;
    private final CommentConverter commentConverter;

    private final CommentValidator commentValidator;

    /**
     * Находит комментарий по его id
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Optional<CommentDtoRequest> findCommentById(@PathVariable Long id) {
        Comment comment = commentService.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Такой комментарий не найден  id =  " + id));
        return Optional.ofNullable(commentConverter.entityToDtoRequest(comment));
    }

    /**
     * Изменяет комментарий по его id
     *
     * @param commentDtoRequest - сериализированный из json в объект commentDtoRequest с новыми данными
     * @return - возвращает измененный комментарий
     */
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Optional<CommentDtoRequest> updateComment(@RequestBody CommentDtoRequest commentDtoRequest) {
       commentValidator.validate(commentDtoRequest);
        return Optional.ofNullable(commentConverter.entityToDtoRequest
                (this.commentService.changeComment
                        (commentConverter.dtoRequestToEntity(commentDtoRequest))));
    }

    /**
     * Возвращает весь список комментариев
     * @id - id тикета
     * @return - возвращает список комментариев, если список существует. Иначе Optional<> содержащий Null
     */
    @GetMapping("/findAll/{id}")
    public Optional<List<CommentDtoRequest>> findCommentAll(@PathVariable Long id) {
        List<Comment> comments = commentService.findAll(id);
        return Optional.ofNullable(comments.stream().map(t -> commentConverter.entityToDtoRequest(t)).toList());
    }

    /**
     * Возвращает пагинированный список комментариев указанного тикета
     *
     * @param page         - номер страницы
     * @param idTicket     - id тикета
     * @param pageSize     - количество элементов на странице
     * @return
     */
    @GetMapping
    public Page<CommentDtoRequest> getComment(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "idTicket", required = false) Long idTicket,
            @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize
    ) {
        if (page < 1) {
            page = 1;
        }

        return commentService.find(idTicket, page, pageSize).map(p -> commentConverter.entityToDtoRequest(p));
    }

    /**
     * Создает новый комметнарий, при удачном добавлении возвращает добавленный комметнарий
     *
     * @param commentDtoRequest - сериализированный из json в объект commentDtoRequest
     * @return
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Optional<CommentDtoRequest> postComment(@RequestBody CommentDtoRequest commentDtoRequest) {
        commentValidator.validate(commentDtoRequest);
        commentDtoRequest.setId(null);
        Comment comment = this.commentService.addComment(commentConverter.dtoRequestToEntity(commentDtoRequest));
        return Optional.ofNullable(commentConverter.entityToDtoRequest(comment));
    }
}
