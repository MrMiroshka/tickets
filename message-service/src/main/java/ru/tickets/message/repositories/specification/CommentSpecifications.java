package ru.tickets.message.repositories.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.tickets.message.data.Comment;

public class CommentSpecifications {
    public static Specification<Comment> commentFrotmIdTicket(Long idTicket) {
        return (Specification<Comment>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .equal(root.get("ticketId"),idTicket.toString());
        //like(root.get("ticketId"), String.format("s", idTicket));
    }
}
