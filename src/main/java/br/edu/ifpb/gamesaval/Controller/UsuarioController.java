package br.edu.ifpb.gamesaval.Controller;

import br.edu.ifpb.gamesaval.Model.Usuario;
import br.edu.ifpb.gamesaval.Service.UsuarioService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Controller
public class UsuarioController {

    @Autowired
    UsuarioService service;

    @GetMapping("/usuario/add")
    public String inserirOuAtualizar(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "Usuarios/add";

    }

    @PostMapping("/usuario/add")
    public String inserirOuAtualizar(@ModelAttribute Usuario usuario) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);

        String nomeFila = "nomeUsuario";

        try(
            Connection con = factory.newConnection();
            Channel channel = con.createChannel()) {
            channel.queueDeclare(nomeFila, false, false, false, null);

            String dados = usuario.getNome() + " " + usuario.getLogin() + " " + usuario.getSenha();

            channel.basicPublish("", nomeFila, null, dados.getBytes());
            System.out.println("Producer: " + dados);

            this.service.inserirOuAtualizar();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }
}
