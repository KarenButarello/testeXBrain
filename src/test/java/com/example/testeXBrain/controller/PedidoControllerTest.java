package com.example.testeXBrain.controller;

import com.example.testeXBrain.dto.*;
import com.example.testeXBrain.exception.NotFoundException;
import com.example.testeXBrain.service.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static com.example.testeXBrain.helper.TestHelper.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PedidoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private PedidoService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void gerarNovoPedido_deveRetornarOk_quandoForGeradaNovaVendaComUmUnicoProduto() throws Exception {
        var pedidoRequest = new PedidoRequest();
        pedidoRequest.setCliente(umClienteRequest());
        pedidoRequest.setProdutos(List.of(umProdutoRequest()));

        var pedidoResponse = new PedidoResponse();
        pedidoResponse.setId(1);
        pedidoResponse.setCliente(umCliente());
        pedidoResponse.setProdutos(List.of(1));
        pedidoResponse.setValorTotal(BigDecimal.valueOf(100.00));
        pedidoResponse.setEnderecoEntrega(umEnderecoCliente());

        when(service.gerarNovoPedido(eq(pedidoRequest))).thenReturn(pedidoResponse);

        mvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cliente.id").value(1))
                .andExpect(jsonPath("$.produtos[0]").value(1))
                .andExpect(jsonPath("$.valorTotal").value(100.00))
                .andExpect(jsonPath("$.enderecoEntrega.id").value(1))
                .andExpect(jsonPath("$.enderecoEntrega.endereco").value("Rua Teste, 123"));

        verify(service, times(1)).gerarNovoPedido(pedidoRequest);
    }

    @Test
    void gerarNovoPedido_deveRetornarOk_quandoForGeradaNovaVendaComUmaListaDeProdutos() throws Exception {
        var pedidoRequest = new PedidoRequest();
        pedidoRequest.setCliente(umClienteRequest());
        pedidoRequest.setProdutos(umaListaDeProdutoRequest());

        var pedidoEsperado = new PedidoResponse();
        pedidoEsperado.setId(1);
        pedidoEsperado.setCliente(umCliente());
        pedidoEsperado.setProdutos(List.of(1, 2, 3));
        pedidoEsperado.setValorTotal(BigDecimal.valueOf(150.00));
        pedidoEsperado.setEnderecoEntrega(umEnderecoCliente());

        when(service.gerarNovoPedido(eq(pedidoRequest))).thenReturn(pedidoEsperado);

        mvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cliente.id").value(1))
                .andExpect(jsonPath("$.produtos[0]").value(1))
                .andExpect(jsonPath("$.produtos[1]").value(2))
                .andExpect(jsonPath("$.produtos[2]").value(3))
                .andExpect(jsonPath("$.valorTotal").value(150.00))
                .andExpect(jsonPath("$.enderecoEntrega.id").value(1))
                .andExpect(jsonPath("$.enderecoEntrega.endereco").value("Rua Teste, 123"));

        verify(service, times(1)).gerarNovoPedido(pedidoRequest);
    }

    @Test
    void gerarNovoPedido_deveRetornarNotFound_quandoNaoEncontrarCliente() throws Exception {
        var pedidoRequest = new PedidoRequest();
        pedidoRequest.setCliente(umClienteRequestComIdInexistente());
        pedidoRequest.setProdutos(List.of(umProdutoRequest()));

        when(service.gerarNovoPedido(pedidoRequest))
                .thenThrow(new NotFoundException("Cliente não encontrado"));

        mvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Cliente não encontrado"));

        verify(service, times(1)).gerarNovoPedido(any(PedidoRequest.class));
    }

    @Test
    void gerarNovoPedido_deveRetornarNotFound_quandoNaoEncontrarProduto() throws Exception {
        var pedidoRequest = new PedidoRequest();
        pedidoRequest.setCliente(umClienteRequest());
        pedidoRequest.setProdutos(List.of(umProdutoRequestInexistente()));

        when(service.gerarNovoPedido(pedidoRequest))
                .thenThrow(new NotFoundException("Produto não encontrado"));

        mvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Produto não encontrado"));

        verify(service, times(1)).gerarNovoPedido(any(PedidoRequest.class));
    }

    @Test
    void gerarNovoPedido_deveRetornarBadRequest_quandoNaoInformadosOsProdutos() throws Exception {
        var pedidoRequest = new PedidoRequest();
        pedidoRequest.setCliente(umClienteRequest());
        pedidoRequest.setProdutos(umaListaDeProdutoRequestVazia());

        when(service.gerarNovoPedido(pedidoRequest))
                .thenThrow(new NotFoundException("Por favor, informe os produtos desejados"));

        mvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Por favor, informe os produtos desejados"));

        verify(service, times(0)).gerarNovoPedido(any(PedidoRequest.class));
    }











}

