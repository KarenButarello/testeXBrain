package com.example.testeXBrain.mapper;

import com.example.testeXBrain.model.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static com.example.testeXBrain.helper.TestHelper.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PedidoMapperTest {

    @InjectMocks
    private PedidoMapper mapper;

    @Test
    void toResponse_deveConverterParaResponse_quandoPedidoForValido() {
        var pedido = new Pedido();
        pedido.setId(1);
        pedido.setCliente(umCliente());
        pedido.setProduto(List.of(umProduto()));
        pedido.setEnderecoEntrega(umCliente().getEndereco());
        pedido.setValorTotalPedido(umProduto().getValor());

        var response = mapper.toResponse(pedido);

        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals(pedido.getCliente(), response.getCliente());
        assertEquals(List.of(1), response.getProdutos());
        assertEquals(BigDecimal.valueOf(10.00), response.getValorTotal());
        assertEquals(pedido.getEnderecoEntrega(), response.getEnderecoEntrega());
    }

    @Test
    void toResponse_deveLancarException_quandoNaoHouverProdutosNoPedido() {
        var pedido = umPedidoSemProdutos();

        var response = mapper.toResponse(pedido);

        assertNotNull(response);
        assertTrue(response.getProdutos().isEmpty());
    }

    private Pedido umPedidoSemProdutos() {
        var pedido = new Pedido();
        pedido.setId(1);
        pedido.setProduto(List.of());
        pedido.setCliente(umCliente());
        pedido.setEnderecoEntrega(umCliente().getEndereco());
        return pedido;
    }
}
