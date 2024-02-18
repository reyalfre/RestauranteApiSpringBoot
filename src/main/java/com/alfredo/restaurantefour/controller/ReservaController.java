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
    public ResponseEntity<Reserva> getByReserva(@PathVariable String id) {
        Reserva reserva = reservaService.reservaPorMesa(id);
        if (reserva == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reserva, HttpStatus.OK);
    }
    @PutMapping("{id}")
    public ResponseEntity<Void> actualizarReserva(@PathVariable String id, @RequestBody Reserva reserva){
        boolean actualizacionExitosa = reservaService.actualizarPorReserva(id, reserva);
        if (actualizacionExitosa){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminarPorReserva(@PathVariable String id){
        boolean eliminado = reservaService.eliminarReserva(id);
        if (eliminado){
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
