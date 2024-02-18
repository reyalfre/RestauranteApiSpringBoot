package com.alfredo.restaurantefour.controller;

import com.alfredo.restaurantefour.model.Reserva;
import com.alfredo.restaurantefour.service.IReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/reservas")
public class ReservaController {
    @Autowired
    public IReservaService reservaService;

    @PostMapping
    public ResponseEntity<Boolean> nuevaInformacionReserva(@RequestBody Reserva info) {
        reservaService.nueva(info);
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<Collection<Reserva>> todasReservas() {
        Collection<Reserva> reservas = reservaService.reservaTodas();
        if (reservas.isEmpty()) {
            return new ResponseEntity<>(reservas, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(reservas, HttpStatus.OK);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Reserva> getByReserva(@PathVariable String nombre) {
        Reserva reserva = reservaService.reservaPorMesa(nombre);
        if (reserva == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reserva, HttpStatus.OK);
    }


}
