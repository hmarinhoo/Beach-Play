
package br.com.fiap.beach_play_api.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.beach_play_api.model.Reservation;
import br.com.fiap.beach_play_api.repository.ReservationRepository;
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/reservations") 
public class ReservationController {

	@Autowired
private ReservationRepository repository;


	// Método Get - Retorna as reservas cadastradas.
	@GetMapping
public List<Reservation> index() {
    return repository.findAll();
}


	// Método Post - Para criar uma reserva.
	@PostMapping
public ResponseEntity<?> create(@RequestBody @Valid Reservation reservation) {
    if (reservation.getUserId() == null || reservation.getUserId() <= 0) {
        return ResponseEntity.status(400).body("Usuário não está logado ou ID inválido.");
    }

    Reservation saved = repository.save(reservation);
    return ResponseEntity.status(201).body(saved);
}


@GetMapping("/{id}")
public ResponseEntity<Reservation> get(@PathVariable Long id) {
    return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}


@DeleteMapping("/{id}")
public ResponseEntity<Void> delete(@PathVariable Long id) {
    if (repository.existsById(id)) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
}

@PutMapping("/{id}")
public ResponseEntity<Reservation> update(@PathVariable Long id, @RequestBody Reservation reservation) {
    if (!repository.existsById(id)) {
        return ResponseEntity.notFound().build();
    }
    reservation.setId(id);
    Reservation updated = repository.save(reservation);
    return ResponseEntity.ok(updated);
}

@GetMapping("/usuario/{userId}")
public List<Reservation> getByUserId(@PathVariable Long userId) {
    return repository.findByUserId(userId);
}

}