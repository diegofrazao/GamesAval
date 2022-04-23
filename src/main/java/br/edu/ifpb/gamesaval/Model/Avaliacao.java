package br.edu.ifpb.gamesaval.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tb_avaliacoes")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Usuario usuario;
    @ManyToMany
    private List<Jogo> jogos;

    public Avaliacao() {
    }

    public Avaliacao(String descricao, Usuario usuario) {
        this.descricao = descricao;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Jogo> getJogos() {
        return jogos;
    }

    public void setJogos(Jogo avaliacoesJogos) {
        avaliacoesJogos.setAvaliacoes(this);
        this.jogos.add(avaliacoesJogos);
    }
}
