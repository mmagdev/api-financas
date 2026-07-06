package br.com.cotiinformatica.api_financas.dtos;

import java.util.UUID;

public record CategoriaResponse(

        UUID id, //Id da categoria
        String nome //Nome da categoria

) {
}
