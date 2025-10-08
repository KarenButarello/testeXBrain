package com.example.testeXBrain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutoRequest {

    private String descricao;
    private BigDecimal valor;
}
