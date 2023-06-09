package ru.tickets.core.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "ticket")
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ticket")
    private Long id;
    private String nameTicket;
    private String textTicket;
    private Long trackerTicket;
    private Long statusTicket;
    private Long priorityTicket;
    private Long worker;
    private Long author; // FIXME: 10.05.2023 В БД есть связи с User, но юзеры теперь в другом микросервисе
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public Ticket(String nameTicket, String textTicket, Long trackerTicket, Long statusTicket, Long priorityTicket, Long worker, Long author) {
        this.nameTicket = nameTicket;
        this.textTicket = textTicket;
        this.trackerTicket = trackerTicket;
        this.statusTicket = statusTicket;
        this.priorityTicket = priorityTicket;
        this.worker = worker;
        this.author = author;
    }

    public Ticket(String nameTicket, String textTicket, Long statusTicket, Long priorityTicket) {
        this.nameTicket = nameTicket;
        this.textTicket = textTicket;
        this.statusTicket = statusTicket;
        this.priorityTicket = priorityTicket;
    }
}
