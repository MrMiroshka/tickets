package ru.tickets.message.servicies;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.tickets.api.exceptions.ResourceNotFoundException;
import ru.tickets.api.exceptions.ValidationException;
import ru.tickets.message.data.Comment;
import ru.tickets.message.repositories.CommentDao;
import ru.tickets.message.repositories.specification.CommentSpecifications;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentDao commentDao;
    public List<Comment> findAll(Long id) {
        return commentDao.findByTicketId(id);
    }

    public Optional<Comment> findById(Long id) {
        return Optional.ofNullable(commentDao.findById(id).orElseThrow(() -> new ResourceNotFoundException
                ("Такой комментарий не найден  id =  " + id)));
    }

    public Comment addComment(Comment comment) {
        return this.commentDao.save(comment);
    }

    public Page<Comment> find(Long idTicket, Integer page, Integer pageSize) {
        Specification<Comment> spec = Specification.where(null);
        if (idTicket != null) {
            spec = spec.and(CommentSpecifications.commentFrotmIdTicket(idTicket));
        }
        return this.commentDao.findAll(spec, PageRequest.of(page - 1, pageSize));

    }

    @Transactional
    public Comment changeComment(Comment comment) {
        List<String> errors = new ArrayList<>();
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        Comment commentChange = this.commentDao.findById(comment.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Такой комментарий не найден id - " + comment.getId()));
        if (!comment.getComment().isBlank()) {
                commentChange.setComment(comment.getComment());
        } else {
            errors.add("Комментарий не может быть пустым");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        return commentChange;
    }
}
