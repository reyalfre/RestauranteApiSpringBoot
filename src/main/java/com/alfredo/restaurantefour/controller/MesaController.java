package com.alfredo.restaurantefour.controller;

import com.alfredo.restaurantefour.model.Mesa;
import com.alfredo.restaurantefour.service.IMesaService;
import io.swagger.v3.oas.annotations.Operation;
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

    /**
     * Método nuevaInformacionReserva: Añadir una nueva mesa.
     *
     * @param info
     * @param result
     * @return La nueva mesa.
     */
    @Operation(
            summary = "Añadir una nueva mesa",
            description = "Añade una nueva mesa")
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

    /**
     * Método todasMesas: Listar todas las mesas.
     *
     * @return 200 o 204.
     */
    @Operation(
            summary = "Listar todas las mesas",
            description = "Lista todas las mesas que hay actualmente")
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

    /**
     * Método getByMesa: Ver detalles de una mesa específica.
     *
     * @param id
     * @return 200 o 204.
     */
    @Operation(
            summary = "Ver detalles de una mesa específica",
            description = "Obtiene los detalles de una mesa")
    @ApiResponse(responseCode = "200", description = "OK: Data content")
    @ApiResponse(responseCode = "404", description = "Not Found")
    @GetMapping("{id}")
    public ResponseEntity<Mesa> getByMesa(@PathVariable Integer id) {
        Mesa mesa = mesaService.mesita(id);
        if (mesa == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mesa, HttpStatus.OK);
    }

    /**
     * Método actualizarMesa: Modificar una mesa. Envía los datos y se modificará entera.
     *
     * @param id
     * @param mesa
     * @param result
     * @return 200, 400, 404
     */
    @Operation(
            summary = "Modificar una mesa",
            description = "Modifica una mesa. Envía los datos y se modificará entera.")
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

    /**
     * Método eliminarPorReserva: Eliminar una mesa.
     *
     * @param id El id que se va a eliminar
     * @return 204 o 404
     */
    @Operation(
            summary = "Eliminar una mesa",
            description = "Elimina una mesa dado por su id")
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

    /**
     * Método mesasDisponibles: Consultar mesas disponibles para un día y franja de horas (hora inicio, hora fin). Es
     * decir, mesas que existan para las cuales no haya reservas en el día y horas solicitadas.
     *
     * @param dia
     * @param horaInicio
     * @param horaFin
     * @return Las mesas disponibles
     */
    @Operation(
            summary = "Consultar mesas disponibles para un día y franja de horas",
            description = "Consultar mesas disponibles para un día y franja de horas (hora inicio, hora fin). Es decir, mesas que existan para las cuales no haya reservas en el día y horas solicitadas.")
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
