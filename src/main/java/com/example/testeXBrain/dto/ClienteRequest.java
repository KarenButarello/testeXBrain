package com.example.testeXBrain.dto;

import com.example.testeXBrain.model.Endereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClienteRequest {

    @NotNull(message = "Por favor, informe o id do cliente")
    private Integer id;

    @Valid
    private Endereco endereco;
}
