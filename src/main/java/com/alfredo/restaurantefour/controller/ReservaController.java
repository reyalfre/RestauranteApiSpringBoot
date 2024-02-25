package com.alfredo.restaurantefour.controller;

import com.alfredo.restaurantefour.model.Reserva;
import com.alfredo.restaurantefour.service.IReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static com.alfredo.restaurantefour.service.ReservaService.datosReservaPorMesa;

@RestController
@RequestMapping("/reservas")
public class ReservaController {
    @Autowired
    public IReservaService reservaService;

    /**
     * Método nuevaInformacionReserva: Crear una nueva reserva.
     *
     * @param info
     * @return 201, 400
     */
    @Operation(
            summary = "Crear una nueva reserva",
            description = "Crear una nueva reserva")
    @ApiResponse(responseCode = "201", description = "Created")
    @ApiResponse(responseCode = "400", description = "Error: Data invalid")
    @ApiResponse(responseCode = "409", description = "Error: Conflict")
    @PostMapping
    public ResponseEntity<Boolean> nuevaInformacionReserva(@RequestBody Reserva info) {
        // Intenta crear la nueva reserva utilizando el servicio
        boolean creacionExitosa = reservaService.nueva(info);

        // Verifica si la creación de la reserva fue exitosa
        if (creacionExitosa) {
            // Si la creación fue exitosa, devuelve un código HttpStatus "CREATED" (201)
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        } else {
            // Si no se pudo crear la reserva debido a algún error de validación u otro motivo,
            // verifica si el motivo del fallo fue un conflicto de disponibilidad (código 409)
            for (Reserva reservaExistente : datosReservaPorMesa.values()) {
                if (reservaExistente.getMesa().equals(info.getMesa()) &&
                        reservaExistente.getDia() == info.getDia() &&
                        reservaExistente.getHoraInicio() == info.getHoraInicio()) {
                    // Si hay una reserva existente para la misma mesa, día y hora, devuelve un código HttpStatus "CONFLICT" (409)
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
            }
            // Si no hay conflicto de disponibilidad, devuelve un código HttpStatus "BAD REQUEST" (400)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Método todasReservas:  Listar todas las reservas.
     *
     * @return 200 o 204
     */
    @Operation(
            summary = "Obtener todas las reservas",
            description = "Obtener todas las reservas")
    @ApiResponse(responseCode = "200", description = "OK: Data content")
    @ApiResponse(responseCode = "204", description = "Error: No content")
    @GetMapping
    public ResponseEntity<Collection<Reserva>> todasReservas() {
        Collection<Reserva> reservas = reservaService.reservaTodas();
        if (reservas.isEmpty()) {
            return new ResponseEntity<>(reservas, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(reservas, HttpStatus.OK);
        }
    }

    /**
     * Método getByReserva: Obtener detalles de una reserva específica.
     *
     * @param id El id de la reserva.
     * @return 200 o 204
     */
    @Operation(
            summary = "Obtener detalles de una reserva específica",
            description = "Obtiene una reserva específica dada por su id")
    @ApiResponse(responseCode = "200", description = "OK: Data content")
    @ApiResponse(responseCode = "404", description = "Error: Not Found")
    @GetMapping("{id}")
    public ResponseEntity<Reserva> getByReserva(@PathVariable Integer id) {
        Reserva reserva = reservaService.reservaPorMesa(id);
        if (reserva == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reserva, HttpStatus.OK);
    }

    /**
     * Método actualizarReserva: Actualizar una reserva existente. Con los nuevos datos se modificará entera.
     *
     * @param id
     * @param reserva
     * @return 200, 400, 404, 409
     */
    @Operation(
            summary = "Actualizar una reserva existente",
            description = "Actualizar una reserva existente. Con los nuevos datos se modificará entera.")
    @ApiResponse(responseCode = "200", description = "OK: Data content")
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

    /**
     * Método eliminarPorReserva: Cancelar una reserva.
     *
     * @param id El id de la reserva.
     * @return 204 o 404.
     */
    @Operation(
            summary = "Cancelar una reserva.",
            description = "Cancela una reserva específica dado por su id")
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

    /**
     * Método reservasDelDia: Obtener las reservas del día actual
     *
     * @return Las reservas del día actual.
     */
    @Operation(
            summary = " Obtener las reservas del día actual.",
            description = " Obtener las reservas del día actual")
    @ApiResponse(responseCode = "200", description = "OK: Data found.")
    @ApiResponse(responseCode = "204", description = "Error: Not content")
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
