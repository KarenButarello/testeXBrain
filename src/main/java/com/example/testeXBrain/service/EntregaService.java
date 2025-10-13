package com.example.testeXBrain.service;

import com.example.testeXBrain.dto.EntregaRequest;
import com.example.testeXBrain.exception.NotFoundException;
import com.example.testeXBrain.model.Entrega;
import com.example.testeXBrain.model.Pedido;
import com.example.testeXBrain.repository.EntregaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EntregaService {

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ObjectMapper objectMapper;

    public void salvarPedidoEntregue(EntregaRequest request) {
        log.info("Iniciando processo de salvar entrega");

        var pedido = pedidoService.buscarPedido(request.getPedidoId());

        validarEnderecoEntrega(pedido);

        var entrega = new Entrega();
        entrega.setPedido(pedido);
        entrega.setEndereco(pedido.getEnderecoEntrega());

        entregaRepository.save(entrega);

        log.info("Entrega salva com sucesso");
    }

    private void validarEnderecoEntrega(Pedido pedido) {
        if (pedido.getEnderecoEntrega() == null) {
            throw new NotFoundException("Endereço de entrega não encontrado");
        }
    }
}
