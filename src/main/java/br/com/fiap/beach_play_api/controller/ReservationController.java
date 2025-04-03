
package br.com.fiap.beach_play_api.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale.Category;

import org.springframework.http.HttpStatus;
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
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.beach_play_api.model.Reservation;
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000") // Permite requisições do frontend
@RestController
@RequestMapping("/reservations") // definindo as rotas
public class ReservationController {

	private List<Reservation> repository = new ArrayList<>(List.of(
			new Reservation(1L, 1, LocalDate.of(2025, 3, 1), LocalTime.of(10, 0))));

	// Método Get - Retorna as reservas cadastradas.
	@GetMapping
	public List<Reservation> index() {
		return repository;
	}

	// Método Post - Para criar uma reserva.
	@PostMapping
	public ResponseEntity<Reservation> create(@RequestBody @Valid Reservation reservation) {
		System.out.println("Cadastrando a reserva na quadra: " + reservation.getQuadra());
		repository.add(reservation);
		return ResponseEntity.status(201).body(reservation);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Reservation> get(@PathVariable Long id) {
		System.out.println("Buscando reserva " + id);
		var res = repository.stream()
				.filter(c -> c.getId().equals(id))
				.findFirst();
		return res.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		System.out.println("Removendo reserva " + id);

		boolean removed = repository.removeIf(res -> res.getId().equals(id));

		if (removed) {
			return ResponseEntity.noContent().build(); // Retorna 204 No Content se foi removido
		} else {
			return ResponseEntity.notFound().build(); // Retorna 404 se o ID não existir
		}
	}

	// Método Put para o update
	@PutMapping("/{id}")
    public ResponseEntity<Reservation> update(@PathVariable Long id, @RequestBody Reservation reservation) {
        System.out.println("Atualizando reserva " + reservation);

        for (int i = 0; i < repository.size(); i++) {
            if (repository.get(i).getId().equals(id)) {
                reservation.setId(id);
                repository.set(i, reservation);
                return ResponseEntity.ok(reservation);
            }
        }
        return ResponseEntity.notFound().build();
    }

    private Reservation getReservation(Long id) {
        return repository.stream()
                .filter(res -> res.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}