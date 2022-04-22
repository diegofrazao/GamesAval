package br.edu.ifpb.gamesaval.Service;

import br.edu.ifpb.gamesaval.Model.Jogo;
import br.edu.ifpb.gamesaval.Repository.JogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class jogoService {

    @Autowired
    private JogoRepository jogoRepository;

    public List<Jogo> getJogos(){
        return this.jogoRepository.findAll();
    }
    public Jogo getJogoId(Integer idJogo) {
        return this.jogoRepository.findById(idJogo).orElse(null);
    }
    public Jogo inserirJogo(Jogo jogo){
        return this.jogoRepository.save(jogo);
    }

    public void apagarJogo(Integer idJogo){
        this.jogoRepository.deleteById(idJogo);
    }

}
