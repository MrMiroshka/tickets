package ru.tickets.api.core;

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
    private Long trackerTicket;
    private Long statusTicket;
    private Long priorityTicket;
    private Long worker;
    private Long author;
}
