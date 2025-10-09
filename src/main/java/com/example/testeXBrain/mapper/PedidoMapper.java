package com.example.testeXBrain.mapper;

import com.example.testeXBrain.dto.PedidoResponse;
import com.example.testeXBrain.model.Pedido;
import com.example.testeXBrain.model.Produto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoMapper {

    public PedidoResponse toResponse(Pedido pedido) {
        var response = new PedidoResponse();
        response.setId(pedido.getId());
        response.setCliente(pedido.getCliente());

        List<Integer> idProdutos = pedido.getProduto().stream()
                .map(Produto::getId)
                .toList();
        response.setProdutos(idProdutos);

        response.setValorTotal(pedido.getValorTotalPedido());

        response.setEnderecoEntrega(pedido.getEnderecoEntrega());

        return response;
    }
}
