package ru.tickets.core.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import ru.gb.ticket.api.TicketDto;
import ru.gb.ticket.api.exceptions.ResourceNotFoundException;
import ru.tickets.core.converters.TicketMapper;
import ru.tickets.core.entities.Ticket;
import ru.tickets.core.services.TicketService;


import java.util.List;
import java.util.Map;


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
                () -> new ResourceNotFoundException("Продукт не найден, id:" + id));
        return TicketMapper.ticketDtoFromTicket(ticket);
    }

    @PostMapping("/create")
    public void create(@RequestBody TicketDto ticketDto,  @RequestHeader String username){
        ticketService.createTicket(ticketDto, username);
    }

    @PutMapping("/updateStatus/{ticketId}")
    public void updateStatusInCreatedTicket(@PathVariable Long ticketId,@RequestBody Map<String, Long> statusIdMap){
        Long statusId = statusIdMap.get("statusId");
        ticketService.updateStatus(ticketId,statusId);
    }
}
