package br.edu.ifpb.gamesaval.Service;

import br.edu.ifpb.gamesaval.Model.Usuario;
import br.edu.ifpb.gamesaval.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    public Usuario UsuarioId(Integer id) {
        return this.usuarioRepository.findById(id).orElse(null);
    }

    public Usuario inserirOuAtualizar(Usuario usuario) {
        return this.usuarioRepository.save(usuario);
    }


    public void apagarUsuario(Integer id) {
        this.usuarioRepository.deleteById(id);
    }


    public Boolean verificaUsuarioLogado(String login, String senha) {
        return this.usuarioRepository.existsByLoginAndSenha(login, senha);
    }

}
