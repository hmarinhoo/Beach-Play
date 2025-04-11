
package br.com.fiap.beach_play_api.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.beach_play_api.model.Reservation;
import br.com.fiap.beach_play_api.repository.ReservationRepository;
import br.com.fiap.beach_play_api.specification.ReservationSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/reservations")
@Slf4j

public class ReservationController {
    public record ReservationFilter(
            Integer quadra,
            LocalDate startDate,
            LocalDate endDate,
            Long userId) {
    }

    @Autowired
    private ReservationRepository repository;

    // Método Post - Para criar uma reserva.
    @CacheEvict(value = "reservas", allEntries = true) // serve para limpar o cache quando salvar
    @PostMapping
    @Operation(summary = "Faz uma nova reserva.", description = "Insere uma nova reserva ao sistema.")
    public ResponseEntity<?> create(@RequestBody @Valid Reservation reservation) {
        if (reservation.getUserId() == null || reservation.getUserId() <= 0) {
            return ResponseEntity.status(400).body("Usuário não está logado ou ID inválido.");
        }

        Reservation saved = repository.save(reservation);
        return ResponseEntity.status(201).body(saved);
    }

    // Método Get - todas as reservas cadastradas, independente do id do usuario
    @Cacheable("reservas")
    @GetMapping
    @Operation(summary = "Lista as reservas.", description = "Retorna todas as reservas cadastrados no sistema.")
    public List<Reservation> index() {
        return repository.findAll();
    }

    // Busca as reservas por id da reserva
    @GetMapping("/{id}")
    @Operation(summary = "Busca e lista reservas por id da reserva.", description = "Retorna todas as reservas cadastradas no sistema que correspondem com o id aleatório gerado na criação da reserva.")
    public ResponseEntity<Reservation> get(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Busca todas as reservas por id do usuario
    @GetMapping("/usuario/{userId}")
    @Operation(summary = "Busca e lista reservas por id de cadastro.", description = "Retorna todas as reservas cadastradas no sistema que correspondem o userId com o id do cadastro de usuário.")
    public List<Reservation> getByUserId(@PathVariable Long userId) {
        return repository.findByUserId(userId);
    }

    // Método para buscar reservas com filtros e paginação
    @GetMapping
    public Page<Reservation> index(ReservationFilter filters,
            @PageableDefault(size = 10, sort = "date", direction = Direction.DESC) Pageable pageable) {
        var specification = ReservationSpecification.withFilters(filters);
        return repository.findAll(specification, pageable);
    }

    // Edita a reserva
    @PutMapping("/{id}")
    @Operation(summary = "Edita uma reserva.", description = "Permite a edição de dados de reservas já cadastradas no sistema.")
    @CacheEvict(value = "reservas", allEntries = true)
    public ResponseEntity<Reservation> update(@PathVariable Long id, @RequestBody Reservation reservation) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        reservation.setId(id);
        Reservation updated = repository.save(reservation);
        return ResponseEntity.ok(updated);
    }

    // Deleta a reserva
    @DeleteMapping("/{id}")
    @Operation(summary = "Apaga uma reserva.", description = "Deleta a reserva correspondente ao id passado.")
    @CacheEvict(value = "reservas", allEntries = true)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}