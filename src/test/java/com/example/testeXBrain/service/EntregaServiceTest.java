package com.example.testeXBrain.service;

import com.example.testeXBrain.dto.EntregaRequest;
import com.example.testeXBrain.exception.NotFoundException;
import com.example.testeXBrain.model.Entrega;
import com.example.testeXBrain.model.Pedido;
import com.example.testeXBrain.repository.EntregaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.example.testeXBrain.helper.TestHelper.umPedido;
import static com.example.testeXBrain.helper.TestHelper.umProduto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EntregaServiceTest {

    @InjectMocks
    private EntregaService service;

    @Mock
    private PedidoService pedidoService;

    @Mock
    private EntregaRepository repository;

    @Test
    void salvarPedidoEntregue_deveSalvarEntrega_quandoExistirPedido() {
        var pedido = umPedido();

        var request = new EntregaRequest();
        request.setPedidoId(pedido.getId());

        var entrega = new Entrega();
        entrega.setPedido(pedido);
        entrega.setEndereco(pedido.getEnderecoEntrega());

        when(pedidoService.buscarPedido(pedido.getId())).thenReturn(pedido);
        when(repository.save(any(Entrega.class))).thenReturn(entrega);

        service.salvarPedidoEntregue(request);

        verify(pedidoService, times(1)).buscarPedido(1);
        verify(repository, times(1)).save(any(Entrega.class));
    }

    @Test
    void salvarPedidoEntregue_naoDeveSalvarEntrega_quandoNaoExistirPedido() {
        var request = new EntregaRequest();
        request.setPedidoId(1);

        when(pedidoService.buscarPedido(1))
                .thenThrow(new NotFoundException("Pedido não encontrado"));

        var exception = assertThrows(NotFoundException.class, () ->
                service.salvarPedidoEntregue(request));

        assertEquals("Pedido não encontrado",
                exception.getMessage());

        verify(pedidoService, times(1)).buscarPedido(1);
        verify(repository, never()).save(any(Entrega.class));
    }

    @Test
    void salvarPedidoEntregue_naoDeveSalvarEntrega_quandoNaoEncontrarEnderecoDeEntrega() {
        var pedido = new Pedido();
        pedido.setId(1);
        pedido.setProduto(List.of(umProduto()));
        pedido.setEnderecoEntrega(null);

        var request = new EntregaRequest();
        request.setPedidoId(pedido.getId());

        when(pedidoService.buscarPedido(1)).thenReturn(pedido);

        var exception = assertThrows(NotFoundException.class, () ->
                service.salvarPedidoEntregue(request));

        assertEquals("Endereço de entrega não encontrado",
                exception.getMessage());

        verify(pedidoService, times(1)).buscarPedido(1);
        verify(repository, never()).save(any(Entrega.class));
    }
}
