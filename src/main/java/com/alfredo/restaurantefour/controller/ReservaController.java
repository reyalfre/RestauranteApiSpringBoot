package com.alfredo.restaurantefour.controller;

import com.alfredo.restaurantefour.model.Reserva;
import com.alfredo.restaurantefour.service.IReservaService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @ApiResponse(responseCode = "201", description = "Created")
    @ApiResponse(responseCode = "400", description = "Error: Data invalid")
    @PostMapping
    public ResponseEntity<Boolean> nuevaInformacionReserva(@RequestBody Reserva info) {
        reservaService.nueva(info);
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }
    @ApiResponse(responseCode = "201", description = "Ok")
    @ApiResponse(responseCode = "400", description = "Error: No content")
    @GetMapping
    public ResponseEntity<Collection<Reserva>> todasReservas() {
        Collection<Reserva> reservas = reservaService.reservaTodas();
        if (reservas.isEmpty()) {
            return new ResponseEntity<>(reservas, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(reservas, HttpStatus.OK);
        }
    }
    @ApiResponse(responseCode = "200", description = "Data content")
    @ApiResponse(responseCode = "404", description = "Error: Not Found")
    @GetMapping("{id}")
    public ResponseEntity<Reserva> getByReserva(@PathVariable Integer id) {
        Reserva reserva = reservaService.reservaPorMesa(id);
        if (reserva == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reserva, HttpStatus.OK);
    }
    @ApiResponse(responseCode = "200", description = "Data content")
    @ApiResponse(responseCode = "400", description = "Error: Not Found")
    @ApiResponse(responseCode = "404", description = "Error: Not Found with this id")
    @ApiResponse(responseCode = "409", description = "Error: Conflict in the hours")
    @PutMapping("{id}")
    public ResponseEntity<Void> actualizarReserva(@PathVariable Integer id, @RequestBody Reserva reserva) {
        boolean actualizacionExitosa = reservaService.actualizarPorReserva(id, reserva);
        if (actualizacionExitosa) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @ApiResponse(responseCode = "204", description = "Delete success with this ID.")
    @ApiResponse(responseCode = "404", description = "Error: Not Found this ID")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminarPorReserva(@PathVariable Integer id) {
        boolean eliminado = reservaService.eliminarReserva(id);
        if (eliminado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("hoy")
    public ResponseEntity<Collection<Reserva>> reservasDelDia() {
        Collection<Reserva> reservasHoy = reservaService.reservasDelDia();
        if (reservasHoy.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(reservasHoy, HttpStatus.OK);
        }
    }



}
