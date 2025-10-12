package com.example.testeXBrain.consumer;

import com.example.testeXBrain.config.constants.RabbitMQConstants;
import com.example.testeXBrain.dto.EntregaRequest;
import com.example.testeXBrain.service.EntregaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

    @Autowired
    private EntregaService entregaService;

    @RabbitListener(queues = RabbitMQConstants.FILA)
    public void consumidor(EntregaRequest request) throws JsonProcessingException {
        entregaService.salvarPedidoEntregue(request);
    }
}
