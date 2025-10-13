package com.example.testeXBrain.consumer;

import com.example.testeXBrain.dto.EntregaRequest;
import com.example.testeXBrain.exception.NotFoundException;
import com.example.testeXBrain.service.EntregaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RabbitMQConsumerTest {

    @InjectMocks
    private RabbitMQConsumer consumer;

    @Mock
    private EntregaService service;

    @Test
    void consumidor_deveProcessarMensagem_quandoReceberEntrega() throws JsonProcessingException {
        var request = new EntregaRequest();
        request.setPedidoId(1);

        doNothing().when(service).salvarPedidoEntregue(request);

        consumer.consumidor(request);

        verify(service, times(1)).salvarPedidoEntregue(request);
    }

    @Test
    void consumidor_deveLancarException_quandoHouverErroAoTentarSalvarEntrega() throws JsonProcessingException {
        var request = new EntregaRequest();
        request.setPedidoId(1);

        doThrow(new NotFoundException("Pedido não encontrado"))
                .when(service).salvarPedidoEntregue(request);

        var exception = assertThrows(NotFoundException.class, () ->
                consumer.consumidor(request));

        assertEquals("Pedido não encontrado", exception.getMessage());
        verify(service, times(1)).salvarPedidoEntregue(request);
    }
}
