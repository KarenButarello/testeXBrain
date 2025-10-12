package com.example.testeXBrain.service;

import com.example.testeXBrain.dto.EntregaRequest;
import com.example.testeXBrain.model.Entrega;
import com.example.testeXBrain.repository.EntregaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregaService {

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ObjectMapper objectMapper;

    public void salvarPedidoEntregue(EntregaRequest request) {
        var pedido = pedidoService.buscarPedido(request.getPedidoId());

        var entrega = new Entrega();
        entrega.setPedido(pedido);
        entrega.setEndereco(pedido.getEnderecoEntrega());

        entregaRepository.save(entrega);
    }
}
