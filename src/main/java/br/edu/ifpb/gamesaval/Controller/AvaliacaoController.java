package br.edu.ifpb.gamesaval.Controller;

import br.edu.ifpb.gamesaval.Model.Avaliacao;
import br.edu.ifpb.gamesaval.Model.Jogo;
import br.edu.ifpb.gamesaval.Service.AvaliacaoService;
import br.edu.ifpb.gamesaval.Service.JogoService;
import br.edu.ifpb.gamesaval.Service.UsuarioService;
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
import java.util.concurrent.TimeoutException;

@Controller
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;
    @Autowired
    private JogoService jogoService;
    @Autowired
    private UsuarioService usuarioService;


    @GetMapping("/avaliacao/add")
    public String addAvaliacao(Model model) {
        model.addAttribute("avaliacao", new Avaliacao());
        return "Avaliacao/add";
    }

    @GetMapping("/avaliacao/add/{id}")
    public ModelAndView adicionarAvaliacao(@PathVariable Integer id) {
        System.out.println("controler avaliacao: " + id);
        ModelAndView addView = new ModelAndView("Avaliacao/add");
        Avaliacao avaliacao = this.avaliacaoService.adicionarAvaliacao(id);
        addView.addObject("avaliacao", avaliacao);
        return addView;
    }

//    @GetMapping("/avaliacao/add/{id}")
//    public String adicionarAvaliacao(@PathVariable Integer id, Model model) {
//        System.out.println("controler avaliacao: " + id);
//        Avaliacao avaliacao = this.avaliacaoService.adicionarAvaliacao(id);
//        model.addAttribute("avaliacao", avaliacao);
//        return "Avaliacao/add";
//    }

    @GetMapping("/avaliacao/del/{id}")
    public String deletarServico(@PathVariable("id") Integer id) {
        this.avaliacaoService.apagarAvaliacao(id);
        return "redirect:/";
    }

//    @PostMapping("/avaliacao/add")
//    public String inserirAvaliacao(@ModelAttribute Avaliacao avaliacao) throws Exception {
//
////        try {
////            this.avaliacaoService.inserirAvaliacao(avaliacao);
////        }catch (Exception ex) {
////            ex.printStackTrace();
////        }
//        System.out.println("\n\nproducer Avaliacao add: " + avaliacao);
//
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("localhost");
//        factory.setPort(5672);
//
//        String dadosFila = "dadosAvaliacao";
//
//        Connection con = factory.newConnection();
//        Channel channel = con.createChannel();
//        channel.queueDeclare(dadosFila, false, false, false, null);
//
//        String dados = avaliacao.getDescricao();
//
//        channel.basicPublish("", dadosFila, null, dados.getBytes());
//        System.out.println("\n\n==> Producer: " + dados + "\n\n");
//
//        this.avaliacaoService.inserirAvaliacao();
//
//        return "redirect:/";
//    }

    @PostMapping("/avaliacao/add/{id}")
    public String inserirAvaliacao(@PathVariable("id") Integer id, @ModelAttribute Avaliacao descricao) throws Exception {

        Avaliacao avaliacao = this.avaliacaoService.getAvaliacaoById(id);
        Jogo jogo = this.jogoService.getJogoById(avaliacao.getJogo().getId());
        System.out.println("\n\nproducer Avaliacao av: " + avaliacao);
        System.out.println("\n\nproducer Avaliacao jogo: " + jogo);
        System.out.println("\n\nproducer Avaliacao id: " + id);
        System.out.println("\n\nproducer Avaliacao descricao: " + descricao.getDescricao());


        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);

        String dadosFila = "dadosAvaliacao";

        Connection con = factory.newConnection();
        Channel channel = con.createChannel();
        channel.queueDeclare(dadosFila, false, false, false, null);

        String dados = avaliacao.getId() + ";" + jogo.getId() + ";" + "1" + ";" + descricao.getDescricao();

        channel.basicPublish("", dadosFila, null, dados.getBytes());
        System.out.println("\n\n==> Producer: " + dados + "\n\n");

        this.avaliacaoService.inserirAvaliacao();

        return "redirect:/";
    }
}
