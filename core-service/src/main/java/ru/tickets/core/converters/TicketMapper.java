package ru.tickets.core.converters;

import ru.gb.ticket.api.TicketDto;
import ru.tickets.core.entities.Ticket;

import java.util.List;

public class TicketMapper {
    public static TicketDto ticketDtoFromTicket(Ticket ticket){
        return new TicketDto(
                ticket.getId(),
                ticket.getNameTicket(),
                ticket.getTextTicket(),
                ticket.getTrackerTicket(),
                ticket.getStatusTicket(),
                ticket.getPriorityTicket(),
                ticket.getWorker(),
                ticket.getAuthor()
                );
    }
    public static Ticket ticketFromTicketDto(TicketDto ticket){
        return new Ticket(
                ticket.getNameTicket(),
                ticket.getTextTicket(),
                ticket.getTrackerTicket(),
                ticket.getStatusTicket(),
                ticket.getPriorityTicket(),
                ticket.getWorker(),
                ticket.getAuthor()
        );
    }

    public static List<TicketDto> ticketDtoListFromTicketList(List<Ticket> tickets){
        return tickets.stream().map(TicketMapper::ticketDtoFromTicket).toList();
    }

    public static List<Ticket> ticketListFromTicketDtoList(List<TicketDto> tickets){
        return tickets.stream().map(TicketMapper::ticketFromTicketDto).toList();
    }
}
