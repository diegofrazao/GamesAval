package br.edu.ifpb.gamesaval.Service;

import br.edu.ifpb.gamesaval.Model.Avaliacao;
import br.edu.ifpb.gamesaval.Repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository repository;

    public List<Avaliacao> getAvaliacoes(){
        return this.repository.findAll();
    }

    public Avaliacao getAvaliacaoById(Integer id){
        return this.repository.findById(id).orElse(null);
    }

    public Avaliacao inserirAvaliacao(Avaliacao avaliacao){
        return this.repository.save(avaliacao);
    }

    public void apagarAvaliacao(Integer id){
        this.repository.deleteById(id);
    }

}
