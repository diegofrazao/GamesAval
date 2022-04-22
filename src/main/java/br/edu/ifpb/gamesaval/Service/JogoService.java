package br.edu.ifpb.gamesaval.Service;

import br.edu.ifpb.gamesaval.Model.Jogo;
import br.edu.ifpb.gamesaval.Repository.JogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JogoService {

    @Autowired
    private JogoRepository repository;

    public List<Jogo> getJogos(){
        return this.repository.findAll();
    }

    public Jogo getJogoById(Integer id) {
        return this.repository.findById(id).orElse(null);
    }

    public Jogo inserirJogo(Jogo jogo){
        return this.repository.save(jogo);
    }

    public void apagarJogo(Integer id){
        this.repository.deleteById(id);
    }

}
