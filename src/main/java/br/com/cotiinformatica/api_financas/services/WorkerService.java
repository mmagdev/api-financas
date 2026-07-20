package br.com.cotiinformatica.api_financas.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class WorkerService {


    /*
    Método para leer e processar cada registro
    contido na fila da mensageria para o agente de IA
    @Payload -> Dados gravados na fila
    */

    @RabbitListener(queues = "relatorios-movimentacoes")
    public void listener(@Payload String payload) throws Exception {
        //TODO enviar os dados da mensageria para a API do agente de IA
        System.out.println("\n\n**********");
        System.out.println("\n\nDADOS TRANSMITIDOS COM SUCESSO!");
        System.out.println("\nPAYLOAD:" + payload);
        System.out.println("\n\n**********");

    }
}
