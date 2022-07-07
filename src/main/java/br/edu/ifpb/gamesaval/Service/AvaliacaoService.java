package br.edu.ifpb.gamesaval.Service;

import br.edu.ifpb.gamesaval.Model.Avaliacao;
import br.edu.ifpb.gamesaval.Model.Jogo;
import br.edu.ifpb.gamesaval.Model.Usuario;
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
    private JogoService jogoService;
    private UsuarioService usuarioService;

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

        String dadosFila = "dadosAvaliacao";

        channel.queueDeclare(dadosFila, false, false, false, null);

        DeliverCallback callback = (consumerTag, delivery) -> {
            String[] dados = new String(delivery.getBody()).split(";");
            String descricao = dados[0];

            System.out.println("Consumer: " + descricao);
            Avaliacao avaliacao = new Avaliacao();
            avaliacao.setDescricao(descricao);
            this.repository.save(avaliacao);
        };

        channel.basicConsume(dadosFila, true, callback, consumerTag -> {});
    }

    public void apagarAvaliacao(Integer id){
        this.repository.deleteById(id);
    }

}
