package br.edu.ifpb.gamesaval.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_avaliacoes")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Usuario usuario;
    @ManyToMany(mappedBy = "avaliacoesJogos", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Jogo> avaliacoesJogos = new ArrayList<>();

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

    public List<Jogo> getAvaliacoesJogos() {
        return avaliacoesJogos;
    }

    public void setAvaliacoesJogos(Jogo avaliacoesJogos) {
        avaliacoesJogos.setAvaliacoesJogos(this);
        this.avaliacoesJogos.add(avaliacoesJogos);
    }
}
