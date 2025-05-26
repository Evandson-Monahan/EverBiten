package com.everbiten.controller;

import com.everbiten.model.Avaliacao;
import com.everbiten.model.Restaurante;
import com.everbiten.dto.AvaliacaoDTO;
import com.everbiten.repository.AvaliacaoRepository;
import com.everbiten.repository.RestauranteRepository;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping("/avaliacoes")
public class AvaliacaoController {
    private final AvaliacaoRepository avaliacaoRepository;
    private final RestauranteRepository restauranteRepository;

    public AvaliacaoController(AvaliacaoRepository avaliacaoRepository, RestauranteRepository restauranteRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.restauranteRepository = restauranteRepository;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> salvarAvaliacao(@RequestBody AvaliacaoDTO avaliacaoDTO) {
        try {
            Restaurante restaurante = restauranteRepository.findById(avaliacaoDTO.getRestauranteId())
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado"));

            Avaliacao avaliacao = new Avaliacao();
            avaliacao.setRestaurante(restaurante);
            avaliacao.setComentario(avaliacaoDTO.getComentario());
            avaliacao.setNota(avaliacaoDTO.getNota());
            avaliacao.setDataCriacao(LocalDateTime.now());

            Avaliacao avaliacaoSalva = avaliacaoRepository.save(avaliacao);
            
            return ResponseEntity.ok(Map.of(
                "id", avaliacaoSalva.getId(),
                "comentario", avaliacaoSalva.getComentario(),
                "nota", avaliacaoSalva.getNota(),
                "dataCriacao", avaliacaoSalva.getDataCriacao()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "message", "Erro ao salvar avaliação: " + e.getMessage()
            ));
        }
    }
}