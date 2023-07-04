package ru.tickets.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ru.tickets.api.auth.core.TicketDto;
import ru.tickets.api.exceptions.ResourceNotFoundException;

import ru.tickets.core.entities.Ticket;
import ru.tickets.core.integrations.SettingServiceIntegration;
import ru.tickets.core.integrations.UserServiceIntegration;
import ru.tickets.core.repositories.TicketRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final SettingServiceIntegration settingServiceIntegration;
    private final UserServiceIntegration userServiceIntegration;

    public List<Ticket> getAllTasks(){
        return ticketRepository.findAll();
    }

    public Optional<Ticket> findById(Long id) {
        return ticketRepository.findById(id);
    }


    //FIXME: 24.06.23 Поле worker при создании должно быть Null, в БД оно not Null
    public void createTicket(TicketDto ticketDto, String username) {
        Ticket ticket = new Ticket(
                ticketDto.getNameTicket(),
                ticketDto.getTextTicket(),
                ticketDto.getTrackerTicket(),
                settingServiceIntegration.getDefaultStatus().getId(),
                ticketDto.getPriorityTicket(),
                1L,
                userServiceIntegration.getUserByUsername(username).getId());
        ticketRepository.save(ticket);
    }

    @Transactional
    public void updateStatus(Long ticketId, Long statusId) {
        Ticket ticket = findById(ticketId).orElseThrow((() -> new ResourceNotFoundException(String.format("Ticket '%s' not found", ticketId))));
        ticket.setStatusTicket(statusId);
        ticketRepository.save(ticket);
    }
}
