package com.example.testeXBrain.controller;

import com.example.testeXBrain.dto.PedidoRequest;
import com.example.testeXBrain.model.Pedido;
import com.example.testeXBrain.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pedido")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @PostMapping
    public Pedido gerarNovoPedido(@RequestBody PedidoRequest request) {
        return service.gerarNovoPedido(request);
    }
}
