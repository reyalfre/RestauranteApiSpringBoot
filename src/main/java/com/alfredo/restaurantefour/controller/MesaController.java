package com.alfredo.restaurantefour.controller;

import com.alfredo.restaurantefour.model.Mesa;
import com.alfredo.restaurantefour.service.IMesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/mesas")
public class MesaController {
    @Autowired
    public IMesaService mesaService;

    @PostMapping
    public ResponseEntity<Boolean> nuevaInformacionMesa(@RequestBody Mesa info) {
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
    public ResponseEntity<Mesa> getByMesa(@PathVariable String id) {
        Mesa mesa = mesaService.mesita(id);
        if (mesa == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(mesa, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> actualizarMesa(@PathVariable String id, @RequestBody Mesa mesa) {
        boolean actualizacionExitosa = mesaService.actualizarPorMesa(id, mesa);
        if (actualizacionExitosa) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminarPorReserva(@PathVariable String id) {
        boolean eliminado = mesaService.eliminarMesa(id);
        if (eliminado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
