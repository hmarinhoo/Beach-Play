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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.beach_play_api.model.Reservation;
import br.com.fiap.beach_play_api.repository.ReservationRepository;
import br.com.fiap.beach_play_api.specification.ReservationSpecification;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/reservations")
@Slf4j
public class ReservationController {

    @Autowired
    private ReservationRepository repository;

    // Método Post - Para criar uma reserva.
    @CacheEvict(value = "reservas", allEntries = true)
    @PostMapping
    @Operation(summary = "Faz uma nova reserva.", description = "Insere uma nova reserva ao sistema.")
    public ResponseEntity<?> create(@RequestBody @Valid Reservation reservation) {
        if (reservation.getUserId() == null || reservation.getUserId() <= 0) {
            return ResponseEntity.badRequest().body("Usuário não está logado ou ID inválido.");
        }

        Reservation saved = repository.save(reservation);
        return ResponseEntity.status(201).body(saved);
    }

    // Busca reservas com filtros e paginação (unificado)
    @GetMapping
    @Cacheable("reservas")
    @Operation(summary = "Lista as reservas.", description = "Retorna todas as reservas cadastradas, com filtros e paginação opcionais.")
    public Page<Reservation> index(
            @RequestParam(required = false) Integer quadra,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Long userId,
            @PageableDefault(size = 10, sort = "data", direction = Direction.DESC) Pageable pageable) {
    
        var filters = new ReservationFilter(quadra, startDate, endDate, userId);
        var specification = ReservationSpecification.withFilters(filters);
        return repository.findAll(specification, pageable);
    }
    

    // Busca uma reserva pelo id
    @GetMapping("/{id}")
    @Operation(summary = "Busca e lista reserva por id.", description = "Retorna a reserva correspondente ao id.")
    public ResponseEntity<Reservation> get(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Busca todas as reservas por id do usuário (sem paginação)
    @GetMapping("/usuario/{userId}")
    @Operation(summary = "Busca reservas por usuário.", description = "Retorna todas as reservas do usuário informado.")
    public List<Reservation> getByUserId(@PathVariable Long userId) {
        return repository.findByUserId(userId);
    }

    // Edita uma reserva
    @PutMapping("/{id}")
    @Operation(summary = "Edita uma reserva.", description = "Permite a edição dos dados de uma reserva já cadastrada.")
    @CacheEvict(value = "reservas", allEntries = true)
    public ResponseEntity<Reservation> update(@PathVariable Long id, @RequestBody @Valid Reservation reservation) {
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

    // Record para filtros
    public record ReservationFilter(
            Integer quadra,
            LocalDate startDate,
            LocalDate endDate,
            Long userId) {
    }
}
