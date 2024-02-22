package com.alfredo.restaurantefour.service;

import com.alfredo.restaurantefour.model.Mesa;
import com.alfredo.restaurantefour.model.Reserva;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ReservaService implements IReservaService {
    private static int nextId = 1;
    public MesaService mesaService;
    private static final Logger log = LoggerFactory.getLogger(ReservaService.class);
    public static ConcurrentHashMap<Integer, Reserva> datosReservaPorMesa = new ConcurrentHashMap<>();

    @Override
    public void generarReservaFake() {
        //MesaService.datosDeMesa
        //if (MesaService.datosDeMesa.containsKey())
        Reserva reservaFake = new Reserva(1, 1, 1, 1, 2, 4);
        datosReservaPorMesa.put(reservaFake.getId(), reservaFake);
    }

    @Override
    public Collection<Reserva> reservaTodas() {

        return datosReservaPorMesa.values();
    }

    @Override
    public boolean nueva(Reserva nuevaReserva) {
        Integer mesaId = nuevaReserva.getMesa();

        // Verificar si la mesa con el ID dado existe en el MesaService
        Mesa mesaAsociada = MesaService.datosDeMesa.get(mesaId);
        if (mesaAsociada == null) {
            // La mesa no existe, no se puede agregar la reserva
            log.error("La mesa con el ID " + mesaId + " no existe. No se pudo agregar la reserva.");
            return false;
        }

        // Verificar número de comensales
        int numeroComensales = nuevaReserva.getNumeroComensales();
        if (numeroComensales < 1) {
            // El número de comensales es inválido
            log.error("El número de comensales (" + numeroComensales + ") es inválido. No se pudo agregar la reserva.");
            return false;
        }

        // Verificar capacidad de la mesa
        int capacidadMesa = mesaAsociada.getCapacidad();
        if (numeroComensales > capacidadMesa) {
            // El número de comensales excede la capacidad de la mesa
            log.error("El número de comensales (" + numeroComensales + ") excede la capacidad de la mesa (" + capacidadMesa + "). No se pudo agregar la reserva.");
            return false;
        }

        // Verificar si ya existe una reserva para la misma mesa, día y hora
        for (Reserva reservaExistente : datosReservaPorMesa.values()) {
            if (reservaExistente.getMesa().equals(nuevaReserva.getMesa()) &&
                    reservaExistente.getDia() == nuevaReserva.getDia() &&
                    reservaExistente.getHoraInicio() == nuevaReserva.getHoraInicio()) {
                // Si hay una reserva existente para la misma mesa, día y hora, no se puede agregar la nueva reserva
                log.error("Ya existe una reserva para la misma mesa, día y hora. No se pudo agregar la nueva reserva.");
                return false;
            }
        }

        // Si no se encontraron reservas conflictivas, asignamos un ID único a la nueva reserva
        nuevaReserva.setId(nextId++);
        datosReservaPorMesa.put(nuevaReserva.getId(), nuevaReserva);
        log.info("Insertada nueva reserva " + nuevaReserva.getId() + " para la mesa " + mesaId);
        return true;
    }

    @Override
    public Reserva reservaPorMesa(Integer reserva) {
        return datosReservaPorMesa.get(reserva);
    }


    //Agregados de mi api
    @Override
    public boolean eliminarReserva(Integer reserva) {
        /*Reserva removed = datosReservaPorMesa.remove(reserva);
        return removed != null;*/
        log.info("Intentando eliminar la reserva con ID: {}", reserva);

        Reserva removed = datosReservaPorMesa.remove(reserva);
        if (removed != null) {
            log.info("Reserva con ID {} eliminada exitosamente", reserva);
            return true;
        } else {
            log.warn("No se encontró ninguna reserva con el ID {}", reserva);
            return false;
        }
    }

    /*@Override
    public boolean actualizarPorReserva(Integer reserva, Reserva reservaActualizada) {
        if (datosReservaPorMesa.containsKey(reserva)) {
            datosReservaPorMesa.put(reserva, reservaActualizada);
            return true; //Actualización exitosa
        }
        return false;//La reserva no existe, actualización no exitosa
    }*/
    @Override
    public boolean actualizarPorReserva(Integer idReserva, Reserva reservaActualizada) {
        // Verificar si la reserva que se está actualizando existe
        if (!datosReservaPorMesa.containsKey(idReserva)) {
            log.error("No se encontró una reserva con el ID " + idReserva + ". No se pudo actualizar la reserva.");
            return false;
        }

        // Obtener la reserva antes de la actualización para verificar la mesa asociada y la capacidad actual
        Reserva reservaAntesDeActualizar = datosReservaPorMesa.get(idReserva);
        Integer mesaId = reservaAntesDeActualizar.getMesa();
        Mesa mesaAsociada = MesaService.datosDeMesa.get(mesaId);
        int capacidadMesa = mesaAsociada.getCapacidad();

        // Verificar número de comensales
        int numeroComensales = reservaActualizada.getNumeroComensales();
        if (numeroComensales < 1) {
            // El número de comensales es inválido
            log.error("El número de comensales (" + numeroComensales + ") es inválido. No se pudo actualizar la reserva.");
            return false;
        }

        // Verificar capacidad de la mesa
        if (numeroComensales > capacidadMesa) {
            // El número de comensales excede la capacidad de la mesa
            log.error("El número de comensales (" + numeroComensales + ") excede la capacidad de la mesa (" + capacidadMesa + "). No se pudo actualizar la reserva.");
            return false;
        }

        // Actualizar la reserva en el mapa de datos
        datosReservaPorMesa.put(idReserva, reservaActualizada);
        log.info("Reserva con ID " + idReserva + " actualizada correctamente.");
        return true;
    }

    @Override
    public Collection<Reserva> reservasDelDia() {
        LocalDate fechaHoy = LocalDate.now();
        Collection<Reserva> reservasHoy = new ArrayList<>();

        for (Reserva reserva : datosReservaPorMesa.values()) {
            if (reserva.getDia() == fechaHoy.getDayOfMonth()) {
                reservasHoy.add(reserva);
            }
        }

        return reservasHoy;
    }
}
