package com.alfredo.restaurantefour.service;

import com.alfredo.restaurantefour.model.Mesa;
import com.alfredo.restaurantefour.model.Reserva;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import static com.alfredo.restaurantefour.service.MesaService.datosDeMesa;

@Service
public class ReservaService implements IReservaService {
    private static int nextId = 1;
    private static final Logger log = LoggerFactory.getLogger(ReservaService.class);
    public static ConcurrentHashMap<Integer, Reserva> datosReservaPorMesa = new ConcurrentHashMap<>();

    @Override
    public void generarReservaFake() {
        //MesaService.datosDeMesa
        //if (MesaService.datosDeMesa.containsKey())
        Reserva reservaFake = new Reserva(1, 1, 1, 1, 2, "", "", "", 4);
        datosReservaPorMesa.put(reservaFake.getId(), reservaFake);
    }

    /**
     * Método reservaTodas: Lógica para obtener todas las reservas.
     *
     * @return las reservas.
     */
    @Override
    public Collection<Reserva> reservaTodas() {
        log.info("Obteniendo todas las reservas.");
        return datosReservaPorMesa.values();
    }

    /**
     * Método nueva: Lógica para crear una nueva reserva
     *
     * @param nuevaReserva
     * @return
     */
    @Override
    public boolean nueva(Reserva nuevaReserva) {
        Integer mesaId = nuevaReserva.getMesa();

        // Verificar si la mesa con el ID dado existe en el MesaService
        Mesa mesaAsociada = datosDeMesa.get(mesaId);
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
        // Validar que la hora de inicio sea menor que la hora final
        if (nuevaReserva.getHoraInicio() >= nuevaReserva.getHoraFin()) {
            log.error("La hora de inicio debe ser menor que la hora final.");
            return false;
        }
        // Validar que la duración de la reserva sea de exactamente una hora
        if (nuevaReserva.getHoraFin() - nuevaReserva.getHoraInicio() != 1) {
            log.error("La duración de la reserva debe ser de exactamente una hora.");
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

    /**
     * Método reservaPorMesa: Lógica para obtener una reserva.
     *
     * @param reserva El id de la reserva.
     * @return Una reserva.
     */
    @Override
    public Reserva reservaPorMesa(Integer reserva) {
        return datosReservaPorMesa.get(reserva);
    }

    /**
     * Método eliminarReserva: Lógica para eliminar una reserva.
     *
     * @param reserva El id de la reserva.
     * @return true si se ha eliminado, false sino se ha eliminado.
     */
    @Override
    public boolean eliminarReserva(Integer reserva) {
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

    /**
     * Método actualizarPorReserva: Lógica para actualizar una reserva
     *
     * @param idReserva          El id de la reserva.
     * @param reservaActualizada La reserva.
     * @return True si se ha actualizado, False si no se ha actualizado.
     */
    @Override
    public boolean actualizarPorReserva(Integer idReserva, Reserva reservaActualizada) {
        // Verificar si la reserva que se está actualizando existe
        if (!datosReservaPorMesa.containsKey(idReserva)) {
            log.error("No se encontró una reserva con el ID " + idReserva + ". No se pudo actualizar la reserva.");
            return false;
        }

        // Verificar si la mesa asociada a la reserva actualizada existe en el sistema
        Integer mesaId = reservaActualizada.getMesa();
        if (mesaId == null || !datosDeMesa.containsKey(mesaId)) {
            // La mesa no existe o el ID de la mesa es nulo, no se puede actualizar la reserva
            log.error("La mesa con el ID " + mesaId + " no existe o el ID de la mesa es nulo. No se pudo actualizar la reserva.");
            return false;
        }

        // Obtener la reserva antes de la actualización para verificar la mesa asociada y la capacidad actual
       // Reserva reservaAntesDeActualizar = datosReservaPorMesa.get(idReserva);
        Mesa mesaAsociada = datosDeMesa.get(mesaId);
        if (mesaAsociada == null) {
            // La mesa no existe, no se puede actualizar la reserva
            log.error("La mesa con el ID " + mesaId + " no existe. No se pudo actualizar la reserva.");
            return false;
        }
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

        // Validar que la hora de inicio sea menor que la hora final
        if (reservaActualizada.getHoraInicio() >= reservaActualizada.getHoraFin()) {
            log.error("La hora de inicio debe ser menor que la hora final.");
            return false;
        }

        // Validar que la duración de la reserva sea de exactamente una hora
        if (reservaActualizada.getHoraFin() - reservaActualizada.getHoraInicio() != 1) {
            log.error("La duración de la reserva debe ser de exactamente una hora.");
            return false;
        }

        // Verificar si ya existe una reserva para la misma mesa, día y hora
        for (Reserva reservaExistente : datosReservaPorMesa.values()) {
            if (reservaExistente.getMesa().equals(reservaActualizada.getMesa()) &&
                    reservaExistente.getDia() == reservaActualizada.getDia() &&
                    reservaExistente.getHoraInicio() == reservaActualizada.getHoraInicio()) {
                // Si hay una reserva existente para la misma mesa, día y hora, no se puede agregar la nueva reserva
                log.error("Ya existe una reserva para la misma mesa, día y hora. No se pudo agregar la nueva reserva.");
                return false;
            }
        }

        // Si todas las validaciones pasan, actualizar la reserva en el mapa de datos
        datosReservaPorMesa.put(idReserva, reservaActualizada);
        log.info("Reserva con ID " + idReserva + " actualizada correctamente.");
        return true;
    }

    /**
     * Método reservasDelDia: Lógica para obtener las reservas del día actual.
     *
     * @return Las reservas del día actual.
     */
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
    public static boolean verificarConflictoHorario(Reserva reservaActualizada) {
        // Verificar que la duración de la reserva sea de exactamente una hora
        if (reservaActualizada.getHoraFin() - reservaActualizada.getHoraInicio() != 1) {
            return true; // La duración de la reserva no es de una hora
        }
        for (Reserva reservaExistente : datosReservaPorMesa.values()) {
            if (reservaExistente.getId() != reservaActualizada.getId() &&
                    reservaExistente.getMesa().equals(reservaActualizada.getMesa()) &&
                    reservaExistente.getDia() == reservaActualizada.getDia() &&
                    reservaExistente.getHoraInicio() == reservaActualizada.getHoraInicio()) {
                return true; // Hay conflicto de horario
            }
        }
        return false; // No hay conflicto de horario
    }
    public static boolean existeReserva(Integer idReserva) {
        return datosReservaPorMesa.containsKey(idReserva);
    }
}
