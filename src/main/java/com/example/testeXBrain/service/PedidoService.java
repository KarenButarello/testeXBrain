package com.example.testeXBrain.service;

import com.example.testeXBrain.dto.PedidoRequest;
import com.example.testeXBrain.dto.PedidoResponse;
import com.example.testeXBrain.exception.NotFoundException;
import com.example.testeXBrain.mapper.PedidoMapper;
import com.example.testeXBrain.model.Pedido;
import com.example.testeXBrain.model.Produto;
import com.example.testeXBrain.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    public PedidoResponse gerarNovoPedido(PedidoRequest request) {
        var cliente = clienteRepository
                .findById(request.getCliente().getId())
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));

        List<Produto> produtos = request.getProdutos().stream()
                .map(produtoRequest -> produtoRepository.findById(produtoRequest.getId())
                        .orElseThrow(() -> new NotFoundException("Produto não encontrado: " + produtoRequest.getId())))
                .toList();

        var valorTotalPedido = calcularValorTotalPedido(produtos);

        var pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setProduto(produtos);
        pedido.setValorTotalPedido(valorTotalPedido);
        pedido.setEnderecoEntrega(cliente.getEndereco());

        var pedidoSalvo = pedidoRepository.save(pedido);

        return pedidoMapper.toResponse(pedidoSalvo);
    }

    private BigDecimal calcularValorTotalPedido(List<Produto> produto) {
        return produto.stream()
                .map(Produto::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
