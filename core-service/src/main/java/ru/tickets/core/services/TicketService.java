package ru.tickets.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ru.gb.ticket.api.TicketDto;
import ru.tickets.core.entities.Ticket;
import ru.tickets.core.repositories.TicketRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;

    public List<Ticket> getAllTasks(){
        return ticketRepository.findAll();
    }

    public Optional<Ticket> findById(Long id) {
        return ticketRepository.findById(id);
    }


    //FIXME: 12.06.23 доработать с интеграцией новых сервисов
    public void createTicket(TicketDto ticketDto) {
        Ticket ticket = new Ticket(
                ticketDto.getNameTicket(),
                ticketDto.getTextTicket(),
                1, //"Запланирована"
                ticketDto.getPriorityTicket());
        ticketRepository.save(ticket);
    }
}
