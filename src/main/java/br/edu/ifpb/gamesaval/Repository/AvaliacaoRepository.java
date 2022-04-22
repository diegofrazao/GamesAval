package br.edu.ifpb.gamesaval.Repository;

import br.edu.ifpb.gamesaval.Model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {
}
