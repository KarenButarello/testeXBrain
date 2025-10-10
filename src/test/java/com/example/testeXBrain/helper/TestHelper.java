package com.example.testeXBrain.helper;

import com.example.testeXBrain.dto.ClienteRequest;
import com.example.testeXBrain.dto.PedidoRequest;
import com.example.testeXBrain.dto.PedidoResponse;
import com.example.testeXBrain.dto.ProdutoRequest;
import com.example.testeXBrain.model.Cliente;
import com.example.testeXBrain.model.Endereco;
import com.example.testeXBrain.model.Pedido;
import com.example.testeXBrain.model.Produto;

import java.math.BigDecimal;
import java.util.List;

public class TestHelper {

    public static Endereco umEnderecoCliente() {
        var enderecoCliente = new Endereco();
        enderecoCliente.setId(1);
        enderecoCliente.setEndereco("Rua Teste, 123");
        return enderecoCliente;
    }

    public static Cliente umCliente() {
        var cliente = new Cliente();
        cliente.setId(1);
        cliente.setEndereco(umEnderecoCliente());
        return cliente;
    }

    public static Pedido umPedido() {
        var pedido = new Pedido();
        pedido.setId(1);
        pedido.setCliente(umCliente());
        pedido.setProduto(List.of(umProduto()));
        pedido.setValorTotalPedido(BigDecimal.valueOf(10.00));
        pedido.setEnderecoEntrega(umEnderecoCliente());
        return pedido;
    }

    public static Produto umProduto() {
        var produto = new Produto();
        produto.setId(1);
        produto.setDescricao("Produto");
        produto.setValor(BigDecimal.valueOf(10.00));
        return produto;
    }

    public static List<Produto> umaListaDeProdutos() {
        var produto1 = new Produto();
        produto1.setId(1);
        produto1.setDescricao("Produto 1");
        produto1.setValor(BigDecimal.valueOf(10.00));

        var produto2 = new Produto();
        produto2.setId(2);
        produto2.setDescricao("Produto 2");
        produto2.setValor(BigDecimal.valueOf(20.00));

        var produto3 = new Produto();
        produto3.setId(3);
        produto3.setDescricao("Produto 3");
        produto3.setValor(BigDecimal.valueOf(30.00));

        return List.of(produto1, produto2, produto3);
    }

    public static ClienteRequest umClienteRequestComIdInexistente() {
        var clienteRequest = new ClienteRequest();
        clienteRequest.setId(99);
        return clienteRequest;
    }

    public static List<ProdutoRequest> umaListaDeProdutoRequestVazia() {
        return List.of();
    }

    public static List<ProdutoRequest> umaListaDeProdutoRequest() {
        var produto1 = new ProdutoRequest();
        produto1.setId(1);

        var produto2 = new ProdutoRequest();
        produto2.setId(2);

        var produto3 = new ProdutoRequest();
        produto3.setId(3);

        return List.of(produto1, produto2, produto3);
    }

    public static Pedido umPedidoComVariosProdutos() {
        var pedido = new Pedido();
        pedido.setId(1);
        pedido.setCliente(umCliente());
        pedido.setProduto(umaListaDeProdutos());
        pedido.setValorTotalPedido(BigDecimal.valueOf(60.00));
        pedido.setEnderecoEntrega(umEnderecoCliente());
        return pedido;
    }

    public static ClienteRequest umClienteRequest() {
        var clienteRequest = new ClienteRequest();
        clienteRequest.setId(1);
        clienteRequest.setEndereco(umEnderecoCliente());
        return clienteRequest;
    }

    public static ProdutoRequest umProdutoRequest() {
        var produtoRequest = new ProdutoRequest();
        produtoRequest.setId(1);
        return produtoRequest;
    }

    public static PedidoRequest umPedidoRequest() {
        var pedidoRequest = new PedidoRequest();
        pedidoRequest.setProdutos(List.of(umProdutoRequest()));
        pedidoRequest.setCliente(umClienteRequest());
        return pedidoRequest;
    }

    public static PedidoResponse umPedidoResponse() {
        var pedidoResponse = new PedidoResponse();
        pedidoResponse.setId(1);
        pedidoResponse.setCliente(umCliente());
        pedidoResponse.setProdutos(List.of(1));
        pedidoResponse.setValorTotal(BigDecimal.valueOf(10.00));
        pedidoResponse.setEnderecoEntrega(umEnderecoCliente());
        return pedidoResponse;
    }

    public static List<ProdutoRequest> umaListaDeProdutosRequest() {
        var produtoRequest1 = new ProdutoRequest();
        produtoRequest1.setId(1);

        var produtoRequest2 = new ProdutoRequest();
        produtoRequest2.setId(2);

        var produtoRequest3 = new ProdutoRequest();
        produtoRequest3.setId(3);

        return List.of(produtoRequest1, produtoRequest2, produtoRequest3);
    }

    public static PedidoRequest umPedidoRequestComListaDeProdutos() {
        var pedidoRequest = new PedidoRequest();
        pedidoRequest.setProdutos(umaListaDeProdutosRequest());
        pedidoRequest.setCliente(umClienteRequest());
        return pedidoRequest;
    }

    public static PedidoResponse umPedidoResponseComUmaListaDeProdutos() {
        var pedidoResponse = new PedidoResponse();
        pedidoResponse.setId(1);
        pedidoResponse.setCliente(umCliente());
        pedidoResponse.setProdutos(List.of(1, 2, 3));
        pedidoResponse.setValorTotal(BigDecimal.valueOf(60.00));
        pedidoResponse.setEnderecoEntrega(umEnderecoCliente());
        return pedidoResponse;
    }

    public static ProdutoRequest umProdutoRequestInexistente() {
        var produtoRequest = new ProdutoRequest();
        produtoRequest.setId(10);
        return produtoRequest;
    }
}
