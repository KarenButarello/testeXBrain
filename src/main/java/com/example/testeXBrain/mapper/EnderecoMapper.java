package com.example.testeXBrain.mapper;

import com.example.testeXBrain.dto.EnderecoRequest;
import com.example.testeXBrain.model.Endereco;
import org.springframework.stereotype.Component;

@Component
public class EnderecoMapper {

    public Endereco toEntity(EnderecoRequest request) {
        var endereco = new Endereco();
        endereco.setId(request.getId());
        return endereco;
    }
}
