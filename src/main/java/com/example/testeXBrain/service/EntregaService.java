package com.example.testeXBrain.service;

import com.example.testeXBrain.dto.EntregaRequest;
import com.example.testeXBrain.exception.NotFoundException;
import com.example.testeXBrain.model.Entrega;
import com.example.testeXBrain.model.Pedido;
import com.example.testeXBrain.repository.EntregaRepository;
import com.example.testeXBrain.repository.PedidoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregaService {

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public void salvarPedidoEntregue(EntregaRequest request) {
        var pedido = buscarPedido(request.getPedidoId());

        var entrega = new Entrega();
        entrega.setPedido(pedido);
        entrega.setEndereco(pedido.getEnderecoEntrega());

        entregaRepository.save(entrega);
    }

    private Pedido buscarPedido(Integer id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado com o id: " + id));
    }
}
