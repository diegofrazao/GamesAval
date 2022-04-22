package br.edu.ifpb.gamesaval.Repository;

import br.edu.ifpb.gamesaval.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    public Boolean existsByLoginAndSenha(String login, String senha);

}
