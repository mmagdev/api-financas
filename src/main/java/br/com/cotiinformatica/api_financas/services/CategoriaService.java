package br.com.cotiinformatica.api_financas.services;


import br.com.cotiinformatica.api_financas.dtos.CategoriaRequest;
import br.com.cotiinformatica.api_financas.dtos.CategoriaResponse;
import br.com.cotiinformatica.api_financas.entities.Categoria;
import br.com.cotiinformatica.api_financas.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        //Salvar a categoria no banco de dados
        categoriaRepository.save(categoria);

        //Retornar a resposta
        return new CategoriaResponse(
                categoria.getId(),
                categoria.getNome()
        );
    }
}
