package br.edu.ifpb.gamesaval.Repository;

import br.edu.ifpb.gamesaval.Model.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Integer> {
}
