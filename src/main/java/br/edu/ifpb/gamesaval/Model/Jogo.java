package br.edu.ifpb.gamesaval.Model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tb_jogos")
public class Jogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    @ManyToMany(mappedBy = "jogos")
    private List<Avaliacao> avaliacoes;

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

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(Avaliacao avaliacoesJogos) {
        avaliacoesJogos.setJogos(this);
        this.avaliacoes.add(avaliacoesJogos);
    }

    @Override
    public String toString() {
        return "Jogo{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", avaliacoes=" + avaliacoes +
                '}';
    }
}

