package com.alfredo.restaurantefour.service;

import com.alfredo.restaurantefour.model.Mesa;
import com.alfredo.restaurantefour.model.Reserva;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
        /*Integer mesaId = nuevoRegistro.getMesa();
        // Verificar si la mesa con el ID dado existe en el MesaService
        Mesa mesaAsociada = MesaService.datosDeMesa.get(mesaId);
        if (mesaAsociada != null) {
            // La mesa existe, podemos agregar la reserva
            nuevoRegistro.setId(nextId++);
            datosReservaPorMesa.put(nuevoRegistro.getId(), nuevoRegistro);
            log.info("Insertada nueva reserva " + nuevoRegistro.getId() + " para la mesa " + mesaId);
            return true;
        } else {
            // La mesa no existe, no podemos agregar la reserva
            log.error("La mesa con el ID " + mesaId + " no existe. No se pudo agregar la reserva.");
            return false;
        }*/
       /* Integer mesaId = nuevoRegistro.getMesa();
        // Verificar si la mesa con el ID dado existe en el MesaService
        Mesa mesaAsociada = MesaService.datosDeMesa.get(mesaId);
        if (mesaAsociada != null) {
            // La mesa existe, podemos agregar la reserva
            datosReservaPorMesa.put(nuevoRegistro.getId(), nuevoRegistro);
            log.info("Insertada nueva reserva " + nuevoRegistro.getId() + " para la mesa " + mesaId);
            return true;
        } else {
            // La mesa no existe, no podemos agregar la reserva
            log.error("La mesa con el ID " + mesaId + " no existe. No se pudo agregar la reserva.");
            return false;
        }*/
        /*if (MesaService.datosDeMesa.containsKey(mesaId)){
            // La mesa existe, podemos agregar la reserva
            datosReservaPorMesa.put(nuevoRegistro.getId(), nuevoRegistro);
            log.info("Insertada nueva reserva " + nuevoRegistro.getId());
            return true;
           // datosReservaPorMesa.put(nuevoRegistro);
        }else {
            // La mesa no existe, no podemos agregar la reserva
            log.error("La mesa con el ID " + mesaId + " no existe. No se pudo agregar la reserva.");
            return false;
        }*/
        /*Reserva reserva = new Reserva(1, 1, 1, 1, 2, 4);
        //Cuidado(puse un cast porque no sé que poner en reserva.get)
        datosReservaPorMesa.put(reserva.getId(), nuevoRegistro);
        log.info("Insertada nueva reserva " + reserva.getId());
        log.info("PROBANDO LOG!!");
        return true;*/
    }

    @Override
    public Reserva reservaPorMesa(Integer reserva) {
        return datosReservaPorMesa.get(reserva);
    }


    //Agregados de mi api
    @Override
    public boolean eliminarReserva(Integer reserva) {
        Reserva removed = datosReservaPorMesa.remove(reserva);
        return removed != null;
    }

    @Override
    public boolean actualizarPorReserva(Integer reserva, Reserva reservaActualizada) {
        if (datosReservaPorMesa.containsKey(reserva)) {
            datosReservaPorMesa.put(reserva, reservaActualizada);
            return true; //Actualización exitosa
        }
        return false;//La reserva no existe, actualización no exitosa
    }
}
