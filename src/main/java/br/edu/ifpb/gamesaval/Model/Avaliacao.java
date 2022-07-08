package br.edu.ifpb.gamesaval.Model;

import javax.persistence.*;

@Entity
@Table(name = "tb_avaliacoes")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descricao;
    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private Jogo jogo;

    public Avaliacao() {
    }

    public Avaliacao(String descricao) {
        this.descricao = descricao;
    }

    public Avaliacao(String descricao, Jogo jogo) {
        this.descricao = descricao;
        this.jogo = jogo;
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
        this.usuario = usuario;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    @Override
    public String toString() {
        return "Avaliacao{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", jogo=" + this.jogo.getId() +
                '}';
    }
}
