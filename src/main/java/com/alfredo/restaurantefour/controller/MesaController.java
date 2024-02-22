package com.alfredo.restaurantefour.controller;

import com.alfredo.restaurantefour.model.Mesa;
import com.alfredo.restaurantefour.service.IMesaService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/mesas")
public class MesaController {
    @Autowired
    public IMesaService mesaService;
    @ApiResponse(responseCode = "201", description = "Created")
    @ApiResponse(responseCode = "400", description = "Error: Data invalid")
    @PostMapping
    public ResponseEntity<Boolean> nuevaInformacionMesa(@Valid @RequestBody Mesa info, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        mesaService.nueva(info);
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }

    @ApiResponse(responseCode = "200", description = "OK: Data correct")
    @ApiResponse(responseCode = "204", description = "Error: No content")
    @GetMapping
    public ResponseEntity<Collection<Mesa>> todasMesas() {
        Collection<Mesa> mesas = mesaService.mesaTodas();
        if (mesas.isEmpty()) {
            return new ResponseEntity<>(mesas, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(mesas, HttpStatus.OK);
        }
    }

    @ApiResponse(responseCode = "200", description = "OK: Data content")
    @ApiResponse(responseCode = "204", description = "No content")
    @GetMapping("{id}")
    public ResponseEntity<Mesa> getByMesa(@PathVariable Integer id) {
        Mesa mesa = mesaService.mesita(id);
        if (mesa == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(mesa, HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = "OK: Data found.")
    @ApiResponse(responseCode = "400", description = "Error: Data invalid.")
    @ApiResponse(responseCode = "404", description = "Error: Data not found with this ID.")
    @PutMapping("{id}")
    public ResponseEntity<Void> actualizarMesa(@PathVariable Integer id, @RequestBody Mesa mesa, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        boolean actualizacionExitosa = mesaService.actualizarPorMesa(id, mesa);
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
        boolean eliminado = mesaService.eliminarMesa(id);
        if (eliminado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiResponse(responseCode = "200", description = "OK: Data found.")
    @ApiResponse(responseCode = "204", description = "No content.")
    @GetMapping("disponibles")
    public ResponseEntity<Collection<Mesa>> mesasDisponibles(
            @RequestParam int dia,
            @RequestParam int horaInicio,
            @RequestParam int horaFin) {
        Collection<Mesa> mesasDisponibles = mesaService.mesasDisponibles(dia, horaInicio, horaFin);
        if (mesasDisponibles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(mesasDisponibles, HttpStatus.OK);
        }
    }


}
