package ru.tickets.core.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.tickets.core.entities.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
