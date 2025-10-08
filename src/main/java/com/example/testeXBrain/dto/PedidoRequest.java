package com.example.testeXBrain.dto;

import lombok.Data;

import java.util.List;

@Data
public class PedidoRequest {

    private ClienteRequest cliente;
    private List<ProdutoRequest> produtos;
    private EnderecoRequest enderecoEntrega;
}
