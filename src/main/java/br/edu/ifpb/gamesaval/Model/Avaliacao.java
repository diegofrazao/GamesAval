package br.edu.ifpb.gamesaval.Model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tb_avaliacoes")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descricao;
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.LAZY)
    private Jogo jogo;

    public Avaliacao() {
    }

    public Avaliacao(String descricao, Usuario usuario) {
        this.descricao = descricao;
        this.usuario = usuario;
    }

    public Integer getId() {
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
        usuario.setAvaliacoes(this);
        this.usuario = usuario;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo avaliacaoJogo) {
        avaliacaoJogo.setAvaliacoes(this);
        this.jogo = avaliacaoJogo;
    }

    @Override
    public String toString() {
        return "Avaliacao{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", usuario=" + usuario.getNome() +
                ", jogo=" + jogo.getNome() +
                '}';
    }
}
