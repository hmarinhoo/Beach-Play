package br.com.fiap.beach_play_api.controller;

import br.com.fiap.beach_play_api.model.Reservation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {
    @RequestMapping(path="/home",produces="application/json",method = {RequestMethod.GET})
	public Reservation index(){
		return new Reservation(null, 0, null, null);
	}
    
}
