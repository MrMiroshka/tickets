package ru.tickets.core.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import ru.gb.ticket.api.ResourceNotFoundExceptions;
import ru.gb.ticket.api.TicketDto;
import ru.tickets.core.converters.TicketMapper;
import ru.tickets.core.entities.Ticket;
import ru.tickets.core.services.TicketService;


import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/ticket")
public class TicketController {
    private final TicketService ticketService;

    @GetMapping()
    public List<TicketDto> getAllTasks(){
        log.info("Метод работает!");
        return TicketMapper.ticketDtoListFromTicketList(ticketService.getAllTasks());
    }
    @GetMapping("/{id}")
    public TicketDto findById(@PathVariable Long id){
        Ticket ticket = ticketService.findById(id).orElseThrow(
                () -> new ResourceNotFoundExceptions("Продукт не найден, id:" + id));
        return TicketMapper.ticketDtoFromTicket(ticket);
    }

    @PostMapping("/create")
    public void create(@RequestBody TicketDto ticketDto){
        ticketService.createTicket(ticketDto);
    }
}
