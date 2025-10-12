package com.example.testeXBrain.controller;

import com.example.testeXBrain.dto.PedidoRequest;
import com.example.testeXBrain.dto.PedidoResponse;
import com.example.testeXBrain.service.PedidoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @PostMapping
    public PedidoResponse gerarNovoPedido(@RequestBody @Valid PedidoRequest request) throws JsonProcessingException {
        return service.gerarNovoPedido(request);
    }
}
