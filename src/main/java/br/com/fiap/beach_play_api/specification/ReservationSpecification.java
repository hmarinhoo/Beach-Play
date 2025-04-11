package br.com.fiap.beach_play_api.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.com.fiap.beach_play_api.controller.ReservationController.ReservationFilter;
import br.com.fiap.beach_play_api.model.Reservation;
import jakarta.persistence.criteria.Predicate;

public class ReservationSpecification {

    public static Specification<Reservation> withFilters(ReservationFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro pela quadra
            if (filter.quadra() != null) {
                predicates.add(cb.equal(root.get("quadra"), filter.quadra()));
            }

            // Filtro pelo userId
            if (filter.userId() != null) {
                predicates.add(cb.equal(root.get("userId"), filter.userId()));
            }

            // Filtro pela data
            if (filter.startDate() != null && filter.endDate() != null) {
                predicates.add(cb.between(root.get("data"), filter.startDate(), filter.endDate()));
            }

            // Retorna os filtros combinados
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
