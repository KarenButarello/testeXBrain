package com.example.testeXBrain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequest {

    @NotNull(message = "Por favor, informe o id do cliente")
    private Integer id;
}
