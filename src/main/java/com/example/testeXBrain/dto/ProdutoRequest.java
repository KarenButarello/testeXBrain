package com.example.testeXBrain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProdutoRequest {

    @NotNull(message = " Por favor, informe o produto desejado")
    private Integer id;
}
