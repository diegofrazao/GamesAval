package br.edu.ifpb.gamesaval.Controller;

import br.edu.ifpb.gamesaval.Service.JogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JogoController {

    @Autowired
    JogoService service;
    @GetMapping("/")

    public String getJogos(Model model) {
        model.addAttribute("jogos", service.getJogos());
        return "Jogo/all";
    }
}
