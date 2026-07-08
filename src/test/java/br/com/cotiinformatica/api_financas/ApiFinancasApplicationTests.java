package br.com.cotiinformatica.api_financas;

import br.com.cotiinformatica.api_financas.dtos.CategoriaRequest;
import br.com.cotiinformatica.api_financas.dtos.CategoriaResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApiFinancasApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


	@Test
    @DisplayName("Deve criar uma categoria com sucesso")
	public void criarCategoriaTest() throws Exception{

         //ARRANGE (Praparar os dados do teste)
        var request = new CategoriaRequest("Categoria Teste");

        //ACT (Executar um endpoint POST: /api/v1/categorias/criar)
        var result = mockMvc.perform(
                post("/api/v1/categorias/criar")
                        .contentType("application/json") //Requisição para a API
                        .content(objectMapper.writeValueAsString(request))) //Formato os dados JSON
                .andExpect(status().isCreated()) //Esperando retorno HTTP 201
                .andReturn(); //Capturando os dados da resposta



        //ASSERT (Verificar o resultado do teste)
        var jsonContent = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        var response = objectMapper.readValue(jsonContent, CategoriaResponse.class);

        //ASSERT: o ID da categoria deve vir preenchido com um UUID aleatório
        assertNotNull(response.id());

        //ASSERT: o nome da categoria retornado deve ser igual ao enviado no cadastro
        assertEquals(request.nome(), response.nome());

	}

    @Test
    @DisplayName("Deve retornar erro se o nome da categoria estiver vazio")
    public void validarNomeCategoriaObrigatorioTest() throws Exception{

        //ARRANGE (Praparar os dados do teste)
        var request = new CategoriaRequest("");

        //ACT (Executar um endpoint POST: /api/v1/categorias/criar)
        var result = mockMvc.perform(
                        post("/api/v1/categorias/criar")
                                .contentType("application/json") //Requisição para a API
                                .content(objectMapper.writeValueAsString(request))) //Formato os dados JSON
                .andExpect(status().isBadRequest()) //Esperando retorno HTTP 400
                .andReturn(); //Capturando os dados da resposta



        //ASSERT (Verificar o resultado do teste)
        var jsonContent = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertTrue(jsonContent.contains("O nome da categoria é obrigatório."));


    }

    @Test
    @DisplayName("Deve retornar erro se o nome tiver menos de 6 caracteres")
    public void validarNomeCategoriaMinimoDeCaracteresTest() throws Exception{

        //ARRANGE (Praparar os dados do teste)
        var request = new CategoriaRequest("Teste");

        //ACT (Executar um endpoint POST: /api/v1/categorias/criar)
        var result = mockMvc.perform(
                        post("/api/v1/categorias/criar")
                                .contentType("application/json") //Requisição para a API
                                .content(objectMapper.writeValueAsString(request))) //Formato os dados JSON
                .andExpect(status().isBadRequest()) //Esperando retorno HTTP 400
                .andReturn(); //Capturando os dados da resposta



        //ASSERT (Verificar o resultado do teste)
        var jsonContent = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertTrue(jsonContent.contains("O nome da categoria deve ter pelo menos 6 caracteres."));


    }

}
