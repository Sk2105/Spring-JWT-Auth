package com.sk.jwtauth.entities;


import lombok.Data;

public record LoginCredentials(
    String email,
    String password
) {
}
