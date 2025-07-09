package com.everbiten.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnoreProperties("avaliacoes")
    private Restaurante restaurante;

    @Column(nullable = false)
    private String comentario;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
     
    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }

    @Column(nullable = false)
    private int nota;

    // Getters e Setters
    public Integer getId() { return id; }
    public Restaurante getRestaurante() { return restaurante; }
    public void setRestaurante(Restaurante restaurante) { this.restaurante = restaurante; }
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
    public int getNota() { return nota; }
    public void setNota(int nota) { this.nota = nota; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
}