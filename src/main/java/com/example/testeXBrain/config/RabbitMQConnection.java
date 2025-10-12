package com.example.testeXBrain.config;

import com.example.testeXBrain.config.constants.RabbitMQConstants;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConnection {

    private static final String NOME_EXCHANGE = "amq.direct";
    private AmqpAdmin amqpAdmin;
    private RabbitTemplate rabbitTemplate;

    public RabbitMQConnection(AmqpAdmin amqpAdmin, RabbitTemplate rabbitTemplate) {
        this.amqpAdmin = amqpAdmin;
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    }

    private Queue fila(String nomeFila) {
        return new Queue(nomeFila, true, false, false);
    }

    private DirectExchange directExchange() {
        return new DirectExchange(NOME_EXCHANGE);

    }

    private Binding relacionamento(Queue fila, DirectExchange direct) {
        return new Binding(
                fila.getName(),
                Binding.DestinationType.QUEUE,
                direct.getName(),
                fila.getName(), null
        );
    }

    @PostConstruct
    private void adicionar() {
        Queue fila = this.fila(RabbitMQConstants.FILA);

        DirectExchange direct = this.directExchange();

        Binding ligacao = this.relacionamento(fila, direct);

        this.amqpAdmin.declareQueue(fila);

        this.amqpAdmin.declareExchange(direct);

        this.amqpAdmin.declareBinding(ligacao);
    }

}
