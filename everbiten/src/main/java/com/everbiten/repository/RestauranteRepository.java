package com.everbiten.repository;

import com.everbiten.model.Restaurante;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Integer> {
    List<Restaurante> findByNomeContainingIgnoreCase(String nome);
}