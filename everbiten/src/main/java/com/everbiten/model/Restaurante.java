package com.everbiten.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private String culinaria;

    @Column(nullable = false)
    private int anoFundacao;

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("restaurante")
    private List<Avaliacao> avaliacoes;

    // Getters e Setters
    public Integer getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getCulinaria() { return culinaria; }
    public void setCulinaria(String culinaria) { this.culinaria = culinaria; }
    public int getAnoFundacao() { return anoFundacao; }
    public void setAnoFundacao(int anoFundacao) { this.anoFundacao = anoFundacao; }
    public List<Avaliacao> getAvaliacoes() { return avaliacoes; }
    public void setAvaliacoes(List<Avaliacao> avaliacoes) { this.avaliacoes = avaliacoes; }
}