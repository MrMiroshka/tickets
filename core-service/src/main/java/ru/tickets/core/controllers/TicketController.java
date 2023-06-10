package ru.tickets.core.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import ru.gb.ticket.api.ResourceNotFoundExceptions;
import ru.gb.ticket.api.TicketDto;
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
        // FIXME: 10.05.2023 Сделать мапперы
        return ticketService.getAllTasks().stream().map(task -> new TicketDto(
                task.getId(),
                task.getTitle(),
                task.getComment(),
                task.getStatus(),
                task.getPriority()))
                .collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public TicketDto findById(@PathVariable Long id){
        Ticket task = ticketService.findById(id).orElseThrow(
                () -> new ResourceNotFoundExceptions("Продукт не найден, id:" + id));
        return new TicketDto(task.getId(),
                task.getTitle(),
                task.getComment(),
                task.getStatus(),
                task.getPriority());
    }

    @PostMapping("/create")
    public void create(@RequestBody TicketDto taskDto){
        ticketService.createTask(taskDto);
    }
}
