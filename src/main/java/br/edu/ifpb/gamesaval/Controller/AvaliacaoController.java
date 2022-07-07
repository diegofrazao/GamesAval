package br.edu.ifpb.gamesaval.Controller;

import br.edu.ifpb.gamesaval.Model.Avaliacao;
import br.edu.ifpb.gamesaval.Model.Jogo;
import br.edu.ifpb.gamesaval.Service.AvaliacaoService;
import br.edu.ifpb.gamesaval.Service.JogoService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Controller
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;
    private JogoService jogoService;

    @GetMapping("/avaliacao/add")
    public String adicionarAvaliacao(Model model) {
        model.addAttribute("avaliacao", new Avaliacao());
        return "Avaliacao/add";
    }

    @GetMapping("/avaliacao/del/{id}")
    public String deletarServico(@PathVariable("id") Integer id) {
        this.avaliacaoService.apagarAvaliacao(id);
        return "redirect:/";
    }

    @PostMapping("/avaliacao/add")
    public String inserirAvaliacao(@ModelAttribute Avaliacao avaliacao) {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);

        String dadosFila = "dadosAvaliacao";

        try(
            Connection con = factory.newConnection();
            Channel channel = con.createChannel()) {
            channel.queueDeclare(dadosFila, false, false, false, null);

            String dados = avaliacao.getDescricao();

            channel.basicPublish("", dadosFila, null, dados.getBytes());
            System.out.println("Producer: " + dados);

            this.avaliacaoService.inserirAvaliacao();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }
}
