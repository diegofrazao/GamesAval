package br.edu.ifpb.gamesaval.Controller;

import br.edu.ifpb.gamesaval.Model.Avaliacao;
import br.edu.ifpb.gamesaval.Service.AvaliacaoService;
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
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService service;

    @GetMapping("/avaliacao")
    public String getAvaliacoes (Model model) {
        model.addAttribute("avaliacoes", service.getAvaliacoes());
        return "Avaliacao/all";
    }

    @GetMapping("/avaliacao/add")
    public String adicionarAvaliacao(Model model) {
        model.addAttribute("avaliacao", new Avaliacao());
        return "Avaliacao/add";
    }

    @PostMapping("/avaliacao/add")
    public String inserirAvaliacao(@ModelAttribute Avaliacao avaliacao) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);

        String nomeFila = "descricaoAvaliacao";

        try(
            Connection con = factory.newConnection();
            Channel channel = con.createChannel()) {
            channel.queueDeclare(nomeFila, false, false, false, null);

            String mensagem = avaliacao.getDescricao();

            channel.basicPublish("", nomeFila, null, mensagem.getBytes());
            System.out.println("Producer: " + mensagem);

            this.service.inserirAvaliacao();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        return "redirect:/avaliacao";
    }
}
