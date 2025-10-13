package com.example.testeXBrain.controller;

import com.example.testeXBrain.dto.PedidoRequest;
import com.example.testeXBrain.dto.PedidoResponse;
import com.example.testeXBrain.service.PedidoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @PostMapping
    public PedidoResponse gerarNovoPedido(@RequestBody @Valid PedidoRequest request) {
        log.info("Iniciando fluxo para gerar novo pedido");
        var pedido = service.gerarNovoPedido(request);
        log.info("Finalizado o fluxo que gera novo pedido");
        return pedido;
    }
}
