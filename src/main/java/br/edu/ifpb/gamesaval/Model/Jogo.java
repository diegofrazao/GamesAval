package br.edu.ifpb.gamesaval.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_jogos")
public class Jogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    @ManyToMany(mappedBy = "avaliacoesJogos", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Avaliacao> avaliacoesJogos = new ArrayList<>();

    public Jogo() {
    }

    public Jogo(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Avaliacao> getAvaliacoesJogos() {
        return avaliacoesJogos;
    }

    public void setAvaliacoesJogos(Avaliacao avaliacoesJogos) {
        avaliacoesJogos.setAvaliacoesJogos(this);
        this.avaliacoesJogos.add(avaliacoesJogos);
    }
}

