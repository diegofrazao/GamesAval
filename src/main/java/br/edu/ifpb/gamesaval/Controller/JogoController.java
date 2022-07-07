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
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Controller
public class JogoController {

    @Autowired
    JogoService jogoService;
    AvaliacaoService avaliacaoService;

    @GetMapping("/")
    public String getJogos(Model model) {
        model.addAttribute("jogos", jogoService.getJogos());
        return "Jogo/all";
    }

    @GetMapping("/jogo/add")
    public String adicionarJogo(Model model) {
        Jogo jogo = new Jogo();
        model.addAttribute("jogo", jogo);
        return "Jogo/add";
    }

    @GetMapping("/jogo/{id}")
    public ModelAndView getAvaliacoes (@PathVariable("id") Integer id) {
        ModelAndView listView = new ModelAndView("Jogo/avaliacoes");
        List<Avaliacao> avaliacaes = this.jogoService.getAvaliacoesPorJogo(id);
        listView.addObject("avaliacoes", avaliacaes);
        return listView;
    }

    @PostMapping("jogo/add")
    public String inserirJogo(@ModelAttribute Jogo jogo){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);

        String dadosFila = "dadosJogo";

        try(
            Connection con = factory.newConnection();
            Channel channel = con.createChannel()) {
            channel.queueDeclare(dadosFila, false, false, false, null);

            String dados = jogo.getNome() + ";" + jogo.getDescricao();

            channel.basicPublish("", dadosFila, null, dados.getBytes());
            System.out.println("Producer: " + dados);

            this.jogoService.inserirJogo();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }
}
