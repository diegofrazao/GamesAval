package br.edu.ifpb.gamesaval.Service;

import br.edu.ifpb.gamesaval.Model.Usuario;
import br.edu.ifpb.gamesaval.Repository.UsuarioRepository;
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
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public List<Usuario> getUsuarios() {
        return repository.findAll();
    }

    public Usuario getUsuarioById(Integer id) {
        return this.repository.findById(id).orElse(null);
    }

    public void inserirOuAtualizar() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection con = factory.newConnection();
        Channel channel = con.createChannel();

        String dadosFila = "dadosUsuario";

        channel.queueDeclare(dadosFila, false, false, false, null);

        DeliverCallback callback = (consumerTag, delivery) -> {
            String[] dados = new String(delivery.getBody()).split(";");
            String nome = dados[0];
            String login = dados[1];
            String senha = dados[2];

            Usuario usuario = new Usuario(nome, login, senha);

            System.out.println("Service user=> " + usuario);
            this.repository.save(usuario);
        };

        channel.basicConsume(dadosFila, true, callback, consumerTag -> {});
    }

    public void apagarUsuario(Integer id) {
        this.repository.deleteById(id);
    }

    public Boolean verificaUsuarioLogado(String login, String senha) {
        return this.repository.existsByLoginAndSenha(login, senha);
    }

}
