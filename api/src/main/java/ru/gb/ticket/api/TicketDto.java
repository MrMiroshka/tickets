package ru.gb.ticket.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {
    private Long id;
    private String nameTicket;
    private String textTicket;
    private int trackerTicket;
    private int statusTicket;
    private int priorityTicket;
    private int worker;
    private int author;
}
