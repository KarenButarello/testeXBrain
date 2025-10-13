package com.example.testeXBrain.service;

import com.example.testeXBrain.config.constants.RabbitMQConstants;
import com.example.testeXBrain.dto.EntregaRequest;
import com.example.testeXBrain.exception.NotFoundException;
import com.example.testeXBrain.mapper.PedidoMapper;
import com.example.testeXBrain.model.Pedido;
import com.example.testeXBrain.repository.ClienteRepository;
import com.example.testeXBrain.repository.PedidoRepository;
import com.example.testeXBrain.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.example.testeXBrain.helper.TestHelper.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @InjectMocks
    private PedidoService service;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private PedidoMapper pedidoMapper;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Test
    void gerarNovoPedido_deveGerarNovoPedido_quandoTodosOsCamposPreenchidosEUmProduto() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(umCliente()));
        when(produtoRepository.findById(1)).thenReturn(Optional.of(umProduto()));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(umPedido());
        when(pedidoMapper.toResponse(any(Pedido.class))).thenReturn(umPedidoResponse());

        var resultado = service.gerarNovoPedido(umPedidoRequest());

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals(umCliente(), resultado.getCliente());
        assertEquals(List.of(1), resultado.getProdutos());
        assertEquals(BigDecimal.valueOf(10.00), resultado.getValorTotal());
        assertEquals(umEnderecoCliente(), resultado.getEnderecoEntrega());

        verify(clienteRepository, times(1)).findById(1);
        verify(produtoRepository, times(1)).findById(1);
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
        verify(pedidoMapper, times(1)).toResponse(any(Pedido.class));
        verify(rabbitTemplate, times(1)).convertAndSend(
                eq(RabbitMQConstants.EXCHANGE),
                eq(RabbitMQConstants.FILA),
                any(EntregaRequest.class)
        );
    }

    @Test
    void gerarNovoPedido_deveGerarNovoPedido_quandoTodosOsCamposPreenchidosEUmaListaDeProdutos() {
        var listaProdutos = umaListaDeProdutos();

        when(clienteRepository.findById(1)).thenReturn(Optional.of(umCliente()));
        when(produtoRepository.findById(1)).thenReturn(Optional.of(listaProdutos.get(0)));
        when(produtoRepository.findById(2)).thenReturn(Optional.of(listaProdutos.get(1)));
        when(produtoRepository.findById(3)).thenReturn(Optional.of(listaProdutos.get(2)));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(umPedidoComVariosProdutos());
        when(pedidoMapper.toResponse(any(Pedido.class))).thenReturn(umPedidoResponseComUmaListaDeProdutos());

        var resultado = service.gerarNovoPedido(umPedidoRequestComListaDeProdutos());

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals(umCliente(), resultado.getCliente());
        assertEquals(List.of(1, 2, 3), resultado.getProdutos());
        assertEquals(BigDecimal.valueOf(60.00), resultado.getValorTotal());
        assertEquals(umEnderecoCliente(), resultado.getEnderecoEntrega());

        verify(clienteRepository, times(1)).findById(1);
        verify(produtoRepository, times(1)).findById(1);
        verify(produtoRepository, times(1)).findById(2);
        verify(produtoRepository, times(1)).findById(3);
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
        verify(pedidoMapper, times(1)).toResponse(any(Pedido.class));
        verify(rabbitTemplate, times(1)).convertAndSend(
                eq(RabbitMQConstants.EXCHANGE),
                eq(RabbitMQConstants.FILA),
                any(EntregaRequest.class)
        );
    }

    @Test
    void gerarNovoPedido_naoDeveGerarNovoPedido_quandoNaoEncontrarCliente() {
        when(clienteRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                service.gerarNovoPedido(umPedidoRequest()));

        verify(clienteRepository, times(1)).findById(1);
        verify(pedidoRepository, never()).findById(1);
        verify(produtoRepository, never()).findById(1);
        verify(pedidoMapper, never()).toResponse(umPedido());
    }

    @Test
    void gerarNovoPedido_naoDeveGerarNovoPedido_quandoNaoHouverProdutos() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(umCliente()));
        when(produtoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                service.gerarNovoPedido(umPedidoRequest()));

        verify(clienteRepository, times(1)).findById(1);
        verify(produtoRepository, times(1)).findById(1);
        verify(pedidoRepository, never()).save(umPedido());
        verify(pedidoMapper, never()).toResponse(umPedido());
    }
}