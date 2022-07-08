package br.edu.ifpb.gamesaval.Service;

import br.edu.ifpb.gamesaval.Model.Avaliacao;
import br.edu.ifpb.gamesaval.Model.Jogo;
import br.edu.ifpb.gamesaval.Repository.JogoRepository;
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
public class JogoService {

    @Autowired
    private JogoRepository jogoRepository;

    public List<Jogo> getJogos(){
        return this.jogoRepository.findAll();
    }

    public Jogo getJogoById(Integer id) {
        return this.jogoRepository.findById(id).orElse(null);
    }

    public List<Avaliacao> getAvaliacoesPorJogo(Integer jogoId) {
        Jogo jogo = this.getJogoById(jogoId);

        if (jogo != null) {
            return jogo.getAvaliacoes();
        }

        return null;
    }

    public void inserirJogo() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection con = factory.newConnection();
        Channel channel = con.createChannel();

        String dadosFila = "dadosJogo";

        channel.queueDeclare(dadosFila, false, false, false, null);

        DeliverCallback callback = (consumerTag, delivery) -> {
            String[] dados = new String(delivery.getBody()).split(";");
            String nome = dados[0];
            String descricao = dados[1];
            Jogo jogo = new Jogo(nome, descricao);
            this.jogoRepository.save(jogo);
        };

        channel.basicConsume(dadosFila, true, callback, consumerTag -> {});
    }

}
