package ru.circledevs.tasks.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "tasks")
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String title;
    private String comment;
    private String status;
    private String priority;


    public Task(String title, String comment, String status, String priority) {
        this.title = title;
        this.comment = comment;
        this.status = status;
        this.priority = priority;
    }
}
