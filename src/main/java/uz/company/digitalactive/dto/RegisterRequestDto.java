package uz.company.digitalactive.dto;

public record RegisterRequestDto(
    String firstname, String lastname, String email, String password) {}
