package com.example.testeXBrain.service;

import com.example.testeXBrain.dto.PedidoRequest;
import com.example.testeXBrain.dto.PedidoResponse;
import com.example.testeXBrain.exception.NotFoundException;
import com.example.testeXBrain.mapper.EnderecoMapper;
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
    private EnderecoMapper enderecoMapper;

    @Autowired
    private PedidoMapper pedidoMapper;

    public PedidoResponse gerarNovoPedido(PedidoRequest request) {
        var endereco = enderecoRepository
                .findById(request.getCliente().getEndereco().getId())
                .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));

        var cliente = clienteRepository
                .findByEnderecoId(endereco.getId())
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));

        validarProdutos(request);

        List<Produto> produtos = request.getProdutos().stream()
                .map(produtoRequest -> produtoRepository.findById(produtoRequest.getId())
                        .orElseThrow(() -> new NotFoundException("Produto não encontrado")))
                .toList();

        var enderecoEntrega = enderecoMapper.toEntity(request.getEnderecoEntrega());

        var enderecoRegistrado = enderecoRepository.findById(enderecoEntrega.getId())
                .orElseThrow(() -> new NotFoundException("Endereço de entrega não encontrado"));

        var valorTotalPedido = calcularValorTotalPedido(produtos);

        var pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setProduto(produtos);
        pedido.setValorTotalPedido(valorTotalPedido);
        pedido.setEnderecoEntrega(enderecoRegistrado);

        var pedidoSalvo = pedidoRepository.save(pedido);

        return pedidoMapper.toResponse(pedidoSalvo);
    }

    private BigDecimal calcularValorTotalPedido(List<Produto> produto) {
        if (produto.size() == 1) {
            return produto.get(0).getValor();
        }
        return produto.stream()
                .map(Produto::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void validarProdutos(PedidoRequest request) {
        if (request.getProdutos() == null || request.getProdutos().isEmpty()) {
            throw new NotFoundException("Por favor, informe os produtos desejados");
        }
    }
}
