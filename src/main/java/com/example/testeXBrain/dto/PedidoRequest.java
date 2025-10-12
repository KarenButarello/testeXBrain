package com.example.testeXBrain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequest {

    @Valid
    @NotNull(message = "Por favor, informe o id do cliente")
    private ClienteRequest cliente;

    @Valid
    @NotEmpty(message = "Por favor, informe os produtos desejados")
    private List<ProdutoRequest> produtos;
}
