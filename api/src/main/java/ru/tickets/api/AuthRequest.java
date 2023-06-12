package ru.tickets.api;

import lombok.Getter;

@Getter
public class AuthRequest {
   private String username;
   private String password;
}
