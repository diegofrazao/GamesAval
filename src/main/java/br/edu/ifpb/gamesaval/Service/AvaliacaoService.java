package br.edu.ifpb.gamesaval.Service;

import br.edu.ifpb.gamesaval.Model.Avaliacao;
import br.edu.ifpb.gamesaval.Repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    public List<Avaliacao> getAvaliacoes(){
        return this.avaliacaoRepository.findAll();
    }

    public Avaliacao getAvaliacaoId(Integer idAvaliacao){
        return this.avaliacaoRepository.findById(idAvaliacao).orElse(null);
    }

    public Avaliacao inserirAvaliacao(Avaliacao avaliacao){
        return this.avaliacaoRepository.save(avaliacao);
    }

    public void apagarAvaliacao(Integer idAvaliacao){
        this.avaliacaoRepository.deleteById(idAvaliacao);
    }

}
