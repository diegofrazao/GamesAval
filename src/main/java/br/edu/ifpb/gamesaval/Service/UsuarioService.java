package br.edu.ifpb.gamesaval.Service;

import br.edu.ifpb.gamesaval.Model.Usuario;
import br.edu.ifpb.gamesaval.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public Usuario getUsuarioById(Integer id) {
        return this.repository.findById(id).orElse(null);
    }

    public Usuario inserirOuAtualizar(Usuario usuario) {
        return this.repository.save(usuario);
    }

    public void apagarUsuario(Integer id) {
        this.repository.deleteById(id);
    }

    public Boolean verificaUsuarioLogado(String login, String senha) {
        return this.repository.existsByLoginAndSenha(login, senha);
    }

}
