package com.alfredo.restaurantefour.controller;

import com.alfredo.restaurantefour.model.Mesa;
import com.alfredo.restaurantefour.service.IMesaService;
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

   /* @PostMapping
    public ResponseEntity<Boolean> nuevaInformacionMesa(@RequestBody Mesa info) {
        mesaService.nueva(info);
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }*/
    @PostMapping
    public ResponseEntity<Boolean> nuevaInformacionMesa(@Valid @RequestBody Mesa info, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        mesaService.nueva(info);
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<Collection<Mesa>> todasMesas() {
        Collection<Mesa> mesas = mesaService.mesaTodas();
        if (mesas.isEmpty()) {
            return new ResponseEntity<>(mesas, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(mesas, HttpStatus.OK);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Mesa> getByMesa(@PathVariable Integer id) {
        Mesa mesa = mesaService.mesita(id);
        if (mesa == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(mesa, HttpStatus.OK);
    }

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

    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminarPorReserva(@PathVariable Integer id) {
        boolean eliminado = mesaService.eliminarMesa(id);
        if (eliminado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
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
