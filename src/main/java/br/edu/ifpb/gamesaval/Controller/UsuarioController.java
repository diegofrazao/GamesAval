package br.edu.ifpb.gamesaval.Controller;

import br.edu.ifpb.gamesaval.Model.Usuario;
import br.edu.ifpb.gamesaval.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
