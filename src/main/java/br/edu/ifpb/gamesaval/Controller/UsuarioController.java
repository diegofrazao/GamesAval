package br.edu.ifpb.gamesaval.Controller;

import br.edu.ifpb.gamesaval.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioController {

    @Autowired
    UsuarioService service;

    @GetMapping("/")
    public String getUsers(Model model) {
        model.addAttribute("usuarios", service.getUsuarios());
        return "Usuarios/all";
    }

}