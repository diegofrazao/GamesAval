package br.edu.ifpb.gamesaval.Controller;

import br.edu.ifpb.gamesaval.Model.Avaliacao;
import br.edu.ifpb.gamesaval.Model.Jogo;
import br.edu.ifpb.gamesaval.Service.AvaliacaoService;
import br.edu.ifpb.gamesaval.Service.JogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

    @PostMapping("jogo/add")
    public String inserirJogo(){

        return "redirect:/";
    }
}
