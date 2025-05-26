package com.everbiten.controller;

import com.everbiten.model.Restaurante;
import com.everbiten.repository.RestauranteRepository;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping("/restaurantes")
public class RestauranteController {

    private final RestauranteRepository restauranteRepository;

    public RestauranteController(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @GetMapping
    public String listarRestaurantes(Model model) {
        model.addAttribute("restaurantes", restauranteRepository.findAll());
        return "lista-restaurantes";
    }
    
    @GetMapping("/novo")
    public String mostrarFormularioNovoRestaurante(@RequestParam(required = false) Integer id, Model model) {
        Restaurante restaurante = id == null ? new Restaurante() : restauranteRepository.findById(id).orElse(new Restaurante());
        model.addAttribute("restaurante", restaurante);
        return "form-restaurante";
    }

    @GetMapping("/{id}")
    public String detalhesRestaurante(@PathVariable Integer id, Model model) {
        Optional<Restaurante> restaurante = restauranteRepository.findById(id);
        if (restaurante.isEmpty()) {
            return "erro";
        }
        model.addAttribute("restaurante", restaurante.get());
        return "detalhes-restaurante";
    }

    @GetMapping("/editar/{id}")
    public String editarRestaurante(@PathVariable Integer id, Model model) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de restaurante inválido: " + id));
        model.addAttribute("restaurante", restaurante);
        return "form-restaurante";
    }

    @PostMapping(value = "/salvar", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> salvarRestaurante(@RequestBody Restaurante restaurante) {
        try {
            if (restaurante.getNome() == null || restaurante.getNome().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("message", "O nome do restaurante é obrigatório"));
            }
            
            if (restaurante.getCulinaria() == null || restaurante.getCulinaria().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("message", "O tipo de culinária é obrigatório"));
            }
            
            Restaurante restauranteSalvo = restauranteRepository.save(restaurante);
            return ResponseEntity.ok(restauranteSalvo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Erro ao salvar restaurante: " + e.getMessage()));
        }
    }

    @PostMapping("/excluir/{id}")
    public String excluirRestaurante(@PathVariable Integer id) {
        restauranteRepository.deleteById(id);
        return "redirect:/restaurantes";
    }
}