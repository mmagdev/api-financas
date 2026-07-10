package br.com.cotiinformatica.api_financas.services;


import br.com.cotiinformatica.api_financas.dtos.CategoriaRequest;
import br.com.cotiinformatica.api_financas.dtos.CategoriaResponse;
import br.com.cotiinformatica.api_financas.entities.Categoria;
import br.com.cotiinformatica.api_financas.exceptions.RegistroNaoEncontradoException;
import br.com.cotiinformatica.api_financas.exceptions.ValidacaoException;
import br.com.cotiinformatica.api_financas.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    /*
    Response - retorna os dados solicitados
    Request - recebe os dados que serão gravados no banco

    O método criar grava de acordo com os parâmetros passados como request.
    Antes do nome do método temos um Response porque é o que será devolvido pelo método
    após a gravação.
     */
    public CategoriaResponse criar(CategoriaRequest request) {

        //Criando um objeto da entidade 'Categoria'
        var categoria = new Categoria();

        //Capturando os dados recebidos
        categoria.setNome(request.nome());

        //Executar a validação
        validarCategoria(categoria);

        //Salvar a categoria no banco de dados
        categoriaRepository.save(categoria);

        return toResponse(categoria);


    }

    public CategoriaResponse alterar(UUID id, CategoriaRequest request) {

        //Buscar a categoria no banco de dados através do ID
        var categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Categoria não encontrada. "));

        //Capturar o nome da categoria que será alterao
        categoria.setNome(request.nome());

        //Atualizar no banco de dados
        categoriaRepository.save(categoria);

        //Retornar a resposta
        return toResponse(categoria);

    }


    //Excluir categoria pelo ID
    public CategoriaResponse excluir(UUID id) {

        //Buscar a categoria no banco de dados através do ID
        var categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Categoria não encontrada."));

        //Excluindo no banco
        categoriaRepository.delete(categoria);

        //Retornando a resposta pós exclusão
        return toResponse(categoria);


    }

    public List<CategoriaResponse> consultar() {

        //Consultar todas as categorias cadastradas
        var categorias = categoriaRepository.findAll();

        //Copiar cada categoria da lista obtida do banco de dados
        //para uma lista do DTO CategoriaResponse
        return categorias.stream()
                .map(this::toResponse)
                .toList();

    }

    public CategoriaResponse obterPorId(UUID id) {

        //Buscar a categoria no banco de dados através do ID
        var categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Categoria não encontrada."));

        //Retornar os dados
        return toResponse(categoria);

    }


    private void validarCategoria(Categoria categoria) {
        if(categoria.getNome() == null || categoria.getNome().trim().isEmpty()) {
            throw new ValidacaoException("O nome da categoria é obrigatório.");

        }
        if(categoria.getNome().length() < 6) {
            throw new ValidacaoException("O nome da categoria deve ter pelo menos 6 caracteres.");
        }
    }

    private CategoriaResponse toResponse(Categoria categoria) {
        //Retornar a resposta
        return new CategoriaResponse(
                categoria.getId(),
                categoria.getNome()
        );

    }


}
