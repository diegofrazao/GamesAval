package br.edu.ifpb.gamesaval.Service;

import br.edu.ifpb.gamesaval.Model.Avaliacao;
import br.edu.ifpb.gamesaval.Repository.AvaliacaoRepository;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository repository;

    public List<Avaliacao> getAvaliacoes(){
        return this.repository.findAll();
    }

    public Avaliacao getAvaliacaoById(Integer id){
        return this.repository.findById(id).orElse(null);
    }

    public void inserirAvaliacao() throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection con = factory.newConnection();
        Channel channel = con.createChannel();

        String nomeFila = "descricaoAvaliacao";

        channel.queueDeclare(nomeFila, false, false, false, null);

        DeliverCallback callback = (consumerTag, delivery) -> {
            String mensagem = new String(delivery.getBody());
            System.out.println("Consumer: " + mensagem);
            Avaliacao avaliacao = new Avaliacao();
            avaliacao.setDescricao(mensagem);
            this.repository.save(avaliacao);
        };

        channel.basicConsume(nomeFila, true, callback, consumerTag -> {
            System.out.println("\n\nConsumer: " + consumerTag);
            System.out.println("\n\nCallback: " + callback);
        });
    }

    public void apagarAvaliacao(Integer id){
        this.repository.deleteById(id);
    }

}
