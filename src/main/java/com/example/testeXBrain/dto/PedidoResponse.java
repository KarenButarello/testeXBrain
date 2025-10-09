package com.example.testeXBrain.dto;

import com.example.testeXBrain.model.Cliente;
import com.example.testeXBrain.model.Endereco;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PedidoResponse {

    private Integer id;
    private Cliente cliente;
    private List<Integer> produtos;
    private BigDecimal valorTotal;
    private Endereco enderecoEntrega;
}
